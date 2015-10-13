package cn.edu.zju.vlis.bigdata.dao;

import java.sql.ResultSet;

/**
 * Created by wangxiaoyi on 15/10/9.
 */
@Deprecated
public interface DAO {


    void update(String sql);

    void execute(String sql);

    void insert(String sql);

    ResultSet query(String sql);

}


