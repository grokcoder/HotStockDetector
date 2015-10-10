package cn.edu.zju.vlis.bigdata.store;

import cn.edu.zju.vlis.bigdata.app.sina.model.News;
import cn.edu.zju.vlis.bigdata.dao.BasicDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangxiaoyi on 15/10/9.
 */
public class NewsDAO extends BasicDAO{

    public static final Logger LOG = LoggerFactory.getLogger(NewsDAO.class);


    /**
     * fetch the data from the database
     * @param sql
     * @return
     */
    public List<News> executeQuery(String sql){
        List<News> newsList = new LinkedList<>();
        ResultSet rs = query(sql);
        try {
            while (rs.next()) {
                News news = new News();
                news.setTitle(rs.getString(2));
                news.setUrl(rs.getString(3));
                news.setPublishDate(rs.getString(4));
                news.setPublishMedia(rs.getString(5));
                news.setKeywords(rs.getString(6));
                news.setTags(rs.getString(7));
                news.setBody(rs.getString(8));
                newsList.add(news);
            }
        }catch (SQLException sqle){
            LOG.error("Query error : ", sqle);
        }
        return newsList;
    }

    /**
     * insert a piece of news into the database
     * @param news
     */
    public void insertRecord(News news){
        StringBuilder sql = new StringBuilder("insert into news(title, url, pub_date, pub_media, keywords, tags, body) values(?,?,?,?,?,?,?)");
        try {
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setString(1, news.getTitle());
            ps.setString(2, news.getUrl());
            ps.setString(3, news.getPublishDate());
            ps.setString(4, news.getPublishMedia());
            ps.setString(5, news.getKeywords());
            ps.setString(6, news.getTags());
            ps.setString(7, news.getBody());
            ps.execute();
        }catch (SQLException sqle){
            LOG.error("insert error", sqle);
        }
    }



    /**
     * insert the record into the database in a batch way
     * @param newsRecords
     */
    public void batchInertRecord(List<News> newsRecords){
        newsRecords.forEach(news -> insertRecord(news));
    }

    public static void main(String []args){
        NewsDAO dao = new NewsDAO();
        List<News> newsList = dao.executeQuery("select * from news");
        int a = 0;
        a ++;
    }


}
