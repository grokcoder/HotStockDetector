package cn.edu.zju.vlis.bigdata.orm;

import com.google.protobuf.ServiceException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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
        conf.set("hbase.zookeeper.property.clientPort", "2181");
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


    public void checkConnection() {
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
     *
     * @param obj object to to be persistent
     * @return encapsulated Put object
     * @throws NoRowKeyFoundException this exception happens when object without row attribute
     */
    public Put getPut(Object obj) throws NoRowKeyFoundException {
        Class cl = obj.getClass();

        Put put = null;
        //1. construct put object by rowkey

        Class scl = cl.getSuperclass();
        if(scl == null) throw new NoRowKeyFoundException("No RowKey Found for " + cl.getName());
        else {
            try {
                String row = (String) scl.getDeclaredFields()[0].get(obj);
                put = new Put(Bytes.toBytes(row));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        Field[] fields = cl.getDeclaredFields();

        //2. add other object for the put object
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value != null) {
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
            for (Object o : objects) {
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
     *
     * @param htd descriptor for hbase table
     */
    public void createTable(HTableDescriptor htd) {
        Admin admin = null;
        try {
            checkConnection();
            admin = connection.getAdmin();
            admin.createTable(htd);
        } catch (IOException e) {
            LOG.error("get admin error ", e);
            e.printStackTrace();
        }
    }

    /**
     * query from hbase and encapuslate the data into object of type clazz
     * @param clazz type of data
     * @param scan query specifications
     * @param tableName table for query
     * @param <T extends HBaseRecord> type for ans
     * @return list of data retrieved from table tableName
     */
    public <T> List<T> query(Class clazz , Scan scan, TableName tableName) {
        Method[] methods = clazz.getDeclaredMethods();
        Map<String, Method> nameToMethod = new HashMap<>();
        List<T> ans = new LinkedList<>();
        try {
            checkConnection();
            Table table = connection.getTable(tableName);
            ResultScanner scanner = table.getScanner(scan);
            Result result;
            while ((result = scanner.next()) != null) {
                Object obj = clazz.newInstance();
                while (result.advance()) {
                    Cell cell = result.current();
                    String column = Bytes.toString(cell.getQualifier());
                    column = "set" + Character.toUpperCase(column.charAt(0)) + column.substring(1);
                    String value = Bytes.toString(cell.getValue());

                    if(((HBaseRecord) obj).getRow() == null) {
                        String row = Bytes.toString(cell.getRow());
                        ((HBaseRecord) obj).setRow(row);
                    }

                    if (! nameToMethod.containsKey(column)) {
                        for (Method m : methods) {
                            if (m.getName().endsWith(column)) {
                                nameToMethod.put(column, m);
                                break;
                            }
                        }

                    }
                    nameToMethod.get(column).invoke(obj, value);
                }
                ans.add((T) obj);
            }

        } catch (IOException e) {
            LOG.error(e.getMessage());
        } catch (IllegalAccessException illa) {
            LOG.error(illa.getMessage());
        } catch (InvocationTargetException ite) {
            LOG.error(ite.getMessage());
        } catch (InstantiationException ie) {
            LOG.error(ie.getMessage());
        }
        return ans;
    }
}
