package cn.edu.zju.vlis.bigdata.orm;

/**
 * Created by wangxiaoyi on 15/11/25.
 * this is the base class for
 * all Hbase data module to extend
 */
public class HBaseRecord {

    protected String row;

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }
}
