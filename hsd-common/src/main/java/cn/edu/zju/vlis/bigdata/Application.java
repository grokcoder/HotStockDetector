package cn.edu.zju.vlis.bigdata;

import cn.edu.zju.vlis.bigdata.orm.HBaseDAOImpl;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Scan;

/**
 * Created by wangxiaoyi on 15/9/28.
 */


public class Application {


    public void createHBaseTable() {
        String name = "crawlers_data";

        HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(name));
        HColumnDescriptor hc1 = new HColumnDescriptor("Article");
        // HColumnDescriptor hc2 = new HColumnDescriptor("financial_news");
        htd.addFamily(hc1);
        // htd.addFamily(hc2);

        HBaseDAOImpl dao = new HBaseDAOImpl();
        dao.createTable(htd);

    }

    public static void query(){
        String name = "crawlers_data";
        Scan scan = new Scan();
    }


    public static void main(String[] args) {

        new Application().createHBaseTable();

    }

}
