package service;

import bean.Category;
import bean.News;
import dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lee on 2017/5/18 0018.
 */
@Service
public class CategoryService {
    @Autowired
    CategoryDao categoryDao;
    private final static int PAGESIZE=10;


    /**
     * 从dao分别拿到实体数据和生成链接所需要的东西。
     * 再封装成map对象并且加入队列
     * @param categoryName
     * @param pageSize
     * @return
     */
    public List<News> getCategoryNews(String categoryName, String pageSize) {
        int parseInt=Integer.parseInt(pageSize);
        if(parseInt>=0){
            List<News> list=categoryDao.getCategoryNews(categoryName,parseInt ,1);//查询pageSize条categoryName分类下的新闻
            List<News> res=new ArrayList<>();
            String link;
            for (News n:list){
                link="/news/"+n.getId();//这个链接可以查询对应id的新闻
                n.setLink(link);
                res.add(n);
            }
            return res;
        }

        return null;
    }

    public List<TempCategory> getMoreCategoryList() {
        List<Category> list=categoryDao.getMoreCategory();//查询flag为false的所有分类
        List<TempCategory> res=new ArrayList<>();
        String link,categoryName;
        for (Category c:list){
            //HashMap<String, String> map=new HashMap<>();
            categoryName=c.getCategoryName();
            link="/category/"+categoryName+"/"+PAGESIZE;//这个链接可以查询该分类下的新闻（即得到一个新闻列表）
            //map.put(categoryName,link);
            res.add(new TempCategory(categoryName,link));
        }
        //System.out.println("service:"+res);
        return res;
    }

    public List<News> getIndexNews() {
        int size=4;
        List<News> list=categoryDao.getIndexNews(size);//查询分类名为"hot"的4条新闻
        List<News> res=new ArrayList<>();
        String link;
        for (News n:list){
            //HashMap<String,News> map=new HashMap<>();
            link="/news/"+n.getId();//这个链接可以查询某个id的新闻
            n.setLink(link);
            //map.put("news",n);
            res.add(n);
        }
        return res;
    }


    public Category createCategory(Category category) {
        return categoryDao.createCategory(category);
    }

    public boolean deleteCategoryById(String id) {
        if (id!=null&&id.equals("")){
            int tid=Integer.valueOf(id);
            return categoryDao.deleteCategoryById(tid);
        }
        return false;

    }

    public Category updateCategoryById(String id,Category category) {
        if (id!=null&&id.equals("")){
            int tid=Integer.valueOf(id);
            category.setId(tid);
            return categoryDao.updateCategoryById(category);
        }
        return null;
    }

    class TempCategory{
        private String categoryName;
        private String link;

        public TempCategory(String categoryName, String link) {
            this.categoryName = categoryName;
            this.link = link;
        }

        public TempCategory() {
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
