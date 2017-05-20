package bean;

import java.util.HashSet;
import java.util.Set;

/**
 * 新闻分类
 * Created by Lee on 2017/5/18 0018.
 */
public class Category {
    private Integer id;
    private String categoryName;
    private boolean flag;//false表示允许删除和修改
    private Set<News> newsList=new HashSet<>();

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

    public Set<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(Set<News> newsList) {
        this.newsList = newsList;
    }
}
