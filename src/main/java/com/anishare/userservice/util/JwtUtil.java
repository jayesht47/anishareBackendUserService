package com.anishare.userservice.util;


import com.anishare.userservice.entity.User;
import com.anishare.userservice.service.UserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;


    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);


    public String getUserNameFromToken(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token)
    {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token)
    {
        Date expirationDate = getExpirationDateFromToken(token);
        return new Date().after(expirationDate) ;
    }

    public boolean isTokenValid(String token, UserDetails user)
    {
        String userNameFromToken = getUserNameFromToken(token);
        return userNameFromToken.equals(user.getUsername()) && !isTokenExpired(token);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String generateToken(User user) {
        HashMap<String, Object> claims = new HashMap<>();
        return createToken(claims, user.getUsername());
    }

    public String createToken(HashMap<String, Object> claims, String subject) {
        return Jwts.
                builder()
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .setSubject(subject)
                .signWith(key,SignatureAlgorithm.HS256).compact();
    }


}
