package dao;

import org.springframework.stereotype.Component;

/**
 * Created by Lee on 2017/5/8 0008.
 */
@Component
public class UserDao {

    /**
     * 暂时让username和password为lee的用户可以登录
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username, String password) {
        if (username.equals(password)){
            return true;
        }
        return false;
    }

    /**
     * //todo 如果是普通权限就设置为null，如果有权限就设置为NEWS_ADMIN，NEWS_MANAGER(即普通用户不会返回一个)
     * @param username
     * @return
     */
    public String getPermission(String username) {
        if (username.equals("lee")){
            return "NEWS_ADMIN";
        }
        return null;
    }
}
