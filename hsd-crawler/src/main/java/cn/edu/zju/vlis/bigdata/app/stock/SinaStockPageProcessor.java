package cn.edu.zju.vlis.bigdata.app.stock;

import cn.edu.zju.vlis.bigdata.PAGE_TYPE;
import cn.edu.zju.vlis.bigdata.PageClassifier;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by wangxiaoyi on 15/10/14.
 *
 */


public class SinaStockPageProcessor  implements PageProcessor{

    public static final Logger LOG = LoggerFactory.getLogger(SinaStockPageProcessor.class);


    private Config conf = null;
    private Site site = null;

    public SinaStockPageProcessor(Config config){

        this.conf = config;
        site = Site.me()
                .setRetryTimes(conf.getInt(HsdConstant.CRAWLER_RETRY_TIMES))
                .setSleepTime(conf.getInt(HsdConstant.CRAWLER_SLEEP_TIME))
                .setTimeOut(conf.getInt(HsdConstant.CRAWLER_TIME_OUT))
                .setUserAgent(conf.getString(HsdConstant.CRAWLER_USER_AGENT));
    }



    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page
     */
    @Override
    public void process(Page page) {
        String url = page.getUrl().toString();

        PAGE_TYPE type = PageClassifier.getPageType(url);
        switch (type){
            case SINA_STOCK_TAGS_INDEX_PAGE: break;

            case SINA_STOCKS_WITH_SPECIFIC_CLASSIFICATION: break;

            default: LOG.error("no such processor found for this page " + url);
        }

        int a = 9;

    }

    /**
     * get the site settings
     *
     * @return site
     * @see Site
     */
    @Override
    public Site getSite() {
        return this.site;
    }
}
