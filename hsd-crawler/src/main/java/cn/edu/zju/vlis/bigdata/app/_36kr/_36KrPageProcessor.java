package cn.edu.zju.vlis.bigdata.app._36kr;

import cn.edu.zju.vlis.bigdata.AbstractPageProcessor;
import cn.edu.zju.vlis.bigdata.PAGE_TYPE;
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

    /**
     * process the index page to fetch the url for crawling
     *
     * @param page
     */
    @Override
    public void processIndexPage(Page page) {
        Html html = page.getHtml();

        List<Selectable> articles = html.xpath("article").nodes();

        articles.forEach(article -> {
            String time = article.xpath("time/@datetime").get();
            String aurl = article.xpath("a[@class='title info_flow_news_title']/@href").get();
        });

        String nextIndexUrl = html.xpath("a[@class=\'load-more J_listLoadMore\']/@href").get();

    }

    /**
     * process the content page and fetch the related information
     *
     * @param page
     */
    @Override
    public void processDetailPage(Page page) {

    }

    /**
     * return the page type of the url
     *
     * @param url
     * @return
     */
    @Override
    public PAGE_TYPE getPageTypeByUrl(String url) {
        if(url.contains("&d=next") || url.equals("http://36kr.com/")){
            return PAGE_TYPE.INDEX_PAGE;
        }else if(url.contains("http://36kr.com/p/")){
            return PAGE_TYPE.DETAIL_PAGE;
        }else {
            return PAGE_TYPE.NOT_DEFINE;
        }
    }
}
