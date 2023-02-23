package com.pccth.jwt;


import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.pccth.pccsdmgrservice.model.Employee;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtGenerator {
	   private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000;
	    private static String SECRET_KEY="ca0149f7-dccc-45d2-883b-654c67fecd63";

	   public String generateAccessToken(String username) {
	        return Jwts.builder()
	                .setSubject(String.format("%s",username))
	                .setIssuer("CodeJava")
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
	                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
	                .compact();
	                 
	    }
	   
	   public String generatePassWordToken(String password) {
	        return Jwts.builder()
	                .setSubject(String.format("%s",password))               
	                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
	                .compact();    
	    }
	   
	   public static Claims decodeJWT(String jwt) {
		    //This line will throw an exception if it is not a signed JWS (as expected)
		   Claims claims = null;
		   try {
		     claims = Jwts.parser()
		            .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
		            .parseClaimsJws(jwt).getBody();
		    return claims;
		   }catch(Exception e) {
			   return claims = null;
		   }
		}

}