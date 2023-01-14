package com.ty.ems.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ty.ems.entity.EmployeePrimary;
import com.ty.ems.exception.EmployeeException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {
	private static final Logger LOGGER =LoggerFactory.getLogger(JwtUtil.class);
	
	private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour
    
   @Value("${app.jwt.secret}")
	private String secretKey;
     
    public String generateAccessToken(EmployeePrimary employee) {
        return Jwts.builder()
                .setSubject(employee.getEmployeeId() +","+employee.getPhoneNumber())
                .setIssuer("RohanRaj")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
                 
    }
    
    public boolean validateAccessToken(String token) {
     try {
		Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		return true;
	} catch (ExpiredJwtException e) {
		LOGGER.error("JWT expired",e);
	} catch (UnsupportedJwtException e) {
		LOGGER.error("JWT is not supporeted",e);
	} catch (MalformedJwtException e) {
		LOGGER.error("JWT expired",e);
	} catch (SignatureException e) {
		LOGGER.error("Signature validation is failed",e);
	} catch (IllegalArgumentException e) {
		LOGGER.error("Token is null ,empty or has only whitespace",e);
		throw new EmployeeException("scscsdc");
	}
	return false;
          
    }
    
    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }
     
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
    
    

}
