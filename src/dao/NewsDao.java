package dao;

import bean.Comment;
import bean.News;
import bean.TempNews;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Lee on 2017/5/17 0017.
 */
@Repository
public class NewsDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void comment(String id, Comment comment) {
        //根据id找到新闻，然后直接关联Comment
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        try {
            Integer tid=Integer.valueOf(id);
            News news=session.get(News.class,tid);
            news.getCommentList().add(comment);//todo 要测试这里的comment和news有没有关联(没有的话可以看看一对多的配置文件)
            session.saveOrUpdate(comment);
            tx.commit();
        }catch (Exception e){
            if (tx!=null){
                tx.rollback();
                e.printStackTrace();
            }
        }

    }

    public TempNews createNews(News news) {
        TempNews tempNews=new TempNews();
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        session.save(news);
        tempNews.setNews(news);
        tx.commit();
        return tempNews;
    }

    public TempNews updateNews(News news) {
        TempNews tempNews=new TempNews();
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        session.update(news);
        tempNews.setNews(news);
        tx.commit();
        return tempNews;
    }


    public List<News> getAllNews() {
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        Query query=session.createQuery("from News");//todo 注意这里不需要List<Comment>
        List<News> list=query.list();
        tx.commit();
        return list;
    }

    public News getNewsById(String id) {
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        Integer tid=Integer.valueOf(id);
        News news=session.get(News.class,tid);
        tx.commit();
        return news;
    }

    public boolean deleteNewsById(String id) {
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        Integer tid=Integer.valueOf(id);
        News news=session.get(News.class,tid);
        session.delete(news);
        tx.commit();
        return true;
    }

    public boolean deleteNews(News news) {
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        session.delete(news);
        tx.commit();
        return true;
    }
}
