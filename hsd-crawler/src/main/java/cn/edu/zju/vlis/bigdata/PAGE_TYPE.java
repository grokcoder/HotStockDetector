package cn.edu.zju.vlis.bigdata;

/**
 * Created by wangxiaoyi on 15/9/24.
 */
public enum PAGE_TYPE {
    /**index page of financial news index page of sina*/
    SINA_FINANCIAL_INDEX_PAGE,

    /**finacial news page of sina*/
    SINA_FINANCIAL_CONTENT_PAGE,

    //TODO:
    XUEQIU_STOCKS_CONTENT_PAGE,
    XUEQIU_STOCKS_DETAILS_PAGE,

    /**index of different stock classifications*/
    SINA_STOCK_TAGS_INDEX_PAGE,

    /**stocks with a spesific classification */
    SINA_STOCKS_WITH_SPECIFIC_CLASSIFICATION,


    /**
     * we just need three page type as follows
     */

    NOT_DEFINE,

    INDEX_PAGE,

    DETAIL_PAGE

}
