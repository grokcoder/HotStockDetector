package cn.edu.zju.vlis.bigdata.app.tech2ipo;

import cn.edu.zju.vlis.bigdata.app.model.Article;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by wangxiaoyi on 15/10/23.
 */
public class Tech2ipoPipeline implements Pipeline{

    Tech2ipoDAO dao = null;

    public Tech2ipoPipeline(){
        dao = new Tech2ipoDAO();
    }

    /**
     * Process extracted results.
     *
     * @param resultItems
     * @param task
     */
    @Override
    public void process(ResultItems resultItems, Task task) {

        Article article = resultItems.get(HsdConstant.MODEL);
        if(article != null){
            dao.insertRecord(article);
        }
    }

}
