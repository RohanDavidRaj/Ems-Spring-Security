package com.ty.ems.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ty.ems.entity.EmployeePrimary;


@Component
public class JwtTokenFilter extends OncePerRequestFilter{

	@Autowired 
	private JwtUtil jwtUtil;
	
	 @Override
	    protected void doFilterInternal(HttpServletRequest request,
	                HttpServletResponse response, FilterChain filterChain)
	            throws ServletException, IOException {
	 
	        if (!hasAuthorizationBearer(request)) {
	            filterChain.doFilter(request, response);
	            return;
	        }
	 
	        String token = getAccessToken(request);
	        System.out.println(token);
	 
	        if (!jwtUtil.validateAccessToken(token)) {
	            filterChain.doFilter(request, response);
	            return;
	        }
	         
	        setAuthenticationContext(token, request);
	        filterChain.doFilter(request, response);
	    }
	 
	    private boolean hasAuthorizationBearer(HttpServletRequest request) {
	        String header = request.getHeader("Authorization");
	        System.out.println("header "+header);
	        
	        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
	            return false;
	        }
	 
	        return true;
	    }
	 
	    private String getAccessToken(HttpServletRequest request) {
	        String header = request.getHeader("Authorization");
	        String token = header.split(" ")[1].trim();
	        System.out.println("header split "+token);
	        String[] jwtSubject = jwtUtil.getSubject(token).split(",");
	        System.out.println("@@@@@@@@@@@@@"+jwtSubject);
	        return token;
	    }
	 
	    private void setAuthenticationContext(String token, HttpServletRequest request) {
	        UserDetails userDetails = getUserDetails(token);
	        
	 
	        UsernamePasswordAuthenticationToken
	            authentication = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
	 
	        authentication.setDetails(
	                new WebAuthenticationDetailsSource().buildDetails(request));
	 
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	    }
	 
	    private UserDetails getUserDetails(String token) {
	        EmployeePrimary userDetails = new EmployeePrimary();
	        String[] jwtSubject = jwtUtil.getSubject(token).split(",");
	        System.out.println("jwtsubject "+jwtSubject);
	 
	        userDetails.setEmployeeId(jwtSubject[0]);
	        userDetails.setPhoneNumber(jwtSubject[1]);
	 
	        return userDetails;
	    }

}
