package cn.edu.zju.vlis.bigdata.app.xueqiu.model;

import cn.edu.zju.vlis.bigdata.store.StockDAO;

import java.util.List;

/**
 * Created by zhuohaizhen on 15-10-12.
 *
 * Store the information of stock
 */
public class Stock {

    private String symbol;

    private String code;

    private String name;

    private String information;

    private String business;

    private String tags;

    private String keywords;

    private String body;


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getInformation() { return information; }

    public void setInformation(String information) { this.information = information; }

    public String getBusiness() { return business; }

    public void setBusiness(String business) { this.business = business; }

    public String getTags() { return tags; }

    public void setTags(String tags) { this.tags = tags; }

    public String getKeywords() { return keywords; }

    public void setKeywords(String keywords) { this.keywords = keywords; }

    public String getBody() { return body; }

    public void setBody(String body) { this.body = body; }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", information='" + information + '\'' +
                ", business='" + business + '\'' +
                ", tags='" + tags + '\'' +
                ", keywords='" + keywords + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

}
