package cn.edu.zju.vlis.bigdata.app._36kr;

import cn.edu.zju.vlis.bigdata.app.model.Article;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import cn.edu.zju.vlis.bigdata.orm.HBaseDAOImpl;
import cn.edu.zju.vlis.bigdata.orm.NoSqlDAO;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.yarn.util.SystemClock;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wangxiaoyi on 15/11/25.
 */
public class _36KrPipelineForHBase  implements Pipeline{


    private NoSqlDAO dao = null;
    private AtomicLong aid = new AtomicLong(System.currentTimeMillis());

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
            int id = (int) aid.incrementAndGet();
            article.setRow(String.format("%10d", id));
            dao.store(article, "crawlers_data");
        }
    }


    public void testQuery(){
        List<Article> ans = ((HBaseDAOImpl) dao).
                query(Article.class, new Scan(), TableName.valueOf("crawlers_data"));
        ans.forEach(a -> System.out.println(a.getTitle()));
    }

    public static void main(String []args){
        new _36KrPipelineForHBase().testQuery();
    }
}
