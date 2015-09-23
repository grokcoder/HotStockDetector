import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.LogManager;

/**
 * Created by wangxiaoyi on 15/9/23.
 */
public class Test {

    public static void main(String []args){

        Logger logger = LogManager.getLogger(Test.class.getName());
        System.out.println("hello");
        logger.error("ssss");
    }
}
