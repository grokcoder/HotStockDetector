package cn.edu.zju.vlis.bigdata.app.huxiu;

import cn.edu.zju.vlis.bigdata.AbstractSpider;
import cn.edu.zju.vlis.bigdata.DateParser;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import cn.edu.zju.vlis.bigdata.common.UrlFactory;
import cn.edu.zju.vlis.bigdata.filter.TimeRangeFilter;
import com.typesafe.config.ConfigFactory;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

/**
 * Created by wangxiaoyi on 15/10/22.
 */
public class HuXiuSpider extends AbstractSpider{


    /**
     * start the spider
     */
    @Override
    public void startSpider() {
        spider.start();
    }

    /**
     * init the spider
     */
    @Override
    public void initSpider() {

        //1. basic configuration
        this.setSpiderName("HuXiuSpider");

        conf = ConfigFactory.load();
        this.setConf(conf);
        Site site = Site.me()
                .setRetryTimes(conf.getInt(HsdConstant.CRAWLER_RETRY_TIMES))
                .setSleepTime(conf.getInt(HsdConstant.CRAWLER_SLEEP_TIME))
                .setTimeOut(conf.getInt(HsdConstant.CRAWLER_TIME_OUT))
                .setUserAgent(conf.getString(HsdConstant.CRAWLER_USER_AGENT))
                .setCharset("utf-8");

        //TODO: this spider just can crawl data a long time ago for example: one week because no specific time for each index url
        long start = DateParser.parseDateBySchema("2014-9-22 00:00:00", "yyyy-MM-dd HH:mm:ss");
        long end = DateParser.parseDateBySchema("2015-10-22 23:59:59", "yyyy-MM-dd HH:mm:ss");

        //2. page processor configuration

        this.processor = new HuXiuPageProcessor()
                .setFilter(new TimeRangeFilter(start, end))
                .setSite(site);

        //3. pipeline configuration

        this.pipeline = new HuXiuPipeLine();

        //4. spider configuration

        this.spider = Spider.create(processor)
                .addPipeline(pipeline)
                .addUrl(UrlFactory.getHuXiuIndexPage(1, 1))
                //.addUrl(UrlFactory.getHuXiuIndexPage(1, 2))
                .thread(4)
                .setExitWhenComplete(true);

    }

    public static void main(String []args){
        HuXiuSpider spider = new HuXiuSpider();
        spider.initSpider();
        spider.startSpider();
    }


}
