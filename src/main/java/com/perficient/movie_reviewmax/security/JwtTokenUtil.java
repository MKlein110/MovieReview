package com.perficient.movie_reviewmax.security;

import java.util.Date;

import com.perficient.movie_reviewmax.entities.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtil {
	
	private final static String jwtSecret = "zdtlD3JK56m6wTTgsNFhqzjqP";
	private final static String jwtIssuer = "google.com";
    public static String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(format(String.valueOf(user.getId()), user.getUsername()))
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

	private static String format(String valueOf, String username) {
		// TODO Auto-generated method stub
		return valueOf + username;
	}
}
