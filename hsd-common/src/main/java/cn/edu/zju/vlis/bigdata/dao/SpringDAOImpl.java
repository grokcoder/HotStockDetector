package cn.edu.zju.vlis.bigdata.dao;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by wangxiaoyi on 15/10/13.
 *
 * dao class for spring-jdbc
 */
public class SpringDAOImpl{

    protected JdbcTemplate jdbcTemplate;

    public SpringDAOImpl(){
        initJDBC();
    }

    public void initJDBC(){
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring-jdbc.xml");
        jdbcTemplate = context.getBean(JdbcTemplate.class);
    }


}
