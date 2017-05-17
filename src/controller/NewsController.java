package controller;

import bean.Comment;
import bean.News;
import bean.Response;
import bean.TempNews;
import bean.user.CommonUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import security.IgnoreSecurity;
import security.TokenManager;
import security.web.WebContext;
import service.NewsService;


/**
 * Created by Lee on 2017/5/3 0003.
 */
@RestController
@RequestMapping("/news")
public class NewsController {//todo：crud新闻以及对新闻的评论（根据header的token判断用户），返回Response对象，前端根据返回的json进行渲染
    @Autowired
    private NewsService newsService;

    @Autowired
    private TokenManager tokenManager;

    @RequestMapping(method = RequestMethod.POST)
    //@IgnoreSecurity加上这个注解就会让该方法跳过检查
    public Response createNews(@RequestBody News news){
        boolean result=newsService.createNews(news);
        if (result){
            return new Response().success();
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

    @RequestMapping(value = "/{id}",method = RequestMethod.POST)
    @IgnoreSecurity
    public Response commentNews(@PathVariable("id")String id, @RequestBody Comment comment){
        String token=WebContext.getRequest().getHeader(DEFAULT_TOKEN_NAME);
        String username=tokenManager.getUserName(token);
        newsService.comment(id,comment,username);//todo comment和news的关系
    }

}
