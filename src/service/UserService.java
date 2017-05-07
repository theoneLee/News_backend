package service;

import bean.user.LoginedResult;
import org.springframework.stereotype.Service;

/**
 * user业务相关，在这个里面再调用dao来
 * Created by Lee on 2017/5/7 0007.
 */
@Service
public class UserService {

    public LoginedResult login(String username, String password) {
        //调用dao来
    }
}
