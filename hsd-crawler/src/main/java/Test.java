import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * Created by wangxiaoyi on 15/9/23.
 */
public class Test {


    public static void main(String []args){
        Logger logger = LogManager.getLogger(Test.class.getName());
        logger.info("Test");


        Config conf = ConfigFactory.load();
        logger.info(conf.getString("b.hbase.retru"));


    }


}
