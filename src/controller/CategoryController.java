package controller;

import bean.Category;
import bean.Response;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Lee on 2017/5/18 0018.
 */
@RestController
@RequestMapping(value = "/category")
public class CategoryController {//todo crud(注意r时要确保保留分类不用查询出来)


    /**
     *
     * @return 除了保留分类外的所有分类（包含categoryName和请求链接）
     * 封装为List<HashMap<categoryName,link>>
     */
    public Response getMoreCategoryList(){

    }

    /**
     *
     * @return 查询最新『热点』分类下的4条新闻，以及请求这些新闻的链接
     */
    public Response getIndexNews(){

    }

    /**
     *
     * @return 查询最新10条对应『类别』的新闻，以及请求这些新闻的链接,如果有更多则返回（总页数和当前页数）
     */
    public Response getCategoryNews(String categoryName){

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
        boolean res=categoryService.deleteNewsById(id);
        if (res){
            return new Response().success();
        }
        return new Response().failure("deleteCategoryById_failure");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Response updateCategory(@PathVariable("id")String id){
        Category res=categoryService.updateCategoryById(id);
        if (res!=null&&res.getId()>0){
            return new Response().success(res);
        }
        return new Response().failure("updateCategoryById_failure");
    }
}
