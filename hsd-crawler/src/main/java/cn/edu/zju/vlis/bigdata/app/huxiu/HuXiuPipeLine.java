package cn.edu.zju.vlis.bigdata.app.huxiu;

import cn.edu.zju.vlis.bigdata.app.model.Article;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by wangxiaoyi on 15/10/22.
 */
public class HuXiuPipeLine implements Pipeline{

    HuXiuDAO dao = null;

    public HuXiuPipeLine(){
        dao = new HuXiuDAO();
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
