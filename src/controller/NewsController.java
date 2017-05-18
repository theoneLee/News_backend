package controller;

import bean.Comment;
import bean.News;
import bean.Response;
import bean.TempNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import security.IgnoreSecurity;
import security.TokenManager;
import service.NewsService;


/**
 * Created by Lee on 2017/5/3 0003.
 */
@RestController
@RequestMapping("/news")
public class NewsController {//todo 搜索（模糊查询）
    private static final String DEFAULT_TOKEN_NAME="X-Token";//todo：crud新闻以及对新闻的评论（根据header的token判断用户），返回Response对象，前端根据返回的json进行渲染
    @Autowired
    private NewsService newsService;

//    @Autowired
//    private TokenManager tokenManager;

    @RequestMapping(method = RequestMethod.POST)
    //@IgnoreSecurity加上这个注解就会让该方法跳过检查
    public Response createNews(@RequestBody News news){
        TempNews tn=newsService.createNews(news);
        if (tn.isResult()){
            return new Response().success(tn.getNews());
        }else {
            return new Response().failure("createNews_failure");
        }
    }


    /**
     *
     * 因为getNews时前端会取到一个news，再请求修改这个news时把这个news的id一并发送到这里
     * @param news
     * @return
     */
    //@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @RequestMapping(method = RequestMethod.PUT)
    public Response updateNews(@RequestBody News news){
        TempNews tn=newsService.updateNews(news);
        if (tn.isResult()){
            return new Response().success(tn.getNews());
        }
        return new Response().failure("updateNews_failure");
    }

    //todo 之后要做分页
    @RequestMapping(method = RequestMethod.GET)
    @IgnoreSecurity
    public Response getAllNews(){//返回所有news
        TempNews tn=newsService.getAllNews();
        if (tn.isResult()){
            return new Response().success(tn.getNewsList());
        }else {
            return new Response().failure("getAllNews_failure");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public Response deleteNews(@RequestBody News news){
        boolean res=newsService.deleteNews(news);
        if (res){
            return new Response().success();
        }
        return new Response().failure("deleteNews_failure");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Response deleteNewsById(@PathVariable("id")String id){
        boolean res=newsService.deleteNewsById(id);
        if (res){
            return new Response().success();
        }
        return new Response().failure("deleteNewsById_failure");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @IgnoreSecurity
    public Response getNews(@PathVariable("id")String id){//返回对应id的news
        TempNews tn=newsService.getNewsById(id);
        if (tn.isResult()){
            return new Response().success(tn.getNews());
        }else {
            return new Response().failure("getNewsById_failure");
        }
    }

    /**
     *
     * @param id 新闻id
     * @param comment 对该新闻的评论
     * @return "please login"即要求登录，"comment_failure"即表示评论失败，成功返回
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.POST)
    //@IgnoreSecurity
    public Response commentNews(@PathVariable("id")String id, @RequestBody Comment comment){
//        String token=WebContext.getRequest().getHeader(DEFAULT_TOKEN_NAME);
//        String username=tokenManager.getUserName(token);
//        if (token==null||username==null||username.equals("")){
//            //提示登录
//           return new Response().failure("please login");
//        }//把验证token部分交给SecurityAspect去做
        boolean res=newsService.comment(id,comment);
        if (res){
            return new Response().success();
        }else {
            return new Response().failure("comment_failure");
        }
    }

}
