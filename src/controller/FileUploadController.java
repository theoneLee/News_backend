package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.Arrays;
import java.util.UUID;

/**
 * 图片上传已Servlet3.0的方式（使用富文本）
 * http://www.kancloud.cn/wangfupeng/wangeditor2/113992
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#mvc-multipart-resolver-standard
 * Created by Lee on 2017/5/13 0013.
 */
@Controller
public class FileUploadController {

    @Autowired
    ServletContext context;//要使用ServletContext直接在controller上注入即可，不需要配置

    /**
     * 要在前端配置editor.config.uploadImgFileName = 'myFileName'
     * 配置header中的token editor.config.uploadHeaders = {'X-token' : 'xxxx'};
     * @param part
     * @param request
     * @param response
     */
    //@PostMapping("/form")
    @RequestMapping("/upload")//不能使用PostMapping这个注解，会跳过token检查（只作用于有RequestMapping注解上）
    public void handleFormUpload(@RequestParam("myFileName") Part part, HttpServletRequest request, HttpServletResponse response) {

        try{
            response.setContentType("textml;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");

            //Part part = request.getPart("myFileName");// myFileName是文件域的name属性值
            // 文件类型限制
            String[] allowedType = { "image/bmp", "image/gif", "image/jpeg", "image/png" };
            boolean allowed = Arrays.asList(allowedType).contains(part.getContentType());
            if (!allowed) {
                response.getWriter().write("error|不支持的类型");
                return;
            }
            // 图片大小限制
            if (part.getSize() > 5 * 1024 * 1024) {
                response.getWriter().write("error|图片大小不能超过5M");
                return;
            }
            // 包含原始文件名的字符串
            String fi = part.getHeader("content-disposition");
            // 提取文件拓展名
            String fileNameExtension = fi.substring(fi.indexOf("."), fi.length() - 1);
            // 生成实际存储的真实文件名
            String realName = UUID.randomUUID().toString() + fileNameExtension;
            // 图片存放的真实路径
            String realPath = context.getRealPath("/files") + "/" + realName;
            // 将文件写入指定路径下
            part.write(realPath);

            // 返回图片的URL地址
            response.getWriter().write(request.getContextPath() + "/files/" + realName);
        }catch (Exception e){
            e.printStackTrace();
        }


    }


}
