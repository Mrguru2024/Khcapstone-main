//package com.keycodehelp.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Base64;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//@Component  // Register JwtUtil as a Spring bean
//public class JwtUtil {
//
//    // Secret key injected from application properties or environment variables
//    @Value("${jwt.secret}")
//    private String SECRET_KEY;
//
//    // Token validity (example: 10 hours, can be moved to properties)
//    @Value("${jwt.expiration}")
//    private long EXPIRATION_TIME;
//
//    private Key getSigningKey() {
//        // Encode the secret using a Key object
//        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
//    }
//
//    // Generate a token for the given username
//    public String generateToken(String username) {
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, username);
//    }
//
//    // Create JWT token with claims and subject (username)
//    private String createToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Token valid for specified time
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Using the new method for signing
//                .compact();
//    }
//
//    // Extract username from the token
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    // Extract specific claim from the token
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    // Extract all claims from the token
//    private Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getSigningKey()) // Use new parserBuilder method
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    // Check if the token is expired
//    public Boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    // Extract expiration date from the token
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    // Validate token against username
//    public boolean validateToken(String token, String username) {
//        final String extractedUsername = extractUsername(token);
//        return (extractedUsername.equals(username) && !isTokenExpired(token));
//    }
//}
