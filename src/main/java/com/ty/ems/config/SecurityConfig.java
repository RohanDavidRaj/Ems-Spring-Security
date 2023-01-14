package com.ty.ems.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ty.ems.dao.EmployeeDao;
import com.ty.ems.security.CustomUserDetailService;
import com.ty.ems.security.JwtTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private EmployeeDao dao;
  
  @Autowired
  private JwtTokenFilter jwtTokenFilter;
  
  @Autowired
  private CustomUserDetailService customUserDetailService;
  
//  @Bean
//  public CustomUserDetailService customUserDetailService(){
//    return new CustomUserDetailService();
//  }
  


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    
    http.csrf().disable();
    http.sessionManagement()
    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.authorizeRequests()
    .antMatchers("/delete").hasAnyRole("ADMIN")
    .antMatchers("/getAll").hasAnyRole("USER")
    .antMatchers("/login","/register").permitAll()
    .anyRequest().authenticated();
    
    http.exceptionHandling().authenticationEntryPoint(
        (request,response,ex) -> {response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
            ex.getMessage());
          }
        );
    
    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    
    
  }

//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return NoOpPasswordEncoder.getInstance();
//  }

//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    try {
//      auth.userDetailsService(username -> dao.findByEmployeeId(username));
//    } catch (Exception e) {
//      e.printStackTrace();
//      throw new EmployeeException("user not found");
//    }
//
//  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    // configure AuthenticationManager so that it knows from where to load
    // user for matching credentials
    // Use BCryptPasswordEncoder
    auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();

  }
}
