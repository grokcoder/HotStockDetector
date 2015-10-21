package cn.edu.zju.vlis.bigdata.app._36kr;

import cn.edu.zju.vlis.bigdata.app._36kr.model.Article;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by wangxiaoyi on 15/10/20.
 */
public class _36KrPipeline implements Pipeline{

    private _36DAO dao = null;

    public _36KrPipeline(){
        dao = new _36DAO();
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
