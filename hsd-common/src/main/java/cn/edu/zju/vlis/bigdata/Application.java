package cn.edu.zju.vlis.bigdata;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;

/**
 * Created by wangxiaoyi on 15/9/28.
 */
public class Application extends JdbcDaoSupport{


    private JdbcTemplate jdbcTemplate;


    @Override
    protected JdbcTemplate createJdbcTemplate(DataSource dataSource) {
        return super.createJdbcTemplate(dataSource);
    }

    public void execute(String sql){
        jdbcTemplate.execute(sql);
    }








    public static void main(String []args){




        Application crawlerEventDao = new Application();
        crawlerEventDao.execute("select * from test");










    }

}
