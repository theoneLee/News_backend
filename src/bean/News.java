package bean;

import java.util.Date;
import java.util.List;

/**
 * Created by Lee on 2017/5/3 0003.
 */
public class News {
    private Integer id;
    private String title;
    private Date date;
    private String newsManagerName;
    private Content content;

    public News() {
    }

    public News(String title, Date date, String newsManagerName, Content content) {
        this.title = title;
        this.date = date;
        this.newsManagerName = newsManagerName;
        this.content = content;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNewsManagerName() {
        return newsManagerName;
    }

    public void setNewsManagerName(String newsManagerName) {
        this.newsManagerName = newsManagerName;
    }





    private class Content {
        private String newsContent;
        private List<String> paths;//将图片上传到临时文件夹，然后返回一个string给这里使用

        public String getNewsContent() {
            return newsContent;
        }

        public void setNewsContent(String newsContent) {
            this.newsContent = newsContent;
        }

        public List<String> getPaths() {
            return paths;
        }

        public void setPaths(List<String> paths) {
            this.paths = paths;
        }
    }
}
