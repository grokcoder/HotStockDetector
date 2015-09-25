package cn.edu.zju.vlis.bigdata.app.sina;

import cn.edu.zju.vlis.bigdata.NAV_BAR;
import cn.edu.zju.vlis.bigdata.PAGE_TYPE;
import cn.edu.zju.vlis.bigdata.PageClassifier;
import cn.edu.zju.vlis.bigdata.app.sina.model.News;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import cn.edu.zju.vlis.bigdata.common.UrlFactory;
import cn.edu.zju.vlis.bigdata.common.UrlParser;
import com.typesafe.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * NewsPageProcessor responsible for parse the finance news page
 * and fetch the info needed
 *
 */

public class NewsPageProcessor implements PageProcessor{

    private static final Logger LOG = LogManager.getLogger(NewsPageProcessor.class.getName());

    private Config conf = null;
    private Site site = null;

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


    public boolean validate(String time){


        Integer endDate = 20150924;// for test

        if(Integer.valueOf(time) > endDate){
            return true;
        }else {
            return false;
        }
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
            String time = UrlParser.fetchTimeInfo(url, regex).get();
            //TODO: find better way to fetch time info from content url
            time = time.substring(1, time.length() - 1);
            if(validate(time)){
                // add
                requests.add(new Request(url));
            }else {
                gotoNextPage = false;
                break;
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


        //TODO: 明儿继续

        news.setTitle(html.xpath("h1[@id=artibodyTitle]/test()").get());
        news.setBody(html.css("div[@id=artibody]").get());


        news.setPublishMedia(html.css("div[class=page-info] > span[class=time-source]").get());
        //news.setPublishDate(html.css("div[class=page-info] > span[class=time-source]").get());

        // div[@id=artibody]/p/text() {多个段落}
        // div[@class=page-info]/span[@class=time-source]/text() // 时间
        // div[@class=page-info]/span[@class=time-source]/text()/span/text() // 来源

        // div[@class=article-keywords]/a/text() //关键字 多个


        news.setKeywords(html.css("div[class=article-keywords]").get());


        LOG.info(news);

       // page.putField(HsdConstant.MODEL, news);

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



    @Override
    public Site getSite() {
        return site;
    }
}
