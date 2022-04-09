package chunson.cc.carket.utils;

import chunson.cc.carket.model.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils implements Serializable
{
    private static String secret;//密钥
    private static Long validate_time; //过期时间
    private String header;

//    public String getHeader()
//    {
//        return header;
//    }


    public TokenUtils(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.token-validity-in-seconds}") Long time,
                      @Value(("${jwt.header}")) String header)
    {
        this.secret = secret;
        this.validate_time = time * 1000;
        this.header = header;
    }

    //用于生成token
    private static String generateToken(Map<String, Object> claims)
    {
        Date expirationDate = new Date((new Date()).getTime() + validate_time);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private static Claims getClaimsFromToken(String token)
    {
        Claims claims;
        claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public static String generateToken(String address)
    {
        Map<String, Object> cliams = new HashMap<>();
        cliams.put("sub", address);
        cliams.put("created", new Date());
        return generateToken(cliams);
    }

    public static String getAddressFromToken(String token)
    {
        String address;
        Claims claims = getClaimsFromToken(token);
        if (claims == null) return null;
        address = claims.getSubject();
        return address;
    }

    public static boolean isTokenExpired(String token)
    {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) return false;
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    public static String refreshToken(String token)
    {
        String refreshToken;
        Claims claims = getClaimsFromToken(token);
        if (claims == null) return null;
        claims.put("created", new Date());
        refreshToken = generateToken(claims);
        return refreshToken;
    }

    /**
     * 用于校验Token是否过期
     *
     * @param Token
     * @return
     */
//    public Boolean validateToken(String Token, User user)
//    {
//        Long userId = getUserIdFromToken(Token);
//        if (userId == null) return false;
//        return (userId.equals(user.getUserId())) && !isTokenExpired(Token);
//    }
}
