package service;

import bean.Comment;
import bean.News;
import bean.TempNews;
import dao.NewsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.StringUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by Lee on 2017/5/7 0007.
 */
@Service
public class NewsService {

    @Autowired
    private NewsDao newsDao;

    public TempNews createNews(News news) {
        //检查news是否符合标准
        boolean res=checkNews(news);
        if (res){
            news.setDate(new Date());
            return newsDao.createNews(news);
        }else {
            return getTempNewsError(res);
        }


    }


    public TempNews updateNews(News news) {
        boolean res=checkNews(news);
        if (res){
            news.setDate(new Date());
            return newsDao.updateNews(news);
        }else {
            return getTempNewsError(res);
        }

    }

    public TempNews getAllNews() {
        TempNews tempNews=new TempNews();
        List<News> list=newsDao.getAllNews();
        if (list.get(0)!=null){
            tempNews.setNewsList(list);
            tempNews.setResult(true);
            return tempNews;
        }else {
            tempNews.setResult(false);
            return tempNews;
        }
    }

    public boolean deleteNews(News news) {
        boolean res=newsDao.deleteNews(news);
        return res;
    }

    public boolean deleteNewsById(String id) {
        boolean res=newsDao.deleteNewsById(id);
        return res;
    }

    public TempNews getNewsById(String id) {
        TempNews tempNews=new TempNews();
        News news=newsDao.getNewsById(id);
        if (news!=null){
            tempNews.setNews(news);
            tempNews.setResult(true);
            return tempNews;
        }else {
            tempNews.setResult(false);
            return tempNews;
        }
    }

    /**
     *
     * @param id 新闻id
     * @param comment 该新闻的评论实体
     * @return
     */
    public boolean comment(String id, Comment comment) {
        if (!checkComment(comment)){
            return false;
        }
        newsDao.comment(id,comment);
        return true;
    }



    //*********私有函数
    private TempNews getTempNewsError(boolean res){
        TempNews tempNews=new TempNews();
        tempNews.setResult(res);
        return tempNews;
    }

    private boolean checkNews(News news) {
        String title=news.getTitle();
        String content=news.getContent();
        String name=news.getNewsManagerName();
        if (StringUtil.isNotEmpty(title)&&StringUtil.isNotEmpty(content)&&StringUtil.isNotEmpty(name)){
            return true;
        }
        return false;
    }


    private boolean checkComment(Comment comment) {
        String content=comment.getContent();
        String name=comment.getUsername();
        if (StringUtil.isNotEmpty(content)&&StringUtil.isNotEmpty(name)){
            return true;
        }
        return false;
    }
}
