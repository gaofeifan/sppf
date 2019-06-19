package com.linkmoretech.account.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

/**
 * @Author: alec
 * Description:
 * @date: 15:09 2019-06-04
 */
public class JwtUtil {

    public static final String SECRET_KEY = "123456"; //秘钥
    public static final long TOKEN_EXPIRE_TIME = 600 * 60 * 1000; //token过期时间
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 600 * 60 * 1000; //refreshToken过期时间
    private static final String ISSUER = "linkmoreitecher"; //签发人

    /**
     * 生成签名
     */
    public static String generateToken(String userName){

        Date now = new Date();
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String token = JWT.create()
                .withIssuer("MING")
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + TOKEN_EXPIRE_TIME))
                .withClaim("userName", userName)//保存身份标识
                .sign(algorithm);
        return token;

    }
}
