package com.dex.coreserver.security;

import com.dex.coreserver.model.Employee;
import com.dex.coreserver.model.User;
import com.dex.coreserver.service.EmployeeService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static com.dex.coreserver.security.SecurityConstants.EXPIRATION_TIME;
import static com.dex.coreserver.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {

    @Autowired
    EmployeeService employeeService;

    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime()+EXPIRATION_TIME);

        Employee loggedEmployee = employeeService.findByUserId(user.getId());

        String userId = Long.toString(user.getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("roles", user.getRoles());
        claims.put("loggedEmployeeId", loggedEmployee.getId());

        return Jwts.builder().setSubject(userId).setClaims(claims)
                .setIssuedAt(now).setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            System.out.println("Invalit JWT Signature");
        }catch (MalformedJwtException ex){
            System.out.println("Invalit JWT Token");
        }catch (ExpiredJwtException ex){
            System.out.println("Expired JWT Token");
        }catch (UnsupportedJwtException ex){
            System.out.println("Unsupported JWT Token");
        }catch (IllegalArgumentException ex){
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

    public Long getUserIdFromJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = (String)claims.get("id");
        return Long.parseLong(id);
    }
}
