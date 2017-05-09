package bean;

/**
 * 修改密码需要用到的实体
 * Created by Lee on 2017/5/9 0009.
 */
public class Pwd {
    private String username;
    private String oldPwd;
    private String newPwd;
    private String checkPwd;

    public String getOldPwd() {
        return oldPwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getCheckPwd() {
        return checkPwd;
    }

    public void setCheckPwd(String checkPwd) {
        this.checkPwd = checkPwd;
    }
}
