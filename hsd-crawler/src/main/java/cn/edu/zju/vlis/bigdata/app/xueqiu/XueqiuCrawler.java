package cn.edu.zju.vlis.bigdata.app.xueqiu;

import cn.edu.zju.vlis.bigdata.app.xueqiu.model.Stock;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import us.codecraft.webmagic.Spider;

/**
 * Created by zhuohaizhen on 15-10-9.
 */
public class XueqiuCrawler {
    private static Config conf = null;
    public static void main(String []args){
        conf = ConfigFactory.load();
        /*get the data from xueqiu
        Spider.create(new XueQiuPageProcessor(conf))
                .addUrl("http://xueqiu.com/")
                .thread(4)
                .run();
         */

        //get the detailed data from xueqiu
        Spider.create(new XueQiuPageProcessor(conf))
                .addUrl("http://xueqiu.com/hq#exchange=CN&firstName=1&secondName=1_0")
                .thread(4)
                .run();




    }

    public void updateIfNULL()
    {

    }

    public void CrawDetailedStockInfo(Stock stock)
    {
        Spider.create(new XueQiuPageProcessor(conf))
                .addUrl("http://xueqiu.com/hq#exchange=CN&firstName=1&secondName=1_0")
                .thread(4)
                .run();
    }
}
