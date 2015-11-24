package cn.edu.zju.vlis.bigdata.orm;


/**
 * Created by wangxiaoyi on 15/11/24.
 */

public abstract class AbstractNoSqlDAO implements NoSqlDAO{

    /**
     * obtains connection from the database
     * @return the connection object for success else null
     */
    abstract boolean connect();

    /**
     * close the connection
     * @return true for close success else false
     */
    abstract boolean close();
}
