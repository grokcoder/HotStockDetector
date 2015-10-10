package cn.edu.zju.vlis.bigdata.app.sina.model;

/**
 * Created by wangxiaoyi on 15/9/25.
 *
 * store one piece of news
 */
public class News {

    private String title;

    private String url;

    private String publishDate;

    /**where the news published*/
    private String publishMedia;

    /**keywords = "kw1 + ',' + kw2 + ',' + kw3"*/
    private String keywords;

    private String tags;

    /**the content of the news*/
    private String body;



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

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
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


    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", publishMedia='" + publishMedia + '\'' +
                ", keywords='" + keywords + '\'' +
                ", tags='" + tags + '\'' +
                ", body='" + "body ..." + '\'' +
                ", body='" + "body ..." + '\'' +
                '}';
    }
}
