package util;

import bean.Category;
import bean.News;
import dao.CategoryDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * Created by Lee on 2017/5/20 0020.
 */
public class AddTestData {
    public static void main(String[] args) {
        //todo 将必备的数据加载（固定分类）
        //category,news
        Category hotCategory=new Category();
        hotCategory.setCategoryName("hot");
        hotCategory.setFlag(true);

        for (int i=0;i<20;i++){
            News hotNews1=new News();
            hotNews1.setTitle("hotNewsTitle"+i);
            hotNews1.setContent("hotNewsContent"+i);
            hotNews1.setDate(new Date());
            hotNews1.setNewsManagerName("admin"+i);

            hotCategory.getNewsList().add(hotNews1);//建立关联关系
            hotNews1.setCategory(hotCategory);
        }
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("util/applicationContext.xml");
        CategoryDao categoryDao= (CategoryDao) applicationContext.getBean("categoryDao");
        categoryDao.createCategory(hotCategory);//注意这里持久化后有无级联（一并提交News）


    }


}
