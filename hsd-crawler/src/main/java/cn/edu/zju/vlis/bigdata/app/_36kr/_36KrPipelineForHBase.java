package cn.edu.zju.vlis.bigdata.app._36kr;

import cn.edu.zju.vlis.bigdata.app.model.Article;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import cn.edu.zju.vlis.bigdata.orm.HBaseDAOImpl;
import cn.edu.zju.vlis.bigdata.orm.NoSqlDAO;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wangxiaoyi on 15/11/25.
 */
public class _36KrPipelineForHBase  implements Pipeline{


    private NoSqlDAO dao = null;
    private AtomicInteger aid = new AtomicInteger(0);

    public _36KrPipelineForHBase(){
        dao = new HBaseDAOImpl();
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
            int id = aid.incrementAndGet();
            article.setRowkey(String.format("%10d", id));
            dao.store(article, "crawlers_data");
        }
    }
}
