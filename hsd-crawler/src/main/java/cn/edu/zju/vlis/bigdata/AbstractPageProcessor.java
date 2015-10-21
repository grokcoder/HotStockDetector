package cn.edu.zju.vlis.bigdata;

import cn.edu.zju.vlis.bigdata.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by wangxiaoyi on 15/10/20.
 * abstract page processor for add site filter and classifier
 * developer should extend this class to implement
 * process
 */

public abstract class AbstractPageProcessor implements PageProcessor, UrlClassifier{

    private static final Logger LOG = LoggerFactory.getLogger(AbstractPageProcessor.class);

    /**
     * for specify the crawl policies of this spider
     */
    protected Site site = null;

    /**
     * for decide whether the url should be crawled
     */
    protected Filter filter = null;


    public AbstractPageProcessor(Site site, Filter filter){
        this.site = site;
        this.filter = filter;
    }

    public AbstractPageProcessor(Site site){
        this.site = site;
        this.filter = null;
    }

    public AbstractPageProcessor(){

    }


    public AbstractPageProcessor setSite(Site site) {
        this.site = site;
        return this;
    }

    public AbstractPageProcessor setFilter(Filter filter) {
        this.filter = filter;
        return this;
    }


    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page
     */
    @Override
    public void process(Page page) {
        String url = page.getUrl().get();
        switch (getPageTypeByUrl(url)){
            case INDEX_PAGE: processIndexPage(page); break;
            case DETAIL_PAGE: processDetailPage(page); break;
            case NOT_DEFINE:
        }
    }

    /**
     * get the site settings
     *
     * @return site
     * @see Site
     */
    @Override
    public Site getSite() {
        return this.site;
    }

    /**
     * process the index page to fetch the url for crawling
     * @param page
     */
    public abstract void processIndexPage(Page page);

    /**
     * process the content page and fetch the related information
     * @param page
     */
    public abstract void processDetailPage(Page page);
}
