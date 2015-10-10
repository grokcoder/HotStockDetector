package cn.edu.zju.vlis.bigdata.store;

import cn.edu.zju.vlis.bigdata.app.sina.model.News;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by wangxiaoyi on 15/10/9.
 */
public class NewsDBPipeline implements Pipeline {

    private NewsDAO dao = null;

    public NewsDBPipeline(){
        dao = new NewsDAO();
    }

    /**
     * Process extracted results.
     *
     * @param resultItems
     * @param task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        News news = resultItems.get(HsdConstant.MODEL);
        if(news != null) {
            dao.insertRecord(news);
        }
    }
}
