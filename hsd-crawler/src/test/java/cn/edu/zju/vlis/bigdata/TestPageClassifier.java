package cn.edu.zju.vlis.bigdata;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by wangxiaoyi on 15/10/15.
 */
public class TestPageClassifier {



    @Test
    public void testGetPageType(){

        Assert.assertEquals(PAGE_TYPE.SINA_STOCK_TAGS_INDEX_PAGE,
                PageClassifier.getPageType("http://money.finance.sina.com.cn/q/view/newFLJK.php?param=class"));

        Assert.assertEquals(PAGE_TYPE.SINA_STOCKS_WITH_SPECIFIC_CLASSIFICATION,
                PageClassifier.getPageType("http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/Market_Center.getHQNodeData?&node=gn_jght&num=10000"));

        //TODO: test other page types
    }

}
