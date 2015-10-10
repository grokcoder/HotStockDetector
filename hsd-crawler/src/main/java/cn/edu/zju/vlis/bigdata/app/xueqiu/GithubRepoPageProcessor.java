package cn.edu.zju.vlis.bigdata.app.xueqiu;

/**
 * Created by zhuohaizhen on 15-10-9.
 */
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class GithubRepoPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(1).setSleepTime(100);

    @Override
    public void process(Page page) {


        //System.out.println(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());

        for(int i=1;i<=54;i++)
        {
            page.addTargetRequest("http://xueqiu.com/stock/cata/stocklist.json?page="+i+"&size=90&order=asc&orderby=code&type=11%2C12");
        }
        //System.out.println(page.getTargetRequests().toString());
        //page.addTargetRequests(page.getHtml().links().regex("(http://xueqiu.com/S/S\\w+)").all());
        page.putField("url", page.getUrl().toString());
        //System.out.print(page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        //System.out.println(page.getHtml());
        page.putField("name", page.getJson().toString());
        System.out.println("***************");
        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new GithubRepoPageProcessor()).addUrl("http://xueqiu.com/hq#exchange=CN&firstName=1&secondName=1_0").thread(5).run();
    }
}