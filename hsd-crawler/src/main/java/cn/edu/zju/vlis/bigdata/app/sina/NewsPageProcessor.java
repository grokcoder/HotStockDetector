package cn.edu.zju.vlis.bigdata.app.sina;

import cn.edu.zju.vlis.bigdata.DateParser;
import cn.edu.zju.vlis.bigdata.NAV_BAR;
import cn.edu.zju.vlis.bigdata.PAGE_TYPE;
import cn.edu.zju.vlis.bigdata.PageClassifier;
import cn.edu.zju.vlis.bigdata.app.sina.model.News;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import cn.edu.zju.vlis.bigdata.common.UrlFactory;

import cn.edu.zju.vlis.bigdata.common.UrlParser;
import cn.edu.zju.vlis.bigdata.filter.FILTER_CODE;
import cn.edu.zju.vlis.bigdata.filter.Filter;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by wangxiaoyi on 15/9/24.
 *
 * XueQiuPageProcessor responsible for parse the finance news page
 * and fetch the info needed
 *
 */

public class NewsPageProcessor implements PageProcessor{

    private static final Logger LOG = LoggerFactory.getLogger(NewsPageProcessor.class);

    private Config conf = null;
    private Site site = null;

    private Filter filter = null;

    public NewsPageProcessor(Config config){

        this.conf = config;
        site = Site.me()
                .setRetryTimes(conf.getInt(HsdConstant.CRAWLER_RETRY_TIMES))
                .setSleepTime(conf.getInt(HsdConstant.CRAWLER_SLEEP_TIME))
                .setTimeOut(conf.getInt(HsdConstant.CRAWLER_TIME_OUT))
                .setUserAgent(conf.getString(HsdConstant.CRAWLER_USER_AGENT));
    }

    //just for tests
    public NewsPageProcessor(){};



    @Override
    public void process(Page page) {

        String currUrl = page.getUrl().get();
        LOG.info("Start process page " + currUrl);

        PAGE_TYPE type = PageClassifier.getPageType(currUrl);
        switch (type){
            case SINA_FINANCIAL_INDEX_PAGE: processIndexPage(page);
                break;
            case SINA_FINANCIAL_CONTENT_PAGE: processContentPage(page);
                break;
            case NOT_DEFINE:LOG.warn(currUrl + " no parser defined yet ! ");
                break;
        }
        LOG.info("process complete : " + currUrl);
    }


    /**
     * process the index page
     * fetch the next index page url and
     * content urls in current index page
     * @param page curr page
     */
    public void processIndexPage(Page page){

        List<Request> requests = new LinkedList<>();

        String cssQuery = "div[class=listBlk] > ul > li > a";// url for news
        String regex = "[/^]\\d{8}[/$]";// fetch time;

        List<String> urls = fetchUrls(page, cssQuery);

        boolean gotoNextPage = true;

        for(String url : urls){
            if(filter != null) {
                String time = UrlParser.fetchTimeInfo(url, regex).get();
                time = time.substring(1, time.length() - 1);
                Long t = DateParser.parseDateBySchema(time, "yyyyMMdd");
                if(t == -1) com.sun.tools.javac.util.Assert.error("time paese error " + time);

                FILTER_CODE code = filter.filtrateURLByTime(t);
                switch (code){
                    case INCLUDE: requests.add(new Request(url));break;
                    case EXCLUDE_NOT_GO_TO_NEXT:gotoNextPage = false; break;
                    case EXCLUED_GO_TO_NEXT:gotoNextPage = true;break;
                }
            }else {
                requests.add(new Request(url));
            }
        }

        if(gotoNextPage){
            String nextIndexPage = getNextPageUrl(page.getUrl().get(), NAV_BAR.GNCJ);
            requests.add(new Request(nextIndexPage));
        }
        // add more request we fetched
        requests.forEach(request -> page.addTargetRequest(request));

    }

    /**
     * process the page and fetch the news info from the page
     * @param page
     */
    public void processContentPage(Page page){

        News news = new News();
        Html html = page.getHtml();

        news.setUrl(page.getUrl().get());

        news.setTitle(html.xpath("h1[@id=artibodyTitle]/text()").get());

        //extract news content
        List<String> phases = html.xpath("div[@id=artibody]/p/tidyText()").all();
        StringBuilder body = new StringBuilder();
        phases.forEach(p -> body.append(p));

        //todo:try better solution
        String cleanBody = body.toString();
        cleanBody.replace("\"", "");
        cleanBody.replace("'", "");
        cleanBody.replace("\\", "");
        cleanBody.replace("/", "");
        cleanBody.replace("“","");
        cleanBody.replace("‘","");
        //cleanBody.replace("<", "");


        news.setBody(cleanBody);

        news.setPublishDate(html.xpath("div[@class=page-info]/span[@class=time-source]/text()").get());

        news.setPublishMedia(html.xpath("meta[@name=mediaid]/@content").get());

        //extract keywords
        news.setKeywords(html.xpath("meta[@name=keywords]/@content").get());

        news.setTags(html.xpath("meta[@name=tags]/@content").get());

        page.putField(HsdConstant.MODEL, news);

    }

    /**
     * construct next crawled url based on the current url
     * and the navigation bar
     * @param currUrl current page url
     * @param nav_bar navigation bar type
     * @return
     */
    public String getNextPageUrl(String currUrl, NAV_BAR nav_bar){
        int startIndex = currUrl.indexOf('_');
        int endIndex = currUrl.indexOf(".shtml");
        int currPageNum = Integer.valueOf(currUrl.substring(startIndex + 1, endIndex));
        switch (nav_bar){//todo: implement in factory
            case GNCJ: return UrlFactory.getSinaGNCJPageUrl(currPageNum + 1);
        }
        return null;
    }


    /**
     * fetch URL form page
     * @param page
     * @param cssQuery css query expressions
     * @return list of URLs
     */
    public List<String> fetchUrls(Page page, String cssQuery){
        List<String> urls = new LinkedList<>();
        Html html = page.getHtml();
        urls.addAll(html.css(cssQuery).links().all());
        return urls;
    }

    public PageProcessor setFilter(Filter filter) {
        this.filter = filter;
        return this;
    }

    @Override
    public Site getSite() {
        return site;
    }
}
