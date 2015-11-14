package cn.edu.zju.vlis.bigdata.dao;

import cn.edu.zju.vlis.bigdata.HsdConstant;
import com.typesafe.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by wangxiaoyi on 15/11/14.
 *
 * to manage the Connection ThreadLocal
 */
public class ConnectionManager {


    public static Config config = null;

    public static ThreadLocal<Connection> conn = new ThreadLocal<Connection>(){

        public Connection initialValue(){

            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String url = config.getString(HsdConstant.DB_URL);
            String name = config.getString(HsdConstant.DB_USER_NAME);
            String password = config.getString(HsdConstant.DB_USER_PASSWORD);

            Connection connection = null;
            try {
                connection = DriverManager.getConnection(url, name, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return connection;
        }

    };

}
