package com.example.account2024.config;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.account2024.Entity.Authority;
import com.example.account2024.service.JpaUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtProvider {
// JWT = Json Web Token
	
	@Value("${jwt.secret.key}")
	private String salt;
	
	private SecretKey secretKey;
	
	// 만료시간 1Hour
	private final long exp = 1000L * 60 * 60;
	
	private final JpaUserDetailsService userDetailsService;
	
	@PostConstruct
	protected void init() {
		secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
	}
	
	// 토큰생성
	public String createToken(String userId, List<Authority> roles) {
		Claims claims = Jwts.claims().setSubject(userId);
		claims.put("roles", roles);
		Date now = new Date();
		return Jwts.builder()
				   .setClaims(claims)
				   .setIssuedAt(now)
				   .setExpiration(new Date(now.getTime() + exp))
				   .signWith(secretKey, SignatureAlgorithm.HS256)
				   .compact();
	}
	
	// 권한정보 획득
	// Spring Security 인증 과정에서 권한 확인을 위한 기능
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	// 토큰에 담겨있는 유저 userId 획득
	public String getUserId(String token) {
		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	// Authorization Header를 통해 인증을 한다.
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("Authorization");
	}
	
	// 토큰 검증
	public boolean validationToken(String token) {
		try {
			// Bearer 검증
			if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER")) {
				return false;
			} else {
				token = token.split(" ")[1].trim();
			}
			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
			// 만료되었을 시 false
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}
}
