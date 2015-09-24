package cn.edu.zju.vlis.bigdata.app;

import cn.edu.zju.vlis.bigdata.NAV_BAR;
import cn.edu.zju.vlis.bigdata.app.sina.NewsPageProcessor;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by wangxiaoyi on 15/9/24.
 */
public class TestNewsPageProcessor {

    @Test
    public void testGetNextPageUrl(){
        String currUrl = "http://roll.finance.sina.com.cn/finance/gncj/gncj/index_10.shtml";

        NewsPageProcessor processor = new NewsPageProcessor();
        String nextUrl = processor.getNextPageUrl(currUrl, NAV_BAR.GNCJ);
        Assert.assertEquals("http://roll.finance.sina.com.cn/finance/gncj/gncj/index_11.shtml", nextUrl);
    }
}
