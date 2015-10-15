package cn.edu.zju.vlis.bigdata.app.stock;

import cn.edu.zju.vlis.bigdata.dao.SpringDAOImpl;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by wangxiaoyi on 15/10/15.
 */
public class SinaStockDAO extends SpringDAOImpl{


    /**
     *
     * @param stockCode
     * @param tags
     */
    public void updateStockTagsByStockCode(String stockCode, List<String> tags){

        String queryStockTags = "select tags from stock where code = ?";
        String sql = "update stock set tags = ? where code = ?";

        System.err.println("query " + stockCode);
        String tag = jdbcTemplate.queryForObject(queryStockTags,
                new Object[]{stockCode},
                (rs, rowNum) -> {
                    return rs.getString(1);
                });


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
        //TODO: find a batch solution
        jdbcTemplate.update(sql, sb.toString(), stockCode);
    }


    public static void main(String []args){
        List<String> tags = new LinkedList<>();
        tags.add("t1");
        tags.add("t2");
        new SinaStockDAO().updateStockTagsByStockCode("000001", tags);
    }


}
