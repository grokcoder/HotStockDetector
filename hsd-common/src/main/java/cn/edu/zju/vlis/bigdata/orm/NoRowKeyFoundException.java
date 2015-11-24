package cn.edu.zju.vlis.bigdata.orm;

/**
 * Created by wangxiaoyi on 15/11/24.
 *
 * todo: write specification
 */
public class NoRowKeyFoundException extends Exception{


    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NoRowKeyFoundException(String message) {
        super(message);
    }



}
