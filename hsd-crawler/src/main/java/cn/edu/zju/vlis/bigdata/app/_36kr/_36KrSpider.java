package cn.edu.zju.vlis.bigdata.app._36kr;

import cn.edu.zju.vlis.bigdata.AbstractSpider;
import cn.edu.zju.vlis.bigdata.DateParser;
import cn.edu.zju.vlis.bigdata.SpiderContainer;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import cn.edu.zju.vlis.bigdata.filter.TimeRangeFilter;
import com.typesafe.config.ConfigFactory;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;


/**
 * Created by wangxiaoyi on 15/10/20.
 * crawl the data from web: http://36kr.com/
 * focus on the information module
 */
public class _36KrSpider  extends AbstractSpider{

    /**
     * init the spider
     */
    @Override
    public void initSpider() {

        //1. basic configuration
        this.setSpiderName("36krSpider");

        conf = ConfigFactory.load();
        this.setConf(conf);
        Site site = Site.me()
                .setRetryTimes(conf.getInt(HsdConstant.CRAWLER_RETRY_TIMES))
                .setSleepTime(conf.getInt(HsdConstant.CRAWLER_SLEEP_TIME))
                .setTimeOut(conf.getInt(HsdConstant.CRAWLER_TIME_OUT))
                .setUserAgent(conf.getString(HsdConstant.CRAWLER_USER_AGENT));

        long start = DateParser.parseDateBySchema("2014-9-22 00:00:00", "yyyy-MM-dd HH:mm:ss");
        long end = DateParser.parseDateBySchema("2015-10-22 23:59:59", "yyyy-MM-dd HH:mm:ss");

        //2. page processor configuration
        this.processor = new _36KrPageProcessor()
                        .setFilter(new TimeRangeFilter(start, end))
                        .setSite(site);

        //3. pipeline configuration
        this.pipeline = new _36KrPipeline();

        //4. spider configuration
        this.spider = Spider.create(processor)
                        .addPipeline(pipeline)
                        .addUrl("http://36kr.com/")
                        //.addUrl("http://36kr.com/?b_url_code=5038684&d=next")
                        .thread(4)
                        .setExitWhenComplete(true);

    }

    /**
     * start the spider
     */
    @Override
    public void startSpider() {
        spider.start();
    }


    public static void main(String []args){
        SpiderContainer spiderContainer =  new _36KrSpider();
        spiderContainer.initSpider();
        spiderContainer.startSpider();
    }
}
