package com.book.util;

import com.book.domain.UserCoreInfo;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author sunlongfei
 */
@Component
public class JwtUtil {

    private static final String SECRET = "adwadawdafawdawdaw";

    private static Gson gson;

    @Autowired
    public void setGson(Gson gson) {
        JwtUtil.gson = gson;
    }

    public static String createJwt(UserCoreInfo info) {
        // 签名方法 HS256
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成Jwt的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 生成秘钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // 计算过期时间
        int lastDays = 3;
        long expMillis = nowMillis + lastDays * 24 * 60 * 60 * 1000;
        Date exp = new Date(expMillis);

        // 设置JWT所存储的信息
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", info.getId());
        claims.put("identity", info.getIdentity());

        JwtBuilder builder = Jwts.builder()
            .setClaims(claims)
            .setExpiration(exp)
            .setIssuedAt(now)
            .signWith(signatureAlgorithm,signingKey);

        // 构建JWT并将其序列化为紧凑的URL安全字符串
        return builder.compact();
    }

    public static UserCoreInfo parseJwt(String jwt) throws Exception {
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET)).parseClaimsJws(jwt).getBody();
            return new UserCoreInfo(claims.get("id", Integer.class), claims.get("identity", String.class));
        } catch (Exception e) {
            throw new Exception("身份校验失败");
        }
    }
}
