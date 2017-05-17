package dao;

import bean.user.CommonUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Lee on 2017/5/8 0008.
 */
@Repository
public class UserDao {//todo 要保证username是唯一的

    @Autowired
    private SessionFactory sessionFactory;

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username, String password) {
        String truePassword=getPasswordByName(username);
        if (truePassword.equals(password)){
            return true;
        }
        return false;
    }

    public String getPasswordByName(String username) {
        Session s= sessionFactory.getCurrentSession();
        Transaction tx=s.beginTransaction();
        Query query=s.createQuery("select c.password from CommonUser c where c.username=?");
        query.setString(0,username);
        String password= (String) query.uniqueResult();
        tx.commit();
        return password;
    }

    /**
     *
     * @param username
     * @return 普通用户会放回null或者“”
     */
    public String getPermission(String username) {
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        Query query=session.createQuery("select c.permission from CommonUser c where c.username=?");
        query.setString(0,username);
        String permission= (String) query.uniqueResult();
        tx.commit();
        return permission;
    }

    public CommonUser getUserByName(String userName) {
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        Query query=session.createQuery("from CommonUser where username=?");
        query.setString(0,userName);
        CommonUser user= (CommonUser) query.uniqueResult();
        tx.commit();
        return user;

    }

    public boolean createUser(CommonUser user) {
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        user.setPermission(null);
        session.save(user);
        tx.commit();
        return true;
    }

    public boolean createNewsManager(CommonUser user){
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        user.setPermission("NEWS_MANAGER");
        session.save(user);
        tx.commit();
        return true;
    }

    public boolean updatePassword(String username, String newPwd) {
        Session session=sessionFactory.getCurrentSession();
        Transaction tx=session.beginTransaction();
        CommonUser user=getUserByName(username);
        user.setPassword(newPwd);
        session.update(user);
        tx.commit();
        return true;
    }
}
