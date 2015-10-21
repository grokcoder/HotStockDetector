package cn.edu.zju.vlis.bigdata.filter;


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
    public FILTER_CODE filtrateURLByTime(long time) {
        if(time <= endTime && time >= startTime){
            return FILTER_CODE.INCLUDE;
        }else {
            if(time < startTime)
                return FILTER_CODE.EXCLUDE_NOT_GO_TO_NEXT;
            if(time > endTime)
                return FILTER_CODE.EXCLUED_GO_TO_NEXT;
        }
        return FILTER_CODE.EXCLUDE_NOT_GO_TO_NEXT;
    }



}
