package cn.edu.zju.vlis.bigdata.common;

/**
 * Created by wangxiaoyi on 15/9/24.
 *
 * construct the url to be crawled by different policies
 */

public class UrlFactory {

    /**
     * @param pageNum page num of "gncj"
     * @return url of sina "gncj" page at pageNum
     * URL example: http://roll.finance.sina.com.cn/finance/gncj/gncj/index_1.shtml
     * URL template: http://roll.finance.sina.com.cn/finance/gncj/gncj/index_num.shtml
     * construct the next url by change the num of index_num
     */
    public static String getSinaGNCJPageUrl(int pageNum){
        String root = "http://roll.finance.sina.com.cn/finance/gncj/gncj/index_";
        String tail = ".shtml";
        return root + pageNum + tail;
    }

    /**
     * @param className the name for the class of the stocks, such as: class, industry,
     * @return url for fetch the json type data of className
     */
    public static String getSinaStockClassUrl(String className){
        String root = "http://money.finance.sina.com.cn/q/view/newFLJK.php?param=";
        return root + className;
    }

    /**
     *
     * @param className
     * @param numOfStocks num of stocks to fetch under type of className
     * @return url for fetch json type data contains stocks under type of className
     */
    public static String getClassToStocksUrl(String className, int numOfStocks){
        String root = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?";
        return root + "&node=" + className + "&num=" + numOfStocks;

    }


}
