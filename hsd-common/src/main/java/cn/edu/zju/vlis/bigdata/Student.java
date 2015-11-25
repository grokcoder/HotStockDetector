package cn.edu.zju.vlis.bigdata;

/**
 * Created by wangxiaoyi on 15/11/24.
 *
 * just for test
 */
public class Student {

    private String name;
    private String addr;
    private long phoneNumber;
    private int age;

    private String row; // always should have this attr

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

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
