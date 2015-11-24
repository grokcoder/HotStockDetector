package cn.edu.zju.vlis.bigdata.orm;

import java.util.List;

/**
 * Created by wangxiaoyi on 15/11/24.
 * dao interface for NoSQL database
 */
public interface NoSqlDAO {
    /**
     * store an object
     * @param object object to be stored
     * @param tableName name of table
     */
    void store(Object object, String tableName);

    /**
     * store list of object
     * @param objects list of object to be stored
     * @param tableName name of table
     */
    void store(List<Object> objects, String tableName);
}
