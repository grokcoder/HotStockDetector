package cn.edu.zju.vlis.bigdata.common;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangxiaoyi on 15/9/24.
 *
 * fetch info from the url
 */
public class UrlParser {

    /**
     * fetch data time related info form url
     * example: http://finance.sina.com.cn/china/20150919/013123292488.shtml
     * output : 20150919
     * @param url target url
     * @param regex
     * @return data in url
     */
    public static Optional<String> fetchTimeInfo(String url, String regex){

        Pattern r = Pattern.compile(regex);

        Matcher m = r.matcher(url);
        if (m.find( )) {
            Optional<String> time = Optional.of(m.group(0));
            return time;
        } else {
           return Optional.empty();
        }
    }

    /**
     * fetch the page num form a index page
     * example : http://tech2ipo.com/news/1
     * output : 1
     * @param url
     * @param regex
     * @return
     */
    public static int fetchPageNum(String url, String regex){

        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(url);
        if (m.find( )) {
            Optional<String> time = Optional.of(m.group(0));

            return Integer.valueOf(time.get().substring(1));
        } else {
            return -1;
        }
    }

}
