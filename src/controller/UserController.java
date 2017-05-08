package controller;

import bean.Response;
import bean.user.CommonUser;
import bean.user.LoginedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import security.IgnoreSecurity;
import security.TokenManager;
import security.web.WebContext;
import service.UserService;

/**
 *
 * Created by Lee on 2017/5/3 0003.
 */
@RestController
@RequestMapping("/user")
public class UserController {//todo：注册，登录，注销，修改密码；返回Response对象，前端根据返回的json进行渲染
    private static final String DEFAULT_TOKEN_NAME = "X-Token";

    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager tokenManager;


    /**
     * 当Permission存在时，Response对象里面包含permission字段，否则就只有username和token字段
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @IgnoreSecurity
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
            if (lr.getPermission()!=null&&!lr.getPermission().equals("")){
                commonUser.setPermission(lr.getPermission());
            }
            return new Response().success(commonUser);//todo 注意：客户端需要把该token写入cookie中(如果有permission也要写入)，之后每次请求都将token随请求头一起发送到服务端
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
    @RequestMapping(value = "/detail" ,method = RequestMethod.GET)
    public Response getUserDetail(){
        String token=WebContext.getRequest().getHeader(DEFAULT_TOKEN_NAME);
        String userName=tokenManager.getUserName(token);

        return new Response().success(userService.getUserByName(userName));
    }


}
