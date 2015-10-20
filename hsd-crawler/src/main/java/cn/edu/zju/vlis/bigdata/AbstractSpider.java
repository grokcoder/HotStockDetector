package cn.edu.zju.vlis.bigdata;

import cn.edu.zju.vlis.bigdata.filter.Filter;
import com.typesafe.config.Config;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.concurrent.ExecutorService;

/**
 * Created by wangxiaoyi on 15/10/20.
 * the abstract spider supply the common function for the specific spider
 */
public abstract class AbstractSpider implements SpiderContainer{


    protected Filter filter = null;

    protected Config conf = null;

    protected Spider spider = null;
    protected ExecutorService service = null;
    protected Pipeline pipeline = null;
    protected PageProcessor processor = null;
    protected String spiderName = null;


    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public void setConf(Config conf) {
        this.conf = conf;
    }

    public void setService(ExecutorService service) {
        this.service = service;
    }

    public void setSpiderName(String spiderName) {
        this.spiderName = spiderName;
    }


    /**
     * get the spider
     *
     * @return
     */
    @Override
    public Spider getSpider(){
        return spider;
    }
    /**
     * start the spider
     */
    @Override
    public abstract void startSpider();

    /**
     * init the spider
     */
    @Override
    public abstract void initSpider();

    /**
     * stop the spider
     */
    @Override
    public  void stopSpider(){
        spider.stop();
    }
    /**
     * get the spider name
     *
     * @return
     */
    @Override
    public  String getSpiderName(){
        return spiderName;
    }
}
