package cn.edu.zju.vlis.bigdata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangxiaoyi on 15/10/21.
 */
public class DateParser {

    /**
     * @param date
     * @return the time represented as micro seconds
     *  -1 means error
     */
    public static long parseDateBySchema(String date, String format){
        if(date == null || format == null) return -1;

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = sdf.parse(date);
        }catch (ParseException pe){

        }
        if(d != null)
            return d.getTime();
        else {
            return -1;
        }
    }


}
