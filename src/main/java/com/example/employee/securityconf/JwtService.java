	package com.example.employee.securityconf;
	
	import java.util.Date;
	import java.util.HashMap;
	import java.util.Map;
	import java.util.function.Function;
	import javax.crypto.SecretKey;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.security.core.userdetails.UserDetails;
	import org.springframework.stereotype.Service;
	
	import com.example.employee.empdto.User;
	
	import io.jsonwebtoken.Claims;
	import io.jsonwebtoken.Jwts;
	import io.jsonwebtoken.io.Decoders;
	import io.jsonwebtoken.security.Keys;
	
	@Service
	public class JwtService {
	
		@Value("${jwt.secret}")
		private String secretKey;
	
		public String generateToken(User authUser) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("role", authUser.getRole());
			return Jwts.builder()
								.claims(map)
								.subject(authUser.getEmail())
								.issuedAt(new Date(System.currentTimeMillis()))
								.expiration(new Date(System.currentTimeMillis()+1000 * 60 *30))
								.signWith(getKey())
								.compact	();
		}
		
		public SecretKey getKey() {
			byte[] keybyte = Decoders.BASE64.decode(secretKey);
			return Keys.hmacShaKeyFor(keybyte);
		}
	
		public String extractEmail(String token) {
			return extractClaim(token, Claims::getSubject);
		}
	
		public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
			final Claims claims = extractAllClaims(token);
			return claimsResolver.apply(claims);
		}
	
		private Claims extractAllClaims(String token) {
			return Jwts.parser()
					.verifyWith(getKey())
					.build()
					.parseSignedClaims(token)
					.getPayload();
		}
	
		public boolean validateToken(String token, UserDetails userDetails) {
			final String email = extractEmail(token);
			return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
		}
	
		private boolean isTokenExpired(String token) {
			return extractExpiration(token).before(new Date());
		}
	
		private Date extractExpiration(String token) {
			return extractClaim(token, Claims::getExpiration);
		}
		
	}
