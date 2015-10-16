package cn.edu.zju.vlis.bigdata.app.stock;

import cn.edu.zju.vlis.bigdata.common.UrlFactory;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import us.codecraft.webmagic.Spider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by wangxiaoyi on 15/10/14.
 *
 * crawl data for stock tags
 *
 */


public class StockTagsSpider {

    private static Config conf = null;

    public static void main(String []arg){

        conf = ConfigFactory.load();
        SinaStockPageProcessor processor = new SinaStockPageProcessor(conf);


        ExecutorService service = Executors.newCachedThreadPool();

        SinaStockPipeLine pipeLine = new SinaStockPipeLine(service);


        Spider spider = Spider.create(processor)
                .addUrl(new String[]{UrlFactory.getSinaStockClassUrl("class"),
                        UrlFactory.getSinaStockClassUrl("industry"),
                        UrlFactory.getSinaStockClassUrl("area")})
                .thread(1)
                .addPipeline(pipeLine);

        spider.setExecutorService(service);
        spider.run();

        pipeLine.saveToDbAfterClose();
        service.shutdown();





    }




}
