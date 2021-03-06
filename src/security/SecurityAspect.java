package security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import security.web.WebContext;
import util.StringUtil;

import java.lang.reflect.Method;

/**
 * 基于 Spring AOP 写一个切面类，用于拦截 Controller 类的方法，并从请求头中获取 token，最后对 token 有效性进行判断
 * 对于加了@IgnoreSecurity注解的controller方法，会直接调用目标方法
 * Created by Lee on 2017/5/7 0007.
 */
public class SecurityAspect {

    private static final String DEFAULT_TOKEN_NAME = "X-Token";
    private static final String DEFAULT_PERMISSION_NAME = "X-Permission";

    private TokenManager tokenManager;
    private String tokenName;
    private String permissionName;

    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    public void setTokenName(String tokenName) {
        if (StringUtil.isEmpty(tokenName)) {
            tokenName = DEFAULT_TOKEN_NAME;
        }
        this.tokenName = tokenName;
    }

    public void setPermissionName(String permissionName){
        if (StringUtil.isEmpty(permissionName)){
            this.permissionName=DEFAULT_PERMISSION_NAME;
        }
        this.permissionName=permissionName;
    }

    public Object execute(ProceedingJoinPoint pjp) throws Throwable {
        // 从切点上获取目标方法
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        // 若目标方法忽略了安全性检查，则直接调用目标方法
        if (method.isAnnotationPresent(IgnoreSecurity.class)) {
            return pjp.proceed();
        }
        // 从 request header 中获取当前 token
        String token = WebContext.getRequest().getHeader(tokenName);
        // 检查 token 有效性
        if (!tokenManager.checkToken(token)) {
            String message = String.format("token [%s] is invalid", token);
            throw new TokenException(message);
        }

        String permission = WebContext.getRequest().getHeader(permissionName);
        // 检查 permission 有效性
        if (StringUtil.isNotEmpty(permission)){//对于header中包含X-Permission的就进行permission的验证，因为普通用户在登录成功后返回的data里面是不包含permission字段的
            if (!tokenManager.checkPermission(permission)) {
                String message = String.format("permission [%s] is invalid", permission);
                throw new TokenException(message);
            }
        }

        // 调用目标方法
        return pjp.proceed();
    }
}