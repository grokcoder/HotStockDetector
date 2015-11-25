package cn.edu.zju.vlis.bigdata.orm;

import com.google.protobuf.ServiceException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by wangxiaoyi on 15/11/24.
 */
public class HBaseDAOImpl extends AbstractNoSqlDAO {

    private static final org.slf4j.Logger LOG =
            LoggerFactory.getLogger(HBaseDAOImpl.class.getSimpleName());


    private Connection connection;
    private Configuration conf;

    public HBaseDAOImpl() {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "10.214.208.11,10.214.208.12,10.214.208.13,10.214.208.14");
        conf.set("hbase.zookeeper.property.clientPort","2181");
        conf.set("node1", "10.214.208.11");
        conf.set("node2", "10.214.208.12");
        conf.set("node3", "10.214.208.13");
        conf.set("node4", "10.214.208.14");
        try {
            HBaseAdmin.checkHBaseAvailable(conf);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * obtains connection from the database
     *
     * @return the connection object for success else null
     */
    @Override
    boolean connect() {
        try {
            connection = ConnectionFactory.createConnection(conf);
        } catch (IOException ioe) {
            LOG.error("connect error ", ioe);
            return false;
        }
        return true;
    }

    /**
     * close the connection
     *
     * @return true for close success else false
     */
    @Override
    boolean close() {
        try {
            connection.close();
        } catch (IOException ioe) {
            LOG.error("close connection error ", connection);
            return false;
        }
        return true;
    }


    public void checkConnection(){
        if (connection == null) connect();
    }

    /**
     * store an object
     *
     * @param object    object to be stored
     * @param tableName name of table
     */
    @Override
    public void store(Object object, String tableName) {
        Table table = null;
        try {
            checkConnection();
            table = connection.getTable(TableName.valueOf(tableName));
            Objects.requireNonNull(table);
            Put put = getPut(object);
            table.put(put);
        } catch (Exception ioe) {
            LOG.error(ioe.getMessage());
            return;
        }
    }

    /**
     * generate object of Put by parsing data from param obj
     * these method require the obj have attribute "row" represents for RowKey in HBase
     * @param obj object to to be persistent
     * @return encapsulated Put object
     * @throws NoRowKeyFoundException this exception happens when object without row attribute
     *
     */
    public Put getPut(Object obj) throws NoRowKeyFoundException{
        Class cl = obj.getClass();

        Field[] fields = cl.getDeclaredFields();
        List<Field> atr = new LinkedList<>();
        Put put = null;
        //1. construct put object by rowkey
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            //System.out.println(name);
            if (name.equals("row")) {
                try {
                    if(field.get(obj) != null)
                        put = new Put(Bytes.toBytes(field.get(obj).toString()));
                }catch (IllegalAccessException illae){
                    LOG.error(illae.getMessage());
                }
            } else atr.add(field);
        }
        if(put == null) throw new NoRowKeyFoundException("No RowKey Found for " +  cl.getName());

        //2. add other object for the put object
        for (Field field : atr) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if(value != null) {
                    put.addColumn(Bytes.toBytes(cl.getSimpleName()),
                            Bytes.toBytes(field.getName()),
                            value.toString().getBytes());
                }
            } catch (IllegalAccessException iae) {
                LOG.error(iae.getMessage());
            }
        }
        return put;
    }

    /**
     * store list of object
     *
     * @param objects   list of object to be stored
     * @param tableName name of table
     */
    @Override
    public void store(List<Object> objects, String tableName) {
        Table table;
        try {
            checkConnection();
            table = connection.getTable(TableName.valueOf(tableName));
            Objects.requireNonNull(table);
            List<Put> puts = new LinkedList<>();
            for(Object o : objects){
                puts.add(getPut(o));
            }
            table.put(puts);
        } catch (Exception ioe) {
            LOG.error(ioe.getMessage());
            return;
        }
    }

    /**
     * create table with HTableDescriptor
     * @param htd
     */
    public void createTable(HTableDescriptor htd){
        Admin admin =  null;
        try {
            checkConnection();
            admin = connection.getAdmin();
            admin.createTable(htd);
        } catch (IOException e) {
            LOG.error("get admin error ", e);
            e.printStackTrace();
        }
    }
}
