package cn.edu.zju.vlis.bigdata.app.xueqiu;

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
        Spider.create(new XueQiuPageProcessor(conf))
                .addUrl("http://xueqiu.com/hq#exchange=CN&firstName=1&secondName=1_0")
                .thread(1)
                .run();
    }
}
