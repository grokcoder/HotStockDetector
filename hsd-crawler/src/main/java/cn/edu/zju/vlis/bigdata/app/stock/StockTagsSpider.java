package cn.edu.zju.vlis.bigdata.app.stock;

import cn.edu.zju.vlis.bigdata.SpiderContainer;
import cn.edu.zju.vlis.bigdata.common.UrlFactory;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by wangxiaoyi on 15/10/14.
 *
 * crawl data for stock tags
 *
 */

public class StockTagsSpider implements SpiderContainer {

    private Config conf = null;

    private Spider spider = null;
    private ExecutorService service = null;
    private Pipeline pipeline = null;
    private PageProcessor processor = null;
    private String spiderName = null;



    @Override
    public Spider getSpider() {
        return this.spider;
    }

    @Override
    public void startSpider() {
        spider.run();//todo: asyn
        ((SinaStockPipeLine)pipeline).saveToDbAfterClose();
    }

    @Override
    public void initSpider() {
        this.service = Executors.newFixedThreadPool(4);
        conf = ConfigFactory.load();

        spiderName = "StockTagsSpider";

        processor = new SinaStockPageProcessor(conf);
        pipeline = new SinaStockPipeLine(service);
        spider = Spider.create(processor)
                .addUrl(new String[]{UrlFactory.getSinaStockClassUrl("class"),
                        UrlFactory.getSinaStockClassUrl("industry"),
                        UrlFactory.getSinaStockClassUrl("area")})
                .thread(4)
                .addPipeline(pipeline);
        spider.setExecutorService(service);
    }

    @Override
    public void stopSpider() {
       spider.close();
        //service.shutdown();
    }

    @Override
    public String getSpiderName() {
        return spiderName;
    }



    public static void main(String []args){
        StockTagsSpider stockTagsSpider = new StockTagsSpider();
        stockTagsSpider.initSpider();
        stockTagsSpider.startSpider();
    }



}
