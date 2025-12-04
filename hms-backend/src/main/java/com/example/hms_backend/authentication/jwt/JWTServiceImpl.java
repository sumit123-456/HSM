package com.example.hms_backend.authentication.jwt;

import com.example.hms_backend.authentication.entity.Permission;
import com.example.hms_backend.authentication.entity.Role;
import com.example.hms_backend.authentication.entity.UserEntity;
import com.example.hms_backend.authentication.entity.UserInfo;
import com.example.hms_backend.exception.JwtTokenExpiredException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;

@Service
public class JWTServiceImpl implements JWTService {

    private String secretKey = "";

    public JWTServiceImpl() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String generateToken(UserEntity user, UserInfo userInfo, List<Permission> permissions) {
        Map<String, Object> claims = new HashMap<>();

        // ✅ Embed user info
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("userId", user.getUserId());
        userMap.put("email", user.getEmail());
        userMap.put("username", user.getUsername());
        userMap.put("firstName", userInfo != null ? userInfo.getFirstName() : null);
        userMap.put("lastName", userInfo != null ? userInfo.getLastName() : null);
        claims.put("user", userMap);


        claims.put("roles", user.getRoles().stream().map(Role::getRoleName).toList());
        claims.put("permissions", permissions.stream().map(Permission::getPermissionName).toList());

//        long now = System.currentTimeMillis(); // this is the millisecond time passed since 1 jan 1970
//        long expMillis = now + 1000 * 60 * 60;
        long now = System.currentTimeMillis();
        long expMillis = now + 1000 * 60 * 60;
        claims.put("exp", expMillis / 1000); //convert ms → seconds


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expMillis))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    @Override
    public String extractUsername(String token) {
        Claims claims = extractAllclaims(token);
        return claims.getSubject(); // 'sub' field in JWT

    }

    @Override
    public boolean validateToken(String token, UserDetails user) {
        String username = extractUsername(token);
        Boolean isExpired=isTokenExpried(token);
        if (username.equalsIgnoreCase(user.getUsername()) && !isExpired){
            return true;
        }
        return false;
    }

    private Boolean isTokenExpried(String token) {
        Claims claims = extractAllclaims(token);
        Date expiration = claims.getExpiration();

        return expiration.before(new Date());
    }
    public Claims extractAllclaims(String token) {
        try{
            return Jwts.parser().verifyWith(decrptKey(secretKey))
                    .build().parseSignedClaims(token).getPayload();
        }catch (ExpiredJwtException e){
            throw new JwtTokenExpiredException("Token Is Expired");
        }catch (JwtException e){
            throw new JwtTokenExpiredException("Invalid Jwt Token");
        }catch (Exception e){
            throw e;
        }
    }

    private SecretKey decrptKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
