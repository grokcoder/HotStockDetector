package cn.edu.zju.vlis.bigdata;

import cn.edu.zju.vlis.bigdata.orm.HBaseRecord;

/**
 * Created by wangxiaoyi on 15/11/24.
 *
 * just for test
 */
public class Student extends HBaseRecord{

    private String name;
    private String addr;
    private long phoneNumber;
    private int age;

    public void setName(String name) {
        this.name = name;
    }


    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAddr() {
        return addr;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }


}
