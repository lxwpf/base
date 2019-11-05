package com.base.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * token生成类，遵循jwt标准
 * @author lixiong
 * @date 2019 10 2019/10/21
 */
public class JwtHelper {

    /**
     * token过期时间，30分钟
     */
    private static final long TOKEN_EXPIRED_TIME = 60 * 1000 * 30;

    /**
     * 密钥properties配置文件路径
     */
    private static final String KEY_PROPERTIES = "/key.properties";

    /**
     * 密钥key
     */
    private static final String PRIVATE_KEY = "privateKey";

    /**
     * jwt id
     */
    private static final String JWT_ID = "tokenId";

    /**
     * 对字符串进行加密使用的算法名称
     */
    private static final String STRING_KEY_ALG = "HmacSHA256";

    /**
     * key.properties 密钥配置文件对象
     */
    private static Properties properties = null;

    /**
     * 加载密钥配置文件
     */
    static {
        ClassPathResource resource = new ClassPathResource(KEY_PROPERTIES);
        try {
            properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建jwt
     *
     * @param claims   实载信息，可根据业务修改
     * @param exp      根据创建时间 + exp = token过期时间（单位毫秒）
     * @return         jwt格式：xxxx.yyyy.zzzz
     */
    private static String createJWT(HashMap<String, Object> claims, Long exp){
        // 生成签名时需要指定算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date now = new Date(System.currentTimeMillis());

        // 读key.properties中key等于privateKey的值生成密钥
        SecretKey secretKey = obtainKey(PRIVATE_KEY);
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)                            // 实载数据，可自行设置，不要设置敏感数据
                .setId(JWT_ID)                                // 根据业务需要可自行修改
                .setIssuedAt(now)                             // iat: jwt签发时间
                .setExpiration(new Date(now.getTime() + exp)) // token过期时间
                .signWith(secretKey, signatureAlgorithm);     // 签名使用的密钥和算法

        return builder.compact();
    }

    /**
     * 验证jwt
     */
    public static Claims verifyJwt(String token) {
        // 签名秘钥，和生成的签名的秘钥一模一样
        SecretKey key = obtainKey(PRIVATE_KEY);
        Claims claims;
        try {
            claims = Jwts.parser()                 // 得到DefaultJwtParser
                    .setSigningKey(key)            // 设置签名的秘钥
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;

    }

    /**
     * 获取token，默认过期时间30分钟
     * @param userId 用户ID，or可根据业务自定义，任意值，不建议添加敏感数据，jwt实载信息是明文加密。防止暴露
     * @return token
     */
    public static String obtainToken(String userId){
        return obtainToken(userId, 0L);
    }
    /**
     * 获取token
     * @param userId      用户ID，or可根据业务自定义，任意值，不建议添加敏感数据，jwt实载信息是明文加密。防止暴露
     * @param delayTime   token过期时间 = 创建时间（无需关心） + delayTime（单位毫秒）
     * @return token
     */
    public static String obtainToken(String userId, Long delayTime){
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        return createJWT(map, delayTime <= 0 ? TOKEN_EXPIRED_TIME : delayTime);
    }

    /**
     * 读 key.properties 配置中的privateKey
     * @param key   密钥key
     * @return      密钥值
     */
    private static String getValue(String key){
        return properties == null ? null : properties.getProperty(key);
    }

    /**
     * 通过字符串得到密钥
     * @param key    密钥key
     * @return 密钥
     */
    private static SecretKey obtainKey(String key) {
        String stringKey = getValue(key);
        byte[] bytes = Base64.getDecoder().decode(Objects.requireNonNull(stringKey));
        return new SecretKeySpec(bytes, STRING_KEY_ALG);
    }

    public static void main(String[] args) {
        String token = obtainToken("123");
        System.out.println(token);
        Claims claims = verifyJwt(token);
        System.out.println("id = " + claims.getId());
        System.out.println("userId = " + claims.get("userId"));
        System.out.println("创建时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("有效时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
    }
}
