package cn.edu.zju.vlis.bigdata;

import cn.edu.zju.vlis.bigdata.dao.SpringDAOImpl;

/**
 * Created by wangxiaoyi on 15/9/28.
 */


public class Application extends SpringDAOImpl{


    public void test(){
        jdbcTemplate.execute("select * from test");
    }



    public static void main(String []args){
      new Application().test();
    }

}
