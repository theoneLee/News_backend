package bean;

import java.util.*;

/**
 * Created by Lee on 2017/5/3 0003.
 */
public class News {
    private Integer id;
    private String title;
    private Date date;
    private String newsManagerName;
    private String content;//使用富文本时，是利用上传图片的api，然后后端返回一个图片链接，前端拿到这个链接，直接嵌入文本的，而上传文本应该直接就是string化的html内容

    private Category category;

    private Set<Comment> commentList=new HashSet<>();

    public News() {
    }

    public News(String title, Date date, String newsManagerName, String content) {
        this.title = title;
        this.date = date;
        this.newsManagerName = newsManagerName;
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
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

    public Set<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(Set<Comment> commentList) {
        this.commentList = commentList;
    }

    //    public class Content {
//        private String newsContent;
//        private List<String> paths;//将图片上传到临时文件夹，然后返回一个string给这里使用
//
//        public String getNewsContent() {
//            return newsContent;
//        }
//
//        public void setNewsContent(String newsContent) {
//            this.newsContent = newsContent;
//        }
//
//        public List<String> getPaths() {
//            return paths;
//        }
//
//        public void setPaths(List<String> paths) {
//            this.paths = paths;
//        }
//    }
}
