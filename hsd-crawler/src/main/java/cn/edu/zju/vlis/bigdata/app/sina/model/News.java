package cn.edu.zju.vlis.bigdata.app.sina.model;

import java.util.Date;

/**
 * Created by wangxiaoyi on 15/9/25.
 *
 * store one piece of news
 */
public class News {

    private String title;

    private Date publishDate;

    /**where the news published*/
    private String publishMedia;

    /**the content of the news*/
    private String body;

    /**keywords = "kw1 + ' ' + kw2 + ' ' + kw3"*/
    private String keywords;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getPublishMedia() {
        return publishMedia;
    }

    public void setPublishMedia(String publishMedia) {
        this.publishMedia = publishMedia;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", publishDate=" + publishDate +
                ", publishMedia='" + publishMedia + '\'' +
                ", body='" + body + '\'' +
                ", keywords='" + keywords + '\'' +
                '}';
    }
}
