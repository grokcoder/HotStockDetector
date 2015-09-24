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
}
