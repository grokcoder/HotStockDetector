package cn.edu.zju.vlis.bigdata;

import us.codecraft.webmagic.Spider;

/**
 * Created by wangxiaoyi on 15/10/13.
 *
 */


public interface SpiderContainer {

    /**
     * get the spider
     * @return
     */
    Spider getSpider();

    /**
     * start the spider
     */
    void startSpider();

    /**
     * init the spider
     */
    void initSpider();

    /**
     * stop the spider
     */
    void stopSpider();

    /**
     * get the spider name
     * @return
     */
    String getSpiderName();
}
