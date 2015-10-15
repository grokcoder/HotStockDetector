package cn.edu.zju.vlis.bigdata.app.stock;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by wangxiaoyi on 15/10/14.
 */

public class SinaStockPipeLine implements Pipeline{

    /**
     * Process extracted results.
     *
     * @param resultItems
     * @param task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {

        System.out.println("result size" + resultItems.getAll().size());


    }
}
