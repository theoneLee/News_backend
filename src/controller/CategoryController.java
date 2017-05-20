package controller;

import bean.Category;
import bean.News;
import bean.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import security.IgnoreSecurity;
import service.CategoryService;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Lee on 2017/5/18 0018.
 */
@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * 已测试
     * @return 除了保留分类外的所有分类（包含categoryName和请求链接）
     * 封装为List<HashMap<categoryName,link>>
     */
    @RequestMapping(value = "/more",method = RequestMethod.GET)
    @IgnoreSecurity
    public Response getMoreCategoryList(){
        List<HashMap<String,String>> list=categoryService.getMoreCategoryList();
        if (list!=null&&list.size()>0){
            return new Response().success(list);
        }
        return new Response().failure("getMoreCategoryList_failure");
    }

    /**
     * 已测试
     * @return 查询最新『热点』分类下的4条新闻，以及请求这些新闻的链接
     */
    @RequestMapping(value = "/hot",method = RequestMethod.GET)
    @IgnoreSecurity
    public Response getIndexNews(){
        List<HashMap<News,String>> list=categoryService.getIndexNews();
        if (list!=null&&list.size()>0){
            return new Response().success(list);
        }
        return new Response().failure("getIndexNews_failure");
    }

    /**
     * 已测试
     * pageSize 值为10.
     * @return 查询最新10条对应『类别』的新闻，以及请求这些新闻的链接,
     *todo 前端接受到这个数据后固定显示『查询更多』的按钮，没按一次页数加1，然后将接受到的json数据通过js函数显示，但如果json为null时就不再显示这个按钮，注意该按钮在刷新后值会重置
     */
    @RequestMapping(value = "/{categoryName}/{pageSize}",method = RequestMethod.GET)
    @IgnoreSecurity
    public Response getCategoryNews(
            @PathVariable("categoryName")String categoryName,
            @PathVariable("pageSize")String pageSize ){
        List<HashMap<News,String>> list=categoryService.getCategoryNews(categoryName,pageSize);
        if (list!=null&&list.size()>0){
            return new Response().success(list);
        }
        return new Response().failure("getCategoryNews_failure");
    }

    @RequestMapping(method = RequestMethod.POST)
    public Response createCategory(@RequestBody Category category){
        Category res=categoryService.createCategory(category);
        if (res!=null&&res.getId()>0){//这里用该实体持久化后hibernate给他生成的id来判断是否操作成功，而不是像News和User那样建一个临时类
            return new Response().success(res);
        }
        return new Response().failure("createCategory_failure");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Response deleteCategory(@PathVariable("id")String id){
        boolean res=categoryService.deleteCategoryById(id);
        if (res){
            return new Response().success();
        }
        return new Response().failure("deleteCategoryById_failure");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Response updateCategory(@PathVariable("id")String id,@RequestBody Category category){
        Category res=categoryService.updateCategoryById(id,category);
        if (res!=null&&res.getId()>0){
            return new Response().success(res);
        }
        return new Response().failure("updateCategoryById_failure");
    }
}
