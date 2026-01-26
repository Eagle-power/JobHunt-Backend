package com.jobhunt.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

//@Component
//public class jwtHelper {
////	 private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//		
//	@Value("${jwt.secret}")
//	private String SECRET_KEY;
//
//	    // ⏳ Token validity (24 hours)
//	    private static final long JWT_TOKEN_VALIDITY = 3600000;
//
//	    public String getUsernameFromToken(String token) {
//	    		return getClaimFromToken(token,Claims::getSubject);
//	    }
//	    
//	    public Date getExpirationDateFromToken(String token) {
//	    		return getClaimFromToken(token , Claims::getExpiration);
//	    }
//	    
//	    public <T> T getClaimFromToken(String token, Function<Claims , T> claimsResolver) {
//	    		final Claims claims = getAllClaimsFromToken(token);
//	    		return claimsResolver.apply(claims);
//	    }
//	    
//	    private Claims getAllClaimsFromToken(String token) {
//	    		return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
//	    }
//	    
//	    private Boolean isTokenExpired(String token) {
//	    		final Date expiration = getExpirationDateFromToken(token);
//	    		return expiration.before(new Date());
//	    }
//	    
//	    public String generateToken(UserDetails userDetails) {
//	    		Map<String , Object> claims = new HashMap<>();
//	    		CustomUserDetails customUser = (CustomUserDetails)userDetails ;
//	    		claims.put("id", customUser.getId());
//	    		claims.put("name", customUser.getName());
//	    		claims.put("profileId", customUser.getProfileId());
//	    		claims.put("accountType", customUser.getAccountType());
//	    		return doGenerateToken(claims,userDetails.getUsername());
//	    }
//	    
//	    private String doGenerateToken(Map<String , Object> claims, String subject) {
//	    		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)).signWith(SECRET_KEY).compact();
//	    }
//	    
//	    public  Boolean validateToken(String token, String username) {
//	    		final String tokenUsername = getUsernameFromToken(token);
//	    		return (tokenUsername.equals(username) && !isTokenExpired(token));
//	    }
//}




@Component
public class jwtHelper {

    private static final long JWT_TOKEN_VALIDITY = 3600000;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(
            String token,
            Function<Claims, T> claimsResolver) {

        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        CustomUserDetails customUser = (CustomUserDetails) userDetails;

        claims.put("id", customUser.getId());
        claims.put("name", customUser.getName());
        claims.put("profileId", customUser.getProfileId());
        claims.put("accountType", customUser.getAccountType());

        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(
            Map<String, Object> claims,
            String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String tokenUsername = getUsernameFromToken(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }
}

