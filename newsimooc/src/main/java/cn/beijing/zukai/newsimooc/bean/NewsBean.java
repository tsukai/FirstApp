package cn.beijing.zukai.newsimooc.bean;

/**
 * Created by zukai on 2015/06/12.
 */
public class NewsBean {
    private String newsIconUrl;
    private String newTitle;
    private String newsContent;

    public String getNewsContent() {
        return newsContent;
    }

    public NewsBean setNewsContent(String newsContent) {
        this.newsContent = newsContent;
        return this;
    }

    public String getNewsIconUrl() {
        return newsIconUrl;
    }

    public NewsBean setNewsIconUrl(String newsIconUrl) {
        this.newsIconUrl = newsIconUrl;
        return this;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public NewsBean setNewTitle(String newTitle) {
        this.newTitle = newTitle;
        return this;
    }
}
