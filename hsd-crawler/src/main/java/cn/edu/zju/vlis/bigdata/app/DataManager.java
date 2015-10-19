package cn.edu.zju.vlis.bigdata.app;

import cn.edu.zju.vlis.bigdata.common.UrlParser;
import cn.edu.zju.vlis.bigdata.dao.SpringDAOImpl;
import java.util.*;

/**
 * Created by wangxiaoyi on 15/10/19.
 *
 * manage the data stored by crawler
 * 1.add the missing values
 * 2.remove duplicate records
 */
public class DataManager extends SpringDAOImpl{


    /**
     * remove the duplicate records for news by check the url
     * if the record is out of the memory limits
     * should we find a better solution
     */
    public void removeDuplicateRecordsForNewsByUrl(){

        List<Map<String, Object>> rs = jdbcTemplate.queryForList("select nid, url from news");

        List<Object[]> duplicateNids = new LinkedList<>();
        Set<String> urls = new LinkedHashSet<>();
        rs.forEach(map -> {
            if(urls.contains(map.get("url"))){
                duplicateNids.add(new Object[]{map.get("nid")});
            }else {
                urls.add((String)map.get("url"));
            }
        });
        jdbcTemplate.batchUpdate("delete from news where nid = ?", duplicateNids);
    }


    /**
     * for news without pub_date
     * extract date from url and update
     * the news's pub_date with it
     */
    public void addPubDateForNewsWithoutDateFromUrl(){

        List<Map<String, Object>> rs = jdbcTemplate.queryForList("select nid, url from news where pub_date like \"%/\" ");

        List<Object[]> args = new LinkedList<>();

        rs.forEach(m -> {
            String url = (String)m.get("url");
            String pubDate = UrlParser.fetchTimeInfo(url, "[/^]\\d{8}[/$]").get();
            pubDate = pubDate.substring(1, pubDate.length() - 1);
            Object [] ojs = new Object[]{pubDate, m.get("nid")};
            args.add(ojs);
        });

        jdbcTemplate.batchUpdate("update news set pub_date = ? where nid = ? ", args);

    }


    public static void main(String []args){
       // new DataManager().removeDuplicateRecordsForNewsByUrl();
        new DataManager().addPubDateForNewsWithoutDateFromUrl();
    }

}
