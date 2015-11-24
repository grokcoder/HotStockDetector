package cn.edu.zju.vlis.bigdata.orm;

import org.apache.hadoop.hbase.client.Put;
import org.junit.*;

import java.io.IOException;

/**
 * Created by wangxiaoyi on 15/11/24.
 */
public class TestHBaseDAOImpl {

    private Student stu;

    public void init(){
        stu = new Student();
        stu.setName("wang xiaoyi");
        stu.setAddr("hangzhou china");
        stu.setPhoneNumber(13250813370l);
        stu.setAge(24);
        stu.setRow("1234567");
    }

    public TestHBaseDAOImpl(){

    }

    @org.junit.Test
    public void testGetPut() throws IOException{
        HBaseDAOImpl hBaseDAO = new HBaseDAOImpl();
        try {
            if(stu == null) init();
            Put put = hBaseDAO.getPut(stu);
            System.out.println(put.toJSON());
        }catch (NoRowKeyFoundException nrkfe){
            nrkfe.printStackTrace();
        }
    }
}
