package cn.edu.zju.vlis.bigdata.app.sina;

import cn.edu.zju.vlis.bigdata.SpiderContainer;
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


public class NewsSpider implements SpiderContainer {

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

        Filter filter = new TimeRangeFilter(20151020, 20151020);
        processor = new NewsPageProcessor(conf).setFilter(filter);
        pipeline = new NewsPipeline();

        spider = Spider.create(processor)
                        .addUrl("http://roll.finance.sina.com.cn/finance/gncj/gncj/index_1.shtml")//国内财经
                        //.addUrl("http://roll.finance.sina.com.cn/finance/gncj/jrxw/index_1.shtml")//金融新闻
                        //.addUrl("http://roll.finance.sina.com.cn/finance/gncj/dfjj/index_1.shtml")//地方经济
                        //.addUrl("http://roll.finance.sina.com.cn/finance/gncj/hgjj/index_1.shtml")//行业经济
                        //.addUrl("http://roll.finance.sina.com.cn/finance/gncj/bwdt/index_1.shtml")//部委动态
                        .addPipeline(pipeline)
                        .thread(1);
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
        SpiderContainer spiderContainer = new NewsSpider();
        spiderContainer.initSpider();
        spiderContainer.startSpider();
    }

}
