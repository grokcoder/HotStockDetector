package cn.edu.zju.vlis.bigdata.store;

import cn.edu.zju.vlis.bigdata.app.sina.model.News;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangxiaoyi on 15/10/13.
 */
public class TestNewsDAO {



    private NewsDAO dao = new NewsDAO();


    private static List<News> newses = new LinkedList<>();

    static {
        for(int i = 0; i < 10; ++ i){
            News news = new News();
            news.setBody("body" + i);
            news.setKeywords("keywords" + i);
            news.setPublishDate("date" + i);
            news.setPublishMedia("media" + i);
            news.setTags("tags" + i);
            news.setTitle("title" + i);
            news.setUrl("url" + i);
            newses.add(news);
        }
    }

    //@Test
    public void testInertNews(){
        dao.insertRecord(newses.get(0));
        //dao.insertRecord();
    }

    @Test
    public void testBatchInertNews(){
        dao.batchInertRecord(newses);
    }



}
