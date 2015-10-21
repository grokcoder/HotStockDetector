package cn.edu.zju.vlis.bigdata.filter;

/**
 * Created by wangxiaoyi on 15/9/28.
 */
public interface Filter {

    /**
     * filter the url
     * @param time time of the url
     * @return
     */
    FILTER_CODE filtrateURLByTime(long time);




}
