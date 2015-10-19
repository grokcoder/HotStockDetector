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

    private List<SpiderContainer> spiderContainers = null;

    public void init(){
        LOG.info("Spider Master init ... ");
        spiderContainers.forEach(spider -> spider.initSpider());
    }

    public void startAll(){
        LOG.info("Starting all spiders");
        spiderContainers.forEach(spiderContainer -> spiderContainer.startSpider());
    }

    public void addSpider(SpiderContainer container){
        spiderContainers.add(container);
    }

    public void addAndStartSpider(SpiderContainer container){
        spiderContainers.add(container);
        container.startSpider();
    }


    public void shutdown(){
        spiderContainers.forEach(spiderContainer -> spiderContainer.stopSpider());
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
