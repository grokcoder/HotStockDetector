package cn.edu.zju.vlis.bigdata.app.sina;

import cn.edu.zju.vlis.bigdata.app.sina.model.News;
import cn.edu.zju.vlis.bigdata.common.HsdConstant;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wangxiaoyi on 15/10/9.
 */
public class NewsPipeline implements Pipeline {

    private NewsDAO dao = null;

    private List<News> newsList = null;

    private boolean batchInsert = true;

    private int bathInsertSize = 100;// default


    public NewsPipeline(){
        dao = new NewsDAO();
        newsList = new LinkedList<>();
    }

    public void setBatchInsert(boolean batchInsert) {
        this.batchInsert = batchInsert;
    }

    public void setBathInsertSize(int bathInsertSize) {
        this.bathInsertSize = bathInsertSize;
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
        //System.err.println(news.getTitle() + news.getPublishDate());
        if(batchInsert) {
            synchronized (this) {
                if (news != null) {
                    newsList.add(news);
                    if (newsList.size() > bathInsertSize) {
                        List<News> temp = newsList;
                        newsList = new LinkedList<>();
                        dao.batchInertRecord(temp);
                    }
                }
            }
        }else {
            dao.insertRecord(news);
        }
    }

    public void saveToDbAfterClose(){
        dao.batchInertRecord(newsList);
    }


}
