package cn.edu.zju.vlis.bigdata.app.tech2ipo;

import cn.edu.zju.vlis.bigdata.AbstractSpider;
import cn.edu.zju.vlis.bigdata.DateParser;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import cn.edu.zju.vlis.bigdata.filter.TimeRangeFilter;
import com.typesafe.config.ConfigFactory;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;

/**
 * Created by wangxiaoyi on 15/10/23.
 *
 * crawl data from website: http://tech2ipo.com/
 * mainly includes 3 plates:
 * 1. http://tech2ipo.com/news/
 * 2. http://tech2ipo.com/startup
 * 3. http://tech2ipo.com/apps
 *
 */
public class Tech2ipoSpider extends AbstractSpider{

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
        this.setSpiderName("Tech2ipoSpider");

        conf = ConfigFactory.load();
        this.setConf(conf);
        Site site = Site.me()
                .setRetryTimes(conf.getInt(HsdConstant.CRAWLER_RETRY_TIMES))
                .setSleepTime(conf.getInt(HsdConstant.CRAWLER_SLEEP_TIME))
                .setTimeOut(conf.getInt(HsdConstant.CRAWLER_TIME_OUT))
                .setUserAgent(conf.getString(HsdConstant.CRAWLER_USER_AGENT));

        long start = DateParser.parseDateBySchema("2015-10-22 00:00:00", "yyyy-MM-dd HH:mm:ss");
        long end = DateParser.parseDateBySchema("2015-10-22 23:59:59", "yyyy-MM-dd HH:mm:ss");

        //2. page processor configuration
        this.processor = new Tech2ipoPageProcessor()
                .setFilter(new TimeRangeFilter(start, end))
                .setSite(site);

        //3. pipeline configuration
        this.pipeline = new Tech2ipoPipeline();

        //4. spider configuration
        this.spider = Spider.create(processor)
                .addPipeline(pipeline)
                .addUrl("http://tech2ipo.com/news/1")
                .addUrl("http://tech2ipo.com/startup/1")
                .addUrl("http://tech2ipo.com/apps/1")
                .thread(1)
                .setExitWhenComplete(true);
    }

    public static void main(String []args){
        Tech2ipoSpider tech2ipoSpider = new Tech2ipoSpider();
        tech2ipoSpider.initSpider();
        tech2ipoSpider.startSpider();
    }
}
