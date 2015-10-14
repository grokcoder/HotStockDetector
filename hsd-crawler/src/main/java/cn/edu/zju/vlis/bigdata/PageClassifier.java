package cn.edu.zju.vlis.bigdata;


/**
 * Created by wangxiaoyi on 15/9/24.
 */
public class PageClassifier {

    /**
     * get the url page type
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
            }else
            {
                return PAGE_TYPE.XUEQIU_STOCKS_DETAILS_PAGE;//todo
            }
        } else {
            //todo: more accuratre
            return PAGE_TYPE.SINA_FINANCIAL_CONTENT_PAGE;
        }
    }



}
