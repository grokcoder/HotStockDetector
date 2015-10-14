package cn.edu.zju.vlis.bigdata.app.sina;

import cn.edu.zju.vlis.bigdata.filter.Filter;
import cn.edu.zju.vlis.bigdata.filter.TimeRangeFilter;
import cn.edu.zju.vlis.bigdata.store.NewsDBPipeline;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import us.codecraft.webmagic.Spider;

/**
 * Created by wangxiaoyi on 15/9/24.
 *
 * start the news crawler
 */


public class NewsSpider {

    private static Config conf = null;

    private Filter filter = null;

    public void setConfig(Config conf){
        this.conf = conf;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }



    public static void main(String []args){
        conf = ConfigFactory.load();
        Filter filter = new TimeRangeFilter(20151009, 20151012);

        NewsPageProcessor npp = new NewsPageProcessor(conf);
        npp.setFilter(filter);

        Spider.create(npp)
                .addUrl("http://roll.finance.sina.com.cn/finance/gncj/gncj/index_1.shtml")
                .addPipeline(new NewsDBPipeline())
                .thread(1)
                .run();
    }
}
