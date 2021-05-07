package chunson.cc.cmarket.utils;

import chunson.cc.cmarket.model.User;
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
    private String secret;//密钥
    private Long validate_time; //过期时间
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
    private String generateToken(Map<String, Object> claims)
    {
        Date expirationDate = new Date((new Date()).getTime() + validate_time);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private Claims getClaimsFromToken(String token)
    {
        Claims claims;
        claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public String generateToken(User user)
    {
        Map<String, Object> cliams = new HashMap<>();
        cliams.put("sub", user.getUserId());
        cliams.put("created", new Date());
        return generateToken(cliams);
    }

    public Long getUserIdFromToken(String token)
    {
        String userId;
        Claims claims = getClaimsFromToken(token);
        if (claims == null) return null;
        userId = claims.getSubject();
        return Long.parseLong(userId);
    }

    public Boolean isTokenExpired(String token)
    {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) return false;
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    public String refreshToken(String token)
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
    public Boolean validateToken(String Token, User user)
    {
        Long userId = getUserIdFromToken(Token);
        if (userId == null) return false;
        return (userId.equals(user.getUserId())) && !isTokenExpired(Token);
    }
}
