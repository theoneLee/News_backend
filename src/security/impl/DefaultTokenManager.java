package security.impl;

import security.TokenManager;
import util.CodecUtil;
import util.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 将 token 存储到 JVM 内存中
 * 检验Permission（在header中拿到该值后进行判断）
 * Created by Lee on 2017/5/7 0007.
 */
public class DefaultTokenManager implements TokenManager {

    private static final String NEWS_MANAGER = "NEWS_MANAGER";
    private static final String NEWS_ADMIN = "NEWS_ADMIN";

    private static Map<String, String> tokenMap = new ConcurrentHashMap<>();

    @Override
    public String createToken(String username) {
        String token = CodecUtil.createUUID();
        tokenMap.put(token, username);
        return token;
    }

    @Override
    public boolean checkToken(String token) {
        return !StringUtil.isEmpty(token) && tokenMap.containsKey(token);
    }

    @Override
    public boolean checkPermission(String permission) {//进来的合法参数可以为"","NEWS_MANAGER","NEWS_ADMIN"
        if (permission.equals(NEWS_MANAGER)||permission.equals(NEWS_ADMIN)){
            return true;
        }
        return false;
    }

    @Override
    public String getUserName(String token){
        return tokenMap.get(token);
    }
}