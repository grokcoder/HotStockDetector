package cn.edu.zju.vlis.bigdata.app._36kr;

import cn.edu.zju.vlis.bigdata.app._36kr.model.Article;
import cn.edu.zju.vlis.bigdata.dao.SpringDAOImpl;

/**
 * Created by wangxiaoyi on 15/10/21.
 */
public class _36DAO extends SpringDAOImpl{

    public void insertRecord(Article article){

        jdbcTemplate.update("insert into reports (title, url, pub_date, pub_media, content) values (?,?,?,?,?)",
                article.getTitle(),
                article.getUrl(),
                article.getPubDate(),
                article.getPubMedia(),
                article.getContent());
    }

}
