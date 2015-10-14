package cn.edu.zju.vlis.bigdata.app.xueqiu;

import cn.edu.zju.vlis.bigdata.PAGE_TYPE;
import cn.edu.zju.vlis.bigdata.PageClassifier;
import cn.edu.zju.vlis.bigdata.app.xueqiu.model.Stock;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import cn.edu.zju.vlis.bigdata.store.StockDAO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.*;


/**
 * Created by zhuohaizhen on 15-10-9.
 */
public class XueQiuPageProcessor implements PageProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(XueQiuPageProcessor.class);

    private Config conf = null;
    private Site site = null;

    public XueQiuPageProcessor(Config config) {
        this.conf = config;
        site = Site.me()
                .setRetryTimes(conf.getInt(HsdConstant.CRAWLER_RETRY_TIMES))
                .setSleepTime(conf.getInt(HsdConstant.CRAWLER_SLEEP_TIME))
                .setTimeOut(conf.getInt(HsdConstant.CRAWLER_TIME_OUT))
                .setUserAgent(conf.getString(HsdConstant.CRAWLER_USER_AGENT));
    }


    @Override
    public void process(Page page) {

        String currUrl = page.getUrl().get();
        LOG.info("Start process page " + currUrl);
        PAGE_TYPE type = PageClassifier.getPageType(currUrl);
        switch (type) {
            case XUEQIU_STOCKS_CONTENT_PAGE:
                processContentPage(page);
                break;
            case XUEQIU_STOCKS_DETAILS_PAGE:
                processDetailsPage(page);
                break;
        }
        LOG.info("process complete : " + currUrl);


    }






    public void processDetailsPage(Page page) {
        Stock stock = new Stock();
        Html html = page.getHtml();
        StockDAO stockDAO = new StockDAO();
        String regex_url = "http://xueqiu.com/S/";
        String sb;
        if (null == page.getUrl().regex(regex_url).toString()) {
            LOG.info("It is first page");
            List symbolList = new ArrayList<String>();
            symbolList.add("SH600019");
            for (int i = 0; i < symbolList.size(); i++) {
                page.addTargetRequest(regex_url + symbolList.get(i).toString());
            }
        } else {
            List<String> pageList = page.getHtml().xpath("//p[@class='companyInfo']/text()").all();
            String symbol = page.getUrl().toString().split("/")[4];
            stock.setSymbol(symbol);
            if(pageList.size()<3){
                LOG.error("the page should be deleted");
                stockDAO.deleteRecord(stock);
                return;
            }
            stock.setInformation(pageList.get(1));
            stock.setBusiness(pageList.get(2));
            stockDAO.updateRecord(stock);
            System.err.println(pageList.get(2));
        }
    }

    public void processContentPage(Page page) {
        //todo: fetch info form page
        Stock stock = new Stock();
        Html html = page.getHtml();
        String regex_url = "http://xueqiu\\.com/stock/cata/stocklist.json";

        if (null == page.getUrl().regex(regex_url).toString()) {
            LOG.error("the data of this page is not json");
            page.addTargetRequest("http://xueqiu.com/stock/cata/stocklist.json?size=90&order=asc&orderby=code&type=11%2C12&page=1");
            page.setSkip(false);
        } else {
            JSONObject jsonTemp = JSON.parseObject(page.getJson().toString());
            JSONArray jsonArray_stocks = (JSONArray) jsonTemp.get("stocks");
            StockDAO stockDAO = new StockDAO();
            //the num of stocks per page
            int numOfStocks = jsonArray_stocks.size();

            //while the size=0,it means that the json data has been download already!
            if (numOfStocks > 0) {
                int page_num = Integer.parseInt(page.getUrl().toString().split("page=")[1]) + 1;
                page.addTargetRequest(page.getUrl().toString().split("page=")[0] + "page=" + page_num);
            } else {
                return;
            }


            for (int i = 0; i < jsonArray_stocks.size(); i++) {
                jsonTemp = JSON.parseObject(jsonArray_stocks.getString(i));
                System.err.println(jsonTemp.getString("name"));
                stock.setSymbol(jsonTemp.getString("symbol"));
                stock.setCode(jsonTemp.getString("code"));
                stock.setName(jsonTemp.getString("name"));
                stockDAO.insertRecord(stock);
            }

        }


    }


    /**
     * fetch URL form page
     *
     * @param page
     * @param cssQuery css query expressions
     * @return list of URLs
     */
    public List<String> fetchUrls(Page page, String cssQuery) {
        List<String> urls = new LinkedList<>();
        Html html = page.getHtml();
        urls.addAll(html.css(cssQuery).links().all());
        return urls;
    }

    @Override
    public Site getSite() {
        return site;
    }
}
