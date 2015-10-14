package cn.edu.zju.vlis.bigdata;

import us.codecraft.webmagic.Spider;

/**
 * Created by wangxiaoyi on 15/10/13.
 *
 */


public interface SpiderContaniner {

    Spider getSpider();

    void startSpider();

    void initSpider();

    void stopSipder();

    String getSpiderName();
}
