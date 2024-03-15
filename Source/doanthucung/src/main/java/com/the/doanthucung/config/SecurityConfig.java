package com.the.doanthucung.config;

import com.the.doanthucung.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity
@Configuration
public class SecurityConfig{
   @Autowired
   public CustomUserDetailsService customUserDetailsService;


   @Bean
   private static PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
   }
   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder builder){
      try {
         builder.userDetailsService(customUserDetailsService)
                 .passwordEncoder(passwordEncoder());
      }catch (Exception e){
         e.printStackTrace();
      }
   }

   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
       http.authorizeHttpRequests((author) -> author.requestMatchers("/","/login").permitAll() // config cho phép vào page login mà không cần đăng nhập
               .requestMatchers("/views/home/index**").hasAnyRole("USER") // config chỉ cho phép vào url /views/home/index khi có quyền admin
               .requestMatchers("/admin/**").hasAnyRole("ADMIN") // chỉ cho phép truy cập vào url /product/** khi có quyền admin
               .requestMatchers("/views/**").hasAnyRole("USER")// cho phép truy cập vào /user/** khi có quyền user
               .requestMatchers("/process-after-login").hasAnyRole(new String[]{"ADMIN", "USER"}) // cho phép truy cập khi có quyền user hoặc admin
               .requestMatchers("/login/**","/assets/**","/frontend/**").permitAll()
       ).formLogin(form ->
               form.
                       loginPage("/login") // GET
                       .loginProcessingUrl("/authentication") // POST
                       .defaultSuccessUrl("/process-after-login") // sau khi login thành công sẽ truy cập vào url process-after-login để điều hướng phân quyền
                       .failureUrl("/login").permitAll()
       ).logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll());
      return http.build();
   }


   public static void main(String[] args) {
      PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      System.out.println(passwordEncoder.encode("admin"));
   }
}
