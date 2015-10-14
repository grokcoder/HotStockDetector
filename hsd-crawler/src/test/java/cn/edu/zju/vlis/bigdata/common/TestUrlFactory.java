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

    @Test
    public void testGetSinaStockClassUrl(){
        String curl = UrlFactory.getSinaStockClassUrl("class");
        String expUrl = "http://money.finance.sina.com.cn/q/view/newFLJK.php?param=class";
        Assert.assertEquals(expUrl, curl);
    }

    @Test
    public void testGetClassToStocksUrl(){
        String curl = UrlFactory.getClassToStocksUrl("gn_jght", 10000);
        String expUrl = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?&node=gn_jght&num=10000";
        Assert.assertEquals(expUrl, curl);
    }





}
