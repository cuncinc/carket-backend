package chunson.cc.carket.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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

    public TokenUtils(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.token-validity-in-seconds}") Long time,
                      @Value(("${jwt.header}")) String header)
    {
        this.secret = secret;
        this.validate_time = time * 1000;
        this.header = header;
    }

    public static String generateToken(String address)
    {
        Map<String, Object> cliams = new HashMap<>();
        cliams.put("sub", address);
        cliams.put("created", new Date());
        return generateToken(cliams);
    }

    public static String getAddress(String token)
    {
        try
        {
            Claims claims = getClaims(token);
            if (claims == null) return null;
            return claims.getSubject();
        }
        catch (ExpiredJwtException e)
        {
            return null;
        }
    }

    /**
     * @return 过期返回true，否则返回false
     */
    public static boolean isTokenOK(String token)
    {
        try
        {
            Claims claims = getClaims(token);
            if (claims == null) return false;
            Date expiration = claims.getExpiration();
            return !expiration.before(new Date());
        }
        catch (ExpiredJwtException e)
        {
            return false;
        }
    }

    public static String refreshToken(String token)
    {
        String newToken;
        Claims claims = getClaims(token);
        if (claims == null) return null;
        claims.put("created", new Date());
        newToken = generateToken(claims);
        return newToken;
    }

    private static String generateToken(Map<String, Object> claims)
    {
        Date expirationDate = new Date((new Date()).getTime() + validate_time);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private static Claims getClaims(String token) throws ExpiredJwtException
    {
        Claims claims;
        claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
