package security;

/**
 * Created by Lee on 2017/5/7 0007.
 */
public interface TokenManager {
    String createToken(String username);

    boolean checkToken(String token);

    boolean checkPermission(String permission);
    String getUserName(String token);

    boolean removeToken(String token);
}
