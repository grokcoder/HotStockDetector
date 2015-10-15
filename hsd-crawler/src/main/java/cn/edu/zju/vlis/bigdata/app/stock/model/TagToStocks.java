package cn.edu.zju.vlis.bigdata.app.stock.model;

import java.util.List;

/**
 * Created by wangxiaoyi on 15/10/15.
 *
 * store the relationship between tags and stocks' codes
 *
 */
public class TagToStocks {

    private String tagName;

    private List<String> stocksCode;

    public TagToStocks(String tagName, List<String> stocksCode){
        this.tagName = tagName;
        this.stocksCode = stocksCode;
    }

    public String getTagName() {
        return tagName;
    }

    public List<String> getStocksCode() {
        return stocksCode;
    }
}
