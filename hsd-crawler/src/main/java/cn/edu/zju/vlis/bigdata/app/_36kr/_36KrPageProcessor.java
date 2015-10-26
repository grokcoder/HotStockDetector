package cn.edu.zju.vlis.bigdata.app._36kr;

import cn.edu.zju.vlis.bigdata.AbstractPageProcessor;
import cn.edu.zju.vlis.bigdata.DateParser;
import cn.edu.zju.vlis.bigdata.PAGE_TYPE;
import cn.edu.zju.vlis.bigdata.app.model.Article;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * Created by wangxiaoyi on 15/10/20.
 *
 * the page parser for 36kr
 */
public class _36KrPageProcessor extends AbstractPageProcessor{

    private static final Logger LOG = LoggerFactory.getLogger(_36KrPageProcessor.class);

    /**
     * process the index page to fetch the url for crawling
     *
     * @param page
     */
    @Override
    public void processIndexPage(Page page) {
        Html html = page.getHtml();
        List<Selectable> articles = html.xpath("article").nodes();
        String nextIndexUrl = html.xpath("a[@class=\'load-more J_listLoadMore\']/@href").get();

        articles.forEach(article -> {
            //two types of time tags
            String time = article.xpath("time/@title").get();
            if(time == null) time = article.xpath("abbr/@title").get();
            if(filter == null) {
                String aurl = article.xpath("a[@class='title info_flow_news_title']/@href").get();
                page.addTargetRequest(aurl);
                page.addTargetRequest(nextIndexUrl);
            }else {
                long t = DateParser.parseDateBySchema(time, "yyyy-MM-dd HH:mm:ss Z");
                switch (filter.filtrateURLByTime(t)){
                    case EXCLUED_GO_TO_NEXT:
                        page.addTargetRequest(nextIndexUrl);
                        break;
                    case INCLUDE:
                        String aurl = article.xpath("a[@class='title info_flow_news_title']/@href").get();
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
        article.setTitle(html.xpath("h1/text()").get());
        article.setUrl(page.getUrl().get());
        String date = DateParser.transformBySchema(
                html.xpath("meta[@name=\'weibo: article:create_at\']/@content").get(),
                "yyyy-MM-dd HH:mm:ss Z",
                "yyyy-MM-dd");
        article.setPubDate(date);

        article.setPubMedia(html.xpath("meta[@name=author]/@content").get());
        article.setContent(html.xpath("section[@class=article]/tidyText()").get());
        page.putField(HsdConstant.MODEL, article);
    }

    /**
     * return the page type of the url
     *
     * @param url
     * @return
     */
    @Override
    public PAGE_TYPE getPageTypeByUrl(String url) {
        if(url.endsWith("&d=next") || url.equals("http://36kr.com/")){
            return PAGE_TYPE.INDEX_PAGE;
        }else if(url.contains("http://36kr.com/p/")){
            return PAGE_TYPE.DETAIL_PAGE;
        }else {
            return PAGE_TYPE.NOT_DEFINE;
        }
    }
}
