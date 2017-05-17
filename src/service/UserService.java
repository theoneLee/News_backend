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
        CommonUser user=userDao.getUserByName(userName);
        return user;
    }

    public boolean updatePassword(String username, String oldPWD, String newPwd, String checkPwd) {
        String password=userDao.getPasswordByName(username);
        if (password.equals(oldPWD)&&newPwd.equals(checkPwd)){
            boolean res=userDao.updatePassword(username,newPwd);
            return res;
        }
        return false;
    }

    public boolean signIn(CommonUser user) {
        if (userDao.getUserByName(user.getUsername())!=null){
            return false;//若已存在该username，则不允许注册
        }
        boolean res=userDao.createUser(user);
        return res;
    }
}
