package com.trang.ShopmeBackEnd_Admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
	public UserDetailsService userDetailsService() {
		return new ShopmeUserDetailsService();
	}

	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(metodoCrittografia());

		return authenticationProvider;
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
			.requestMatchers("/users/**").hasAuthority("Admin")
			.requestMatchers("/categories/**", "/brands/**").hasAnyAuthority("Admin", "Editor")
			.requestMatchers("/products/**").hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
			.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.usernameParameter("email")
				.permitAll()
			.and().logout().permitAll()
			.and()
				.rememberMe()
					.key("AbcDefgHijKlmnOpQrs_1234567890")
					.tokenValiditySeconds(7 * 24 * 60 * 60);    // 1 week = 7 days * 24 hours/per day * 60 minutes * 60 seconds
			
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
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
	}

	@Bean
	public static BCryptPasswordEncoder metodoCrittografia() {
		return new BCryptPasswordEncoder();
	}

}
