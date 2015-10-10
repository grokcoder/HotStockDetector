package cn.edu.zju.vlis.bigdata.filter;

/**
 * Created by wangxiaoyi on 15/9/28.
 */
public interface Filter {

    /**
     * filter the url
     * @param url url to fetch
     * @param regex expression to extract info we need
     * @return
     */
    FILTER_CODE filtrateURL(String url, String regex);
}
