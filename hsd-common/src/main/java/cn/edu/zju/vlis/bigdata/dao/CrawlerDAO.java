package cn.edu.zju.vlis.bigdata.dao;

import cn.edu.zju.vlis.bigdata.HsdConstant;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * Created by wangxiaoyi on 15/10/9.
 *
 * manipulate the database related to  crawler
 */
public class CrawlerDAO implements DAO{

    public static final Logger LOG = LoggerFactory.getLogger(CrawlerDAO.class);

    public static Config config = null;


    private Connection conn = null;

    public CrawlerDAO(){
        config = ConfigFactory.load();
        init();
    }


    /**
     * load the mysql jdbc driver
     */
    public void init(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url = config.getString(HsdConstant.DB_URL);
            String name = config.getString(HsdConstant.DB_USER_NAME);
            String password = config.getString(HsdConstant.DB_USER_PASSWORD);

            conn = DriverManager.getConnection(url, name, password);


        }catch (Exception cnfe){
            LOG.error(cnfe.getMessage());
        }
    }


    @Override
    public void update(String sql) {

    }

    @Override
    public void execute(String sql) {

    }

    @Override
    public void insert(String sql) {

    }

    @Override
    public List<Object> query(String sql) {
        return null;
    }


    public static void main(String []args)throws Exception{
        CrawlerDAO dao = new CrawlerDAO();
        Connection conn = dao.conn;

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from customer");

        while (rs.next()){
            int cid = rs.getInt(1);
            String fname = rs.getString(2);
            String lname = rs.getString(3);
            System.out.println(cid + " " + fname + " " + lname);
        }



    }


}
