package com.trang.ShopmeBackEnd_Admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.trang.ShopmeBackEnd_Admin.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfiguration {

//	@Autowired
//	@Qualifier("bcryptPasswordEncoder")
//	PasswordEncoder bcryptPasswordEncoder;
	
//	@Autowired 
//	UserService userService;
//	

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests().anyRequest().permitAll();
		
//		http.authorizeHttpRequests()
//		.requestMatchers("/", "/ShopAdmin/**", "/register", "/h2-console/**").permitAll()
//		.requestMatchers("/admin/**").hasRole("ADMIN")
//		.anyRequest()
//		.authenticated()
//		.and()
//		.formLogin()
//		.loginPage("/login")
//		.permitAll()
//		.failureUrl("/login?error=true")
//		.defaultSuccessUrl("/")
//		.usernameParameter("email")
//		.passwordParameter("password")
//		.and()
//		.logout()
//		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//		.logoutSuccessUrl("/login")
//		.invalidateHttpSession(true)
//		.deleteCookies("JSESSIONID")
//		.and()
//		.exceptionHandling()
//		.and()
//		.csrf()
//		.disable();
	
		return http.build();
	}

//	@Bean
//	public PasswordEncoder PasswordEncoder() {
////		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
////		return bCryptPasswordEncoder;
//		return new BCryptPasswordEncoder();
//	}
	
	 @Bean
     public static BCryptPasswordEncoder metodoCrittografia() {
         return new BCryptPasswordEncoder();
     }
	


}
