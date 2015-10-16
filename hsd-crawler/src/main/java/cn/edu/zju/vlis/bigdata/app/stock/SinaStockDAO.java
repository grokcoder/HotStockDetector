package cn.edu.zju.vlis.bigdata.app.stock;

import cn.edu.zju.vlis.bigdata.dao.SpringDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by wangxiaoyi on 15/10/15.
 */
public class SinaStockDAO extends SpringDAOImpl{

    private static final Logger LOG = LoggerFactory.getLogger(SinaStockDAO.class);

    /**
     *
     * @param stockCode
     * @param tags
     */
    public void updateStockTagsByStockCode(String stockCode, List<String> tags){

        String queryStockTags = "select tags from stock where code = ?";
        String sql = "update stock set tags = ? where code = ?";
        String tag = null;
        try {
            tag = jdbcTemplate.queryForObject(queryStockTags,
                    new Object[]{stockCode}, String.class);
        }catch (EmptyResultDataAccessException ee){
            LOG.error("no stock with code " + stockCode + " found in database!");
            //LOG.error(stockCode);
        }

        StringBuilder sb = new StringBuilder();
        if(tag == null || tag.equals("")){
            tags.forEach(t -> sb.append(t).append(','));
        }else {
            String fileds[] = tag.split(",");
            Set<String> ts = new HashSet<>();
            for(int i = 0; i < fileds.length; ++ i){
                ts.add(fileds[i]);
            }
            tags.forEach(t -> ts.add(t));
            ts.forEach(t -> sb.append(t).append(','));
        }
        jdbcTemplate.update(sql, sb.toString(), stockCode);
    }
}
