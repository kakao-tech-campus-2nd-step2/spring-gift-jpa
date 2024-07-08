package gift.Util;

import gift.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import javax.crypto.SecretKey;
import java.util.Date;

public class JWTUtil {

    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    /**
     * 토큰 생성
     */
    public static String generateToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("user_id", user.getId());
        return createToken(claims, user.getId());
    }

    private static String createToken(Claims claims, int subject) {
        Date now = new Date(System.currentTimeMillis());
        Date expirationTime = new Date(now.getTime() + 1000 * 60 * 60);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    /**
     * 토큰 유효여부 확인
     */
    public static Boolean isValidToken(String token) {
        //log.info("isValidToken token = {}", token);
        return !isTokenExpired(token);
    }

    /**
     * 토큰의 Claim 디코딩
     */
    private static Claims getAllClaims(String token) {
        //log.info("getAllClaims token = {}", token);
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Claim 에서 username 가져오기
     */
    public static int getIdFromToken(String token) {
        int userID = Integer.parseInt((String) getAllClaims(token).get("user_id"));
        //log.info("getUsernameFormToken subject = {}", username);
        return userID;
    }

    /**
     * 토큰 만료기한 가져오기
     */
    public static Date getExpirationDate(String token) {
        Claims claims = getAllClaims(token);
        return claims.getExpiration();
    }

    /**
     * 토큰이 만료되었는지
     */
    private static boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }
}
