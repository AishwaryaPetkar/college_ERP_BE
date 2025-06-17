package com.app.auth.config;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
	private final String jwtIssure = "http://localhost:3000";
	private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public String generateToken(String username, long expirationMillis) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuer(jwtIssure)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+expirationMillis))
				.signWith(key)
				.compact();
	}

	
	public String getUsernameFromToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key).build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	 
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		}catch(JwtException ex) {
			return false;
		}
	}

}
