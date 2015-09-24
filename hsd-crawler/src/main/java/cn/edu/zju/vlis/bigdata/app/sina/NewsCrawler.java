package cn.edu.zju.vlis.bigdata.app.sina;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import us.codecraft.webmagic.Spider;


/**
 * Created by wangxiaoyi on 15/9/24.
 *
 * start the news crawler
 */
public class NewsCrawler {

    private static Config conf = null;

    public static void main(String []args){
        conf = ConfigFactory.load();


        Spider.create(new NewsPageProcessor(conf))
                .addUrl("http://roll.finance.sina.com.cn/finance/gncj/gncj/index_1.shtml")
                .thread(1)
                .run();
    }
}
