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

    /**
     * construct the index page of huxiu, for example:http://www.huxiu.com/v2_action/article_list?page=14&catid=2
     * @param pageNum for which page
     * @param catid for which category
     * @return the index page at "pageNum" and with category id "catid"
     */
    public static String getHuXiuIndexPage(int pageNum, int catid){
        String root = "http://www.huxiu.com/v2_action/article_list?page=";
        return root + pageNum + "&catid=" + catid;
    }


    /**
     * construct the detailed page fo huxiu, for example: http://www.huxiu.com/
     * @param pageInfo indicate the specified page
     * @return
     */
    public static String getHuXiuDetailPage(String pageInfo){
        String root = "http://www.huxiu.com";
        return root + pageInfo;
    }

    /**
     * construct the index page for tech2ipo index page
     * for example: originalUrl = http://tech2ipo.com/news/1, next url should be : http://tech2ipo.com/news/2
     * @param originalUrl
     * @return
     */
    public static String getTech2ipoNextIndexPage(String originalUrl){
        int nextPageNum = 1 + UrlParser.fetchPageNum(originalUrl, "/[\\d]+");
        String nextUrl = originalUrl.replaceAll("/[\\d]+", "/" + nextPageNum);
        return nextUrl;
    }

}
