package service;

import bean.user.CommonUser;
import bean.user.LoginedResult;
import dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * user业务相关，在这个里面再调用dao来
 * Created by Lee on 2017/5/7 0007.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    /**
     *
     * @param username
     * @param password
     * @return 返回一个LoginedResult，如果查到该用户为普通用户，就在包装LoginedResult对象时置permission为null
     */
    public LoginedResult login(String username, String password) {
        //调用dao来
        LoginedResult lr=new LoginedResult();
        if (userDao.login(username,password)){
            lr.setResult(true);
        }else {
            lr.setResult(false);
        }
        String p=userDao.getPermission(username);
        if (p!=null&&!p.equals("")){
           lr.setPermission(p);
        }
        return lr;
    }

    public CommonUser getUserByName(String userName) {
        CommonUser user=new CommonUser();
        user.setUsername(userName);
        user.setPassword("123");
        return user;
    }
}
