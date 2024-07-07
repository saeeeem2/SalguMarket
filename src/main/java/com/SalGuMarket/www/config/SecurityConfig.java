package com.SalGuMarket.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.SalGuMarket.www.security.CustomUserService;
import com.SalGuMarket.www.security.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	//passwordEncoder => springSecurity5(이후부터 변경사항)
	// createDelegatingPasswordEncoder
	@Bean
	PasswordEncoder passwordencoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	//SecurityFilterChain 객체로 설정
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		return http.csrf(csrf-> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(
								"/**"/* ,"/js/**","/css/**","/img/**","/fonts/**" */)
						.permitAll()
				.requestMatchers("/member/list").hasAnyRole("ADMIN")
				.anyRequest().authenticated())
				.formLogin(login -> login
						.usernameParameter("email")
						.passwordParameter("pwd")
						.loginPage("/member/login")
						.failureUrl("/member/login?error=true") 
						.defaultSuccessUrl("/").permitAll())
				.logout(logout -> logout
						.logoutUrl("/member/logout")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
						.logoutSuccessUrl("/")).build();
	}


	//AuthenticationManager 객체로 설정
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	// UserDetailsService : 기존 Spring과 같은 클래스
	@Bean
	UserDetailsService userDetailsService() {
		return new CustomUserService();
	}
	
}