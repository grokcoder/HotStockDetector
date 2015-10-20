package cn.edu.zju.vlis.bigdata;

/**
 * Created by wangxiaoyi on 15/10/20.
 */

public interface UrlClassifier {

    /**
     * return the page type of the url
     * @param url
     * @return
     */
    PAGE_TYPE getPageTypeByUrl(String url);

}
