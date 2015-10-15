package cn.edu.zju.vlis.bigdata.app.stock;

import cn.edu.zju.vlis.bigdata.PAGE_TYPE;
import cn.edu.zju.vlis.bigdata.PageClassifier;
import cn.edu.zju.vlis.bigdata.app.stock.model.TagToStocks;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import cn.edu.zju.vlis.bigdata.common.UrlFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
                .setUserAgent(conf.getString(HsdConstant.CRAWLER_USER_AGENT))
                .setCharset(HsdConstant.SINA_STOCK_TAGS_CHARSET);
    }



    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page
     */
    @Override
    public void process(Page page) {

        PAGE_TYPE type = PageClassifier.getPageType(page.getUrl().toString());
        switch (type){
            case SINA_STOCK_TAGS_INDEX_PAGE:
                processIndexPage(page);break;

            case SINA_STOCKS_WITH_SPECIFIC_CLASSIFICATION:
                processContentPage(page);break;

            default: LOG.error("no such processor found for this page " + page.getUrl());
        }

    }

    /**
     * page example: http://money.finance.sina.com.cn/q/view/newFLJK.php?param=class
     * try to get the class info and construct related url for fetch stocks under that class
     * @param page
     */
    public void processIndexPage(Page page){

        String content = page.getJson().get();
        int index = content.indexOf("= {");
        content = content.substring(index + 2);

        JSONObject jsonObject = JSON.parseObject(content);

        Set<String> keys = jsonObject.keySet();

        keys.forEach(key -> {
            String ob = (String)jsonObject.get(key);
            String[] fields = ob.split(",");

            String className = fields[0];
            String classChineseName =  fields[1];
            int numOfStocks = 10000;
                    //Integer.valueOf(fields[3]);
            System.out.println(className + "\t" + classChineseName + "\t" + numOfStocks);

            Request request = new Request(UrlFactory.getClassToStocksUrl(className, numOfStocks));
            request.putExtra("TAG_NAME", classChineseName);

            page.addTargetRequest(request);
        });
    }


    /**
     * page example : http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?&node=gn_jght&num=10000
     * contains the stocks under tag of gn_jght
     * @param page
     */
    public void processContentPage(Page page){

        String content = page.getJson().get();
        List<String> codes = new LinkedList<>();
        JSONArray jsonArray = JSON.parseArray(content);
        jsonArray.forEach(stock ->
                codes.add((String) ((JSONObject) stock).get("code")));

        String tag = (String)page.getRequest().getExtra("TAG_NAME");

        TagToStocks tagToStocks = new TagToStocks(tag, codes);
        page.putField(HsdConstant.MODEL, tagToStocks);

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
