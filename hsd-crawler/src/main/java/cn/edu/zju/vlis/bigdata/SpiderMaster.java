package cn.edu.zju.vlis.bigdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by wangxiaoyi on 15/10/13.
 *
 * manage different spiders
 */
public class SpiderMaster {


    private static final Logger LOG = LoggerFactory.getLogger(SpiderMaster.class.getName());

    private List<SpiderContaniner> spiderContaniners = null;

    public void init(){
        LOG.info("Spider Master init ... ");
        spiderContaniners.forEach(spider -> spider.initSpider());
    }

    public void startAll(){
        LOG.info("Starting all spiders");
        spiderContaniners.forEach(spiderContaniner -> spiderContaniner.startSpider());
    }

    public void addSpider(SpiderContaniner contaniner){
        spiderContaniners.add(contaniner);
    }

    public void addAndStartSpider(SpiderContaniner contaniner){
        spiderContaniners.add(contaniner);
        contaniner.startSpider();
    }


    public void shutdown(){
        spiderContaniners.forEach(spiderContaniner -> spiderContaniner.stopSipder());
    }


    public void startSpiderByClassName(String className){

    }

    public static void main(String []args){

        SpiderMaster spiderMaster = new SpiderMaster();
        spiderMaster.init();

        LOG.info("Starting all spiders");
        spiderMaster.startAll();

        System.out.println("hello world!");
    }



}
