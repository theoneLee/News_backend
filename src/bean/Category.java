package bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻分类
 * Created by Lee on 2017/5/18 0018.
 */
public class Category {
    private Integer id;
    private String categoryName;
    private boolean flag;//false表示允许删除和修改
    private List<News> newsList=new ArrayList<>();

    public Category() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}
