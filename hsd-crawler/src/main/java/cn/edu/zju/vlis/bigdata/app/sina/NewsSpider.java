package cn.edu.zju.vlis.bigdata.app.sina;

import cn.edu.zju.vlis.bigdata.SpiderContaniner;
import cn.edu.zju.vlis.bigdata.filter.Filter;
import cn.edu.zju.vlis.bigdata.filter.TimeRangeFilter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.concurrent.ExecutorService;

/**
 * Created by wangxiaoyi on 15/9/24.
 *
 * start the news crawler
 */


public class NewsSpider implements SpiderContaniner{

    private Filter filter = null;


    private Config conf = null;

    private Spider spider = null;
    private ExecutorService service = null;
    private Pipeline pipeline = null;
    private PageProcessor processor = null;
    private String spiderName = null;


    public void setConfig(Config conf){
        this.conf = conf;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }


    /**
     * get the spider
     *
     * @return
     */
    @Override
    public Spider getSpider() {
        return spider;
    }

    /**
     * start the spider
     */
    @Override
    public void startSpider() {
        spider.run();
        ((NewsPipeline)pipeline).saveToDbAfterClose();
    }

    /**
     * init the spider
     */
    @Override
    public void initSpider() {
        spiderName = "NewsSpider";
        conf = ConfigFactory.load();

        Filter filter = new TimeRangeFilter(20151015, 20151015);
        processor = new NewsPageProcessor(conf).setFilter(filter);
        pipeline = new NewsPipeline();

        spider = Spider.create(processor)
                        .addUrl("http://roll.finance.sina.com.cn/finance/gncj/gncj/index_1.shtml")
                        .addPipeline(pipeline)
                        .thread(4);
    }

    /**
     * stop the spider
     */
    @Override
    public void stopSpider() {
        spider.close();
    }

    /**
     * get the spider name
     *
     * @return
     */
    @Override
    public String getSpiderName() {
        return spiderName;
    }


    public static void main(String []args){
        SpiderContaniner spiderContaniner = new NewsSpider();
        spiderContaniner.initSpider();
        spiderContaniner.startSpider();
    }

}
