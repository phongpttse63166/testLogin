package swp490.spa.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTUtils {

    private String secret = "swp490";
    private long JWT_EXPIRATION = 604800000;

    public String extractPhoneNumber(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(String phoneNumber, String role){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims, phoneNumber, role);
    }

    private String createToken(Map<String, Object> claims, String phoneNumber, String role){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(phoneNumber+"-"+role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() +JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String phoneNumber = extractPhoneNumber(token);
        return (phoneNumber.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
