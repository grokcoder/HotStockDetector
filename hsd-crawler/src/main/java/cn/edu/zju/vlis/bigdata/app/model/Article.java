package cn.edu.zju.vlis.bigdata.app.model;

/**
 * Created by wangxiaoyi on 15/10/21.
 *
 * report in the web of 36kr
 *
 * if this article data try to be persistent into HBase
 * than should make sure that the Article an a column family in the hbase data base
 *
 */
public class Article {

    private String title;
    private String url;
    private String pubDate;
    private String pubMedia;
    private String keywords;
    private String content;

    private String row;// for hbase row key

    public String getRowkey() {
        return row;
    }

    public void setRowkey(String rowkey) {
        this.row = rowkey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPubMedia() {
        return pubMedia;
    }

    public void setPubMedia(String pubMedia) {
        this.pubMedia = pubMedia;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", pubMedia='" + pubMedia + '\'' +
                ", keywords='" + keywords + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
