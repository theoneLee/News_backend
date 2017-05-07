package controller;

import bean.News;
import bean.Response;
import bean.TempNews;
import org.springframework.web.bind.annotation.*;


/**
 * Created by Lee on 2017/5/3 0003.
 */
@RestController
@RequestMapping("/news")
public class NewsController {//todo：crud新闻，返回Response对象，前端根据返回的json进行渲染

    @RequestMapping(method = RequestMethod.POST)
    public Response createNews(@RequestBody News news){
        boolean result=newsService.createNews(news);
        if (result){
            return new Response().success();
        }else {
            return new Response().failure("createNews_failure");
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Response getNews(@PathVariable("id")String id){//返回对应id的news
        TempNews tn=newsService.getNewsById(id);
        if (tn.isResult()){
            return new Response().success(tn.getNews());
        }else {
            return new Response().failure("getNewsById_failure");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response getAllNews(){//返回所有news
        TempNews tn=newsService.getAllNews();
        if (tn.isResult()){
            return new Response().success(tn.getNewsList());
        }else {
            return new Response().failure("getAllNews_failure");
        }
    }
}
