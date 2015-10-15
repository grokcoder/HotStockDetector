package cn.edu.zju.vlis.bigdata.app.stock;

import cn.edu.zju.vlis.bigdata.app.stock.model.TagToStocks;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created by wangxiaoyi on 15/10/14.
 */

public class SinaStockPipeLine implements Pipeline{

    private SinaStockDAO dao = null;


    private volatile Map<String, List<String>> stocksToTags = null;
    public static final int NO_RECEIVE_RECORD_TIME = 30 * 1000;

    private long lastReceiveResultTime = 0;

    public SinaStockPipeLine(ExecutorService service){
        lastReceiveResultTime = System.currentTimeMillis();
        stocksToTags = new HashMap<>();
        dao = new SinaStockDAO();
       // service.submit(new DBA());
    }


    public Map<String, List<String>> getStocksToTags() {
        return stocksToTags;
    }




    /**
     * Process extracted results.
     *
     * @param resultItems
     * @param task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {

        TagToStocks tagToStocks = resultItems.get(HsdConstant.MODEL);
        if(tagToStocks != null) {

            List<String> codes = tagToStocks.getStocksCode();
            String tag = tagToStocks.getTagName();

            codes.forEach(code -> {
                if (stocksToTags.containsKey(code)) {
                    stocksToTags.get(code).add(tag);
                } else {
                    List<String> cl = new LinkedList<>();
                    cl.add(tag);
                    stocksToTags.put(code, cl);
                }
            });
            lastReceiveResultTime = System.currentTimeMillis();
            stocksToTags.forEach((stockCode, tags) -> dao.updateStockTagsByStockCode(stockCode, tags));
        }
    }


    class DBA implements Runnable{

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            while (true){
                if(System.currentTimeMillis() - lastReceiveResultTime > NO_RECEIVE_RECORD_TIME){
                    Map<String, List<String>> stocks = stocksToTags;
                    stocksToTags = new HashMap<>(); // for new received record
                    stocks.forEach((stockCode, tags) -> dao.updateStockTagsByStockCode(stockCode, tags));
                }
            }
        }
    }
}
