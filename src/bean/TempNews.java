package bean;

import java.util.List;

/**
 * 在NewsService查找News时返回的结果
 * Created by Lee on 2017/5/7 0007.
 */
public class TempNews {
    private News news;
    private List<News> newsList;
    private boolean result;

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
