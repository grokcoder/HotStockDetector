package cn.edu.zju.vlis.bigdata.app.tech2ipo;

import cn.edu.zju.vlis.bigdata.AbstractPageProcessor;
import cn.edu.zju.vlis.bigdata.DateParser;
import cn.edu.zju.vlis.bigdata.PAGE_TYPE;
import cn.edu.zju.vlis.bigdata.app.model.Article;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import cn.edu.zju.vlis.bigdata.common.UrlFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangxiaoyi on 15/10/23.
 */

public class Tech2ipoPageProcessor extends AbstractPageProcessor{


    /**
     * process the index page to fetch the url for crawling
     *
     * @param page
     */
    @Override
    public void processIndexPage(Page page) {
        Html html = page.getHtml();
        List<Selectable> articles = html.xpath("article[@class=post]").nodes();


        String nextIndexUrl = UrlFactory.getTech2ipoNextIndexPage(page.getUrl().get());

        articles.forEach(article -> {
            String time = article.xpath("span[@class=author]/text()").get();

            if(filter == null) {
                String aurl = article.xpath("h3/a/@href").get();
                page.addTargetRequest(aurl);
                page.addTargetRequest(nextIndexUrl);
            }else {
                long t = getCleanTime(time);
                switch (filter.filtrateURLByTime(t)){
                    case EXCLUED_GO_TO_NEXT:
                        page.addTargetRequest(nextIndexUrl);
                        break;
                    case INCLUDE:
                        String aurl = article.xpath("h3/a/@href").get();
                        page.addTargetRequest(aurl);
                        page.addTargetRequest(nextIndexUrl);
                        break;
                    case EXCLUDE_NOT_GO_TO_NEXT:
                        break;
                }
            }
        });

    }

    /**
     * process the content page and fetch the related information
     *
     * @param page
     */
    @Override
    public void processDetailPage(Page page) {
        Html html = page.getHtml();
        Article article = new Article();
        article.setUrl(page.getUrl().get());
        article.setTitle(html.xpath("article/header/h1/text()").get());
        article.setKeywords(html.xpath("meta[@name=keywords]/@content").get());
        article.setPubMedia("Tech2ipo");

        List<Selectable> phases = html.xpath("section[@id=article-content]/p").nodes();
        StringBuilder content = new StringBuilder();
        phases.forEach(p -> content.append(p.xpath("p/text()").get()));

        article.setContent(content.toString());

        String dirtyTime = html.xpath("div[@class=title-act]/text()").get();

        article.setPubDate(DateParser.parseDateBySchema(getCleanTime(dirtyTime), "yyyy-MM-dd"));

        page.putField(HsdConstant.MODEL, article);
    }

    /**
     * return the page type of the url
     *
     * @param url
     * @return INDEX_PAGE, DETAIL_PAGE or NOT_DEFINE
     */
    @Override
    public PAGE_TYPE getPageTypeByUrl(String url) {

        if(url.contains("news") || url.contains("apps") || url.contains("startup")){
            return PAGE_TYPE.INDEX_PAGE;
        }else {
            return PAGE_TYPE.DETAIL_PAGE;
        }
    }

    /**
     * get clean time info form text like "  发表于2015-10-23 13:03:03 "
     * @param timeText
     * @return
     */
    public long getCleanTime(String timeText){

        Pattern r = Pattern.compile("([\\d]{4})-[\\d]{2}-[\\d]{2}\\s{1}[\\d]{2}:[\\d]{2}:[\\d]{2}");
        Matcher m = r.matcher(timeText);
        Optional<String> time;
        if (m.find( )) {
            time = Optional.of(m.group(0));
        } else {
            return -1;
        }
        return DateParser.parseDateBySchema(time.get(), "yyyy-MM-dd HH:mm:ss");
    }
}
