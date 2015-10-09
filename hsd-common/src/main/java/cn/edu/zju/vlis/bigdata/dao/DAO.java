package cn.edu.zju.vlis.bigdata.dao;

import java.util.List;

/**
 * Created by wangxiaoyi on 15/10/9.
 */
public interface DAO {


    void update(String sql);

    void execute(String sql);

    void insert(String sql);

    List<Object> query(String sql);

}


