package cn.edu.zju.vlis.bigdata.app.huxiu;

import cn.edu.zju.vlis.bigdata.app.model.Article;
import cn.edu.zju.vlis.bigdata.dao.SpringDAOImpl;

/**
 * Created by wangxiaoyi on 15/10/22.
 */
public class HuXiuDAO extends SpringDAOImpl{

    public void insertRecord(Article article){

        jdbcTemplate.update("insert into reports (title, url, pub_date, pub_media, content, keywords) values (?,?,?,?,?,?)",
                article.getTitle(),
                article.getUrl(),
                article.getPubDate(),
                article.getPubMedia(),
                article.getContent(),
                article.getKeywords());
    }
}
