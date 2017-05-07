package bean.user;

/**
 * 存取登陆后的结果以及登录成功时该用户的权限
 * Created by Lee on 2017/5/7 0007.
 */
public class LoginedResult {
    private String permission;
    private boolean result;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
