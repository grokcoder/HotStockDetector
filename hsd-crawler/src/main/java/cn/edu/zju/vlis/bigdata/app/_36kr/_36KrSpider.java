package cn.edu.zju.vlis.bigdata.app._36kr;

import cn.edu.zju.vlis.bigdata.AbstractSpider;
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
        this.setSpiderName("36krSpider");

        conf = ConfigFactory.load();
        this.setConf(conf);
        Site site = Site.me()
                .setRetryTimes(conf.getInt(HsdConstant.CRAWLER_RETRY_TIMES))
                .setSleepTime(conf.getInt(HsdConstant.CRAWLER_SLEEP_TIME))
                .setTimeOut(conf.getInt(HsdConstant.CRAWLER_TIME_OUT))
                .setUserAgent(conf.getString(HsdConstant.CRAWLER_USER_AGENT));

        this.processor = new _36KrPageProcessor()
                        .setFilter(new TimeRangeFilter(1, 2))
                        .setSite(site);

       // this.pipeline = new ConsolePipeline();
        this.spider = Spider.create(processor)
                        .addPipeline(pipeline)
                        .addUrl("http://36kr.com/")
                        .thread(1)
                        .setExitWhenComplete(false);

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
