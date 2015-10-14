package cn.edu.zju.vlis.bigdata.app.sina;

import cn.edu.zju.vlis.bigdata.app.sina.model.News;
import cn.edu.zju.vlis.bigdata.dao.SpringDAOImpl;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangxiaoyi on 15/10/13.
 * dao for news table in hsd database
 */


public class NewsDAO extends SpringDAOImpl{

    /**
     * insert a record into the database
     * @param news
     */
    public void insertRecord(News news){
        String sql =
                "insert into news(title, url, pub_date, pub_media, keywords, tags, body) values(?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql,
                news.getTitle(),
                news.getUrl(),
                news.getPublishDate(),
                news.getPublishMedia(),
                news.getKeywords(),
                news.getTags(),
                news.getBody());
    }

    /**
     * batch insert records into the databases
     * @param newsRecords
     */
    public void batchInertRecord(List<News> newsRecords){

        String sql =
                "insert into news(title, url, pub_date, pub_media, keywords, tags, body) values(?,?,?,?,?,?,?)";

        List<Object[]> batchArgs = new LinkedList<>();
        newsRecords.forEach(news -> {
            Object[] objects = new Object[]{
                    news.getTitle(),
                    news.getUrl(),
                    news.getPublishDate(),
                    news.getPublishMedia(),
                    news.getKeywords(),
                    news.getTags(),
                    news.getBody()};
            batchArgs.add(objects);
        });

        jdbcTemplate.batchUpdate(sql, batchArgs);
    }


    public void execute(String sql) {
        jdbcTemplate.execute(sql);
    }

}
