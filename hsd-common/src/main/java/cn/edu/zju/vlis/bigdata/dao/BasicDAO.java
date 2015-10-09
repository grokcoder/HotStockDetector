package cn.edu.zju.vlis.bigdata.dao;

import cn.edu.zju.vlis.bigdata.HsdConstant;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by wangxiaoyi on 15/10/9.
 *
 * manipulate the database related to  crawler
 */
public class BasicDAO implements DAO{

    public static final Logger LOG = LoggerFactory.getLogger(BasicDAO.class);

    public static Config config = null;


    public Connection conn = null;

    public BasicDAO(){
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
        execute(sql);
    }

    @Override
    public void execute(String sql) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        }catch (SQLException sqle){
            LOG.error("Execute sql :  " + sql + " error ", sqle);
            try {
                conn.close();
            }catch (SQLException se){
                LOG.error("Connection close error ", se);
            }
        }
    }

    @Override
    public void insert(String sql) {
       execute(sql);
    }

    @Override
    public ResultSet query(String sql) {

        ResultSet rs = null;

        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        }catch (SQLException sqle){
            LOG.error("Query error : ", sqle);
            try {
                conn.close();
            }catch (SQLException se){
                LOG.error("Connection close error ", se);
            }
        }
        return rs;
    }



}
