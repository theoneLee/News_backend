package controller;

import bean.Response;
import bean.user.CommonUser;
import bean.user.LoginedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import security.TokenManager;
import service.UserService;

/**
 *
 * Created by Lee on 2017/5/3 0003.
 */
@RestController
@RequestMapping("/user")
public class UserController {//todo：注册，登录，注销，修改密码；返回Response对象，前端根据返回的json进行渲染
    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager tokenManager;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Response login(@RequestBody CommonUser user){
        String username=user.getUsername();
        String password=user.getPassword();
        LoginedResult lr=userService.login(username,password);//
        if (lr.isResult()){
            //登录成功后，创建token用来作为用户登录后的凭证，封装user并返回前端
            String token=tokenManager.createToken(username);
            CommonUser commonUser=new CommonUser();
            commonUser.setToken(token);
            commonUser.setUsername(username);
            commonUser.setPermission(lr.getPermission());
            return new Response().success(commonUser);
        }else {
            //新建一个Response并注入相关登录失败的相关信息
            return new Response().failure("login_failure");
        }
    }

//    @RequestMapping(value = "/logout",method = RequestMethod.GET)
//    public Response logout(){
//        //todo:https://my.oschina.net/huangyong/blog/521891 4.6里面有解决方案（登入登出的用户安全有效性）
//    }
//
//    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
//    public Response updatePassword(@RequestBody CommonUser user){
//        String truePWD=userDAO.getPassword(user);
//        String requestPWD=user.getPassword();
//        if (!requestPWD.trim().equals("")&&requestPWD.equals(truePWD)){
//            //todo:新建一个Response并注入相关修改密码成功的相关信息
//
//        }else {
//            //todo:新建一个Response并注入相关修改密码失败的相关信息
//        }
//        return null;
//    }



}
