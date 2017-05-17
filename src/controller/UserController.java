package controller;

import bean.Pwd;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Created by Lee on 2017/5/3 0003.
 */
@RestController
@RequestMapping("/user")
public class UserController {//注册，登录，注销，修改密码；返回Response对象，前端根据返回的json进行渲染
    private static final String DEFAULT_TOKEN_NAME = "X-Token";
    private static final String DEFAULT_PERMISSION_NAME = "X-Permission";

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
    public Response login(@RequestBody CommonUser user, HttpServletResponse httpServletResponse){
        String username=user.getUsername();
        String password=user.getPassword();
        LoginedResult lr=userService.login(username,password);//
        if (lr.isResult()){
            Response response=new Response();
            //登录成功后，创建token用来作为用户登录后的凭证，封装user并返回前端
            String token=tokenManager.createToken(username);
            CommonUser commonUser=new CommonUser();
            commonUser.setToken(token);
            commonUser.setUsername(username);

            Cookie tokenCookie=new Cookie(DEFAULT_TOKEN_NAME,token);
            tokenCookie.setMaxAge(-1);//关闭浏览器即清除Cookie
            httpServletResponse.addCookie(tokenCookie);//token写入cookie
            if (lr.getPermission()!=null&&!lr.getPermission().equals("")){
                commonUser.setPermission(lr.getPermission());
                Cookie permissionCookie=new Cookie(DEFAULT_PERMISSION_NAME,lr.getPermission());
                permissionCookie.setMaxAge(-1);
                httpServletResponse.addCookie(permissionCookie);//permission写入cookie
            }
            return response.success(commonUser);//todo 注意：之后客户端每次请求都将cookie中的token作为请求头，发送到服务端
        }else {
            //新建一个Response并注入相关登录失败的相关信息
            return new Response().failure("login_failure");
        }
    }

    @RequestMapping(value = "/signin" ,method = RequestMethod.POST)
    @IgnoreSecurity
    public Response signin(@RequestBody CommonUser user){
        boolean res=userService.signIn(user);
        if (res){
            return new Response().success();
        }
        return new Response().failure("signin_failure");
    }
    /**
     * 在header拿到该token，然后移除该token
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public Response logout(){
        String token=WebContext.getRequest().getHeader(DEFAULT_TOKEN_NAME);
        boolean result=tokenManager.removeToken(token);
        if (result){
            return new Response().success();
        }else {
            return new Response().failure("logout_failure");
        }
    }

    /**
     *
     * @param pwd 包含username，oldPwd，newPwd，checkPwd字段 且请求时要cookie保存的token放入header中
     * @return
     */
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)//一般来说真正意义上的RESTful是不允许路径存在动词的，然后CRUD对应post，get，put，delete
    public Response updatePassword(@RequestBody Pwd pwd){
        String username=pwd.getUsername();
        String oldPWD=pwd.getOldPwd();
        String newPwd=pwd.getNewPwd();
        String checkPwd=pwd.getCheckPwd();
        boolean res=userService.updatePassword(username,oldPWD,newPwd,checkPwd);
        if (res){
            return new Response().success();
        }
        return new Response().failure("updatePassword_failure");
    }
    @RequestMapping(value = "/detail" ,method = RequestMethod.GET)
    public Response getUserDetail(){
        String token=WebContext.getRequest().getHeader(DEFAULT_TOKEN_NAME);
        String userName=tokenManager.getUserName(token);

        return new Response().success(userService.getUserByName(userName));
    }


}
