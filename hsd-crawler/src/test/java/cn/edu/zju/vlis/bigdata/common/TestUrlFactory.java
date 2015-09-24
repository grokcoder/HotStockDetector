package cn.edu.zju.vlis.bigdata.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by wangxiaoyi on 15/9/24.
 */
public class TestUrlFactory {

    @Test
    public void testGetSinaGNCJPageUrl(){
        for(int i = 1; i< 100; i ++){
            String url = UrlFactory.getSinaGNCJPageUrl(i);
            String t = "http://roll.finance.sina.com.cn/finance/gncj/gncj/index_" + i + ".shtml";
            Assert.assertEquals(t, url);
        }
    }
}
