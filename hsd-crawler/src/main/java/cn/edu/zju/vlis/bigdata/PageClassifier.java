package cn.edu.zju.vlis.bigdata;


/**
 * Created by wangxiaoyi on 15/9/24.
 * mark the page with a specific classification
 */
public class PageClassifier {

    /**
     * get the url page type
     * TODO: should every app to impl their own PageClassifer to avoid too much String match computation?
     * @param url
     * @return
     */
    public static PAGE_TYPE getPageType(String url){
        if(url.contains("http://roll.finance.sina.com.cn")){
            if(url.contains("index")){
                return PAGE_TYPE.SINA_FINANCIAL_INDEX_PAGE;
            }else {
                return PAGE_TYPE.NOT_DEFINE;
            }
        }else if(url.contains("xueqiu")){
            if(url.contains("stcoklist.json")) {
                return PAGE_TYPE.XUEQIU_STOCKS_CONTENT_PAGE;
            }else {
                return PAGE_TYPE.XUEQIU_STOCKS_DETAILS_PAGE;//todo
            }
        } else if(url.contains("http://money.finance.sina.com.cn/q/view/newFLJK.php?param=")){
            return PAGE_TYPE.SINA_STOCK_TAGS_INDEX_PAGE;
        } else if(url.contains("http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php")){
            return PAGE_TYPE.SINA_STOCKS_WITH_SPECIFIC_CLASSIFICATION;
        } else {
            //todo: more accuratre
            return PAGE_TYPE.SINA_FINANCIAL_CONTENT_PAGE;
        }
    }



}
