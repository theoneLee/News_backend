package dao;

import bean.Category;
import bean.News;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Lee on 2017/5/18 0018.
 */
@Repository
public class CategoryDao {
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 首先可以查询全部实体，然后通过setFirstResult方法设置在全部实体中的起始点，再设置setMaxResults来限制记录数（即每一页都含有pageSize条记录）
     query.setFirstResult((pageNo-1)*pageSize);
     query.setMaxResults(pageSize);

     * @param categoryName
     * @param pageSize
     * @param pageNo
     * @return
     */
    public List<News> getCategoryNews(String categoryName, int pageSize, int pageNo) {
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        Query query=session.createQuery("select n from News n where n.category.categoryName=?");//这个是查询『类别名为xx的所有News』，做分页操作
        query.setParameter(0,categoryName);
        query.setMaxResults(pageSize);
        query.setFirstResult((pageNo-1)*pageSize);
        List<News> list=query.list();
        tx.commit();
        return list;
    }

    public List<News> getIndexNews(int size) {
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        String hot="hot";
        Query query=session.createQuery("from News n where n.category.categoryName=? order by date desc ");//查询『热点』分类下的所有新闻，按时间降序排列
        query.setFirstResult(0);
        query.setMaxResults(size);
        query.setParameter(0,hot);
        List<News> list=query.list();
        tx.commit();
        return list;
    }


    public List<Category> getMoreCategory() {
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        Query query=session.createQuery("from Category c where c.flag=false");
        List<Category> list=query.list();
        System.out.println("dao:"+list);
        tx.commit();
        return list;
    }

    public Category createCategory(Category category) {
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        try {
            session.save(category);
            tx.commit();
            return category;//持久化之后在用这个category是包含一个hibernate分配的id的。
        }catch (Exception e){
            if (tx!=null){
                tx.rollback();
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean deleteCategoryById(int id) {
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        Category category=session.get(Category.class,id);
        session.delete(category);
        tx.commit();
        return true;
    }

    public Category updateCategoryById(Category category) {
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        session.update(category);
        tx.commit();
        return category;
    }
}
