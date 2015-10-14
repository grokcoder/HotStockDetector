package cn.edu.zju.vlis.bigdata.app.xueqiu;


import cn.edu.zju.vlis.bigdata.app.xueqiu.model.Stock;
import cn.edu.zju.vlis.bigdata.dao.SpringDAOImpl;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by zhuohaizhen on 15-10-12.
 * dao for stock table in hsd database
 */
public class StockDAO extends SpringDAOImpl{
    /**
     * insert a record into the database
     * @param Stock
     */
    public void insertRecord(Stock stock){
        String sql = "insert into stock(symbol,code,name,information,business,tags,keywords,body) values(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                stock.getSymbol(),
                stock.getCode(),
                stock.getName(),
                stock.getInformation(),
                stock.getBusiness(),
                stock.getTags(),
                stock.getKeywords(),
                stock.getBody());
    }

    public List getAllSymbols(){
        return jdbcTemplate.queryForList("select symbol from stock");
    }

    public void deleteRecord(Stock stock) {
        String sql = "delete from stock where symbol=?";
        jdbcTemplate.update(sql,
                stock.getSymbol());
    }

    public void updateRecord(Stock stock){
        String sql = "update stock set information=?,business=?,tags=?,keywords=?,body=? where symbol=?";
        jdbcTemplate.update(sql,
                stock.getInformation(),
                stock.getBusiness(),
                stock.getTags(),
                stock.getKeywords(),
                stock.getBody(),
                stock.getSymbol());
    }

    /**
     * batch insert records into the databases
     * @param stockRecords
     */
    public void batchInertRecord(List<Stock> stockRecords){

        String sql = "insert into stock(symbol,code,name,information,business,tags,keywords,body) values(?,?,?,?,?,?,?,?)";

        List<Object[]> batchArgs = new LinkedList<>();
        stockRecords.forEach(stock -> {
            Object[] objects = new Object[]{
                    stock.getSymbol(),
                    stock.getCode(),
                    stock.getName(),
                    stock.getInformation(),
                    stock.getBusiness(),
                    stock.getTags(),
                    stock.getKeywords(),
                    stock.getBody()};

            batchArgs.add(objects);
        });

        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    /**
     * batch update records into the databases
     * @param stockRecords
     */
    public void batchUpdateRecordBySymbol(List<Stock> stockRecords){
        String sql = "update stock set information=?,set business=?,set tags=?,set keywords=?,set body=? where symbol=?";
        List<Object[]> batchArgs = new LinkedList<>();
        stockRecords.forEach(stock -> {
            Object[] objects = new Object[]{
                    stock.getInformation(),
                    stock.getBusiness(),
                    stock.getTags(),
                    stock.getKeywords(),
                    stock.getBody(),
                    stock.getSymbol()};

            batchArgs.add(objects);
        });

        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    public void execute(String sql) {
        jdbcTemplate.execute(sql);
    }
}
