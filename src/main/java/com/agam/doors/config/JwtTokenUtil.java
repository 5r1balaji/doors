package com.agam.doors.config;

import com.agam.doors.model.AuthToken;
import com.agam.doors.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.agam.doors.model.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.agam.doors.model.Constants.SIGNING_KEY;

@Component
public class JwtTokenUtil implements Serializable {
    @Autowired
    private RedisUtil<AuthToken> redisUtil;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean  isTokenInSession(String token){
        return redisUtil.getValue(token)==null ? false : true;
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public AuthToken generateToken(User user) {
        return doGenerateToken(user.getUsername());
    }

    private AuthToken doGenerateToken(String subject) {
        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        String token=    Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://doors.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
        AuthToken authToken=new AuthToken(token,subject);
        redisUtil.putValue(token,authToken);
        redisUtil.setExpire(token,System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000, TimeUnit.MILLISECONDS);
        return authToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {

        final String username = getUsernameFromToken(token);
        System.out.println("testing"+username);
        return (
              username.equals(userDetails.getUsername())
                    && !isTokenExpired(token) && isTokenInSession(token) );
    }

    public void invalidateToken(String token){
        System.out.println(redisUtil.getValue(token));
        redisUtil.deleteValue(token);
    }




}
