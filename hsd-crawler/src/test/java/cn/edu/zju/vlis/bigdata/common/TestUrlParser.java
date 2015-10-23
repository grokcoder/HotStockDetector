package cn.edu.zju.vlis.bigdata.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by wangxiaoyi on 15/9/24.
 */
public class TestUrlParser {

    @Test
    public void testFetchTimeInfo(){

        String url = "http://finance.sina.com.cn/china/20150919/013123292488.shtml";
        String regex = "[/^]\\d{8}[/$]";
        String time = UrlParser.fetchTimeInfo(url, regex).get();
        time = time.substring(1, time.length() - 1);

        Assert.assertEquals("20150919", time);
    }

    @Test
    public void testFetchPageNum(){
        String url = "http://tech2ipo.com/news/1";
        String regex = "/[\\d]+";
        Assert.assertEquals(1, UrlParser.fetchPageNum(url, regex));
        Assert.assertEquals(123, UrlParser.fetchPageNum(url + "23", regex));
    }
}
