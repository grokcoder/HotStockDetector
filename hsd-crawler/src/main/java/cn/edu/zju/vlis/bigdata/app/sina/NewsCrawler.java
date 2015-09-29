package cn.edu.zju.vlis.bigdata.app.sina;

import cn.edu.zju.vlis.bigdata.filter.Filter;
import cn.edu.zju.vlis.bigdata.filter.TimeRangeFilter;
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
        Filter filter = new TimeRangeFilter(20150928, 20150929);

        NewsPageProcessor npp = new NewsPageProcessor(conf);
        npp.setFilter(filter);

        Spider.create(npp)
                .addUrl("http://roll.finance.sina.com.cn/finance/gncj/gncj/index_1.shtml")
                .thread(1)
                .run();
    }
}
