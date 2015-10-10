package cn.edu.zju.vlis.bigdata.filter;

import cn.edu.zju.vlis.bigdata.common.UrlParser;

/**
 * Created by wangxiaoyi on 15/9/28.
 */
public class TimeRangeFilter implements Filter{

    long startTime = 0l;
    long endTime = 0l;

    public TimeRangeFilter(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public FILTER_CODE filtrateURL(String url, String regex) {

        String time = UrlParser.fetchTimeInfo(url, regex).get();
        //TODO: find better way to fetch time info from content url
        time = time.substring(1, time.length() - 1);
        Long t = Long.parseLong(time);
        if(t <= endTime && t >= startTime){
            return FILTER_CODE.INCLUDE;
        }else {
            if(t < startTime)
                return FILTER_CODE.EXCLUDE_NOT_GO_TO_NEXT;
            if(t > endTime)
                return FILTER_CODE.EXCLUED_GO_TO_NEXT;
        }
        return FILTER_CODE.EXCLUDE_NOT_GO_TO_NEXT;
    }

}
