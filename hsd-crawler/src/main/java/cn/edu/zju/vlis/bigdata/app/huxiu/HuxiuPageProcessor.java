package cn.edu.zju.vlis.bigdata.app.huxiu;

import cn.edu.zju.vlis.bigdata.AbstractPageProcessor;
import cn.edu.zju.vlis.bigdata.DateParser;
import cn.edu.zju.vlis.bigdata.PAGE_TYPE;

import cn.edu.zju.vlis.bigdata.app.model.Article;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import cn.edu.zju.vlis.bigdata.common.UrlFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * Created by wangxiaoyi on 15/10/22.
 * page processor for Huxiu
 */
public class HuXiuPageProcessor extends AbstractPageProcessor{

    /**
     * process the index page to fetch the url for crawling
     *
     * @param page
     */
    @Override
    public void processIndexPage(Page page) {
        Json jsonData = page.getJson();
        JSONObject jo = JSON.parseObject(jsonData.get());
        String data = jo.getString("data");
        if(null != data) {
            Html html = Html.create(data);
            List<Selectable> articles = html.xpath("div[@class=\"mod-b mod-art\"]").nodes();

            articles.forEach(article -> {
                String timeText = article.xpath("span[@class=time]/text()").get();
                long time = getArticlePubTime(timeText);

                String url = UrlFactory.getHuXiuDetailPage(article.xpath("a/@href").get());
                String nextIndexUrl = getNextIndexUrl(page.getUrl().get());

                if(filter == null){
                    page.addTargetRequest(url);
                    page.addTargetRequest(nextIndexUrl);
                }else {
                    switch (filter.filtrateURLByTime(time)){
                        case INCLUDE:
                            page.addTargetRequest(url);
                            page.addTargetRequest(nextIndexUrl);
                            break;
                        case EXCLUED_GO_TO_NEXT:
                            page.addTargetRequest(nextIndexUrl);break;
                        case EXCLUDE_NOT_GO_TO_NEXT:break;
                    }
                }
            });

        }
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
        //article.setPubMedia(html.xpath("meta[@name=author]/@content").get());
        article.setPubMedia("虎嗅网");
        article.setUrl(page.getUrl().get());
        article.setKeywords(html.xpath("meta[@name=keywords]/@content").get());
        article.setTitle(html.xpath("h1[@class=t-h1]/text()").get());

        String stringDate = html.xpath("span[@class=article-time]/text()").get();
        stringDate = DateParser.parseDateBySchema(
                DateParser.parseDateBySchema(stringDate, "yyyy-MM-dd HH:mm"), "yyyy-MM-dd");
        article.setPubDate(stringDate);

        List<Selectable> phases = html.xpath("div[@id=article_content]/p").nodes();
        StringBuilder sb = new StringBuilder();
        phases.forEach(phase -> {
            sb.append(phase.xpath("p/text()"));
        });

        article.setContent(sb.toString());

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
        if(url.contains("page=")){
            return PAGE_TYPE.INDEX_PAGE;
        }else if(url.startsWith("http://www.huxiu.com/article/")){
            return PAGE_TYPE.DETAIL_PAGE;
        }
        return PAGE_TYPE.NOT_DEFINE;
    }

    /**
     * construct the date from timeText
     * @param timeText
     * @return
     */
    public long getArticlePubTime(String timeText){
        long now = System.currentTimeMillis();
        long timeParsed = 0l;

        for(int i = 0; i < 24; ++i){
            if(timeText.startsWith(i + "小")){
                timeParsed = i * 60 * 60 * 1000;
                break;
            }
        }

        for(int i = 1; i <= 7; ++i){
            if(timeText.startsWith(i + "天")){
                timeParsed = i * 24 * 60 * 60 * 1000;
                break;
            }
        }

        if(timeParsed == 0){
            timeParsed = DateParser.parseDateBySchema(timeText, "yyyy-MM-dd");
            return timeParsed;
        }
        return now - timeParsed;
    }


    public String getNextIndexUrl(String currUrl){

        int index1 = currUrl.indexOf("page=");
        int index2 = currUrl.indexOf('&');

        int paggeNum = Integer.parseInt(currUrl.substring(index1 + 5, index2));


        int catid = Integer.parseInt(currUrl.charAt(currUrl.length() - 1) + "");
        return UrlFactory.getHuXiuIndexPage(paggeNum + 1, catid);
    }



}
