package com.nudt.spoom.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

public class JwtTokenUtil {
    //加载jwt.jks文件
    private static InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("jwt.jks");
    private static PrivateKey privateKey = null;
    private static PublicKey publicKey = null;

    private static Long expiration = 86400L;

    static {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(inputStream, "asdf1234".toCharArray());
            privateKey = (PrivateKey) keyStore.getKey("jwt", "asdf1234".toCharArray());
            publicKey = keyStore.getCertificate("jwt").getPublicKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get username from jwt token
     */
    public static String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = parseToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public static String generateToken(String subject) {
        return Jwts.builder()
                .setClaims(null)
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    public static Claims parseToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }


    public static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * get exipre time from token
     */
    public static Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = parseToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }
}