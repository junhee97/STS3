package com.example.app.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.example.app.config.auth.PrincipalDetailsService;
import com.example.app.config.auth.exceptionHandler.CustomAccessDeniedHandler;
import com.example.app.config.auth.exceptionHandler.CustomAuthenticationEntryPoint;
import com.example.app.config.auth.loginHandler.CustomLoginFailureHandler;
import com.example.app.config.auth.loginHandler.CustomLoginSuccessHnadler;
import com.example.app.config.auth.logoutHandler.CustomLogoutHandler;
import com.example.app.config.auth.logoutHandler.CustomLogoutSuccessHandler;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PrincipalDetailsService principalDetailsService;

	@Autowired
	private DataSource dataSource3;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// CSRF 비활성화
		http.csrf().disable(); // +CSRF 토근값 확인 X, GET /logout 처리 가능

		// CSRF 토큰 쿠키형태로 전달
		// http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

		// 권한 체크
		http.authorizeRequests().antMatchers("/", "/join", "/login").permitAll().antMatchers("/user").hasRole("USER")
				.antMatchers("/manager").hasRole("MANAGER").antMatchers("/admin").hasRole("ADMIN").anyRequest()
				.authenticated();
		// .permitAll();

		// 로그인
		http.formLogin().loginPage("/login").permitAll().successHandler(new CustomLoginSuccessHnadler())
				.failureHandler(new CustomLoginFailureHandler());

		// 로그아웃
		http.logout().permitAll().addLogoutHandler(new CustomLogoutHandler())
				.logoutSuccessHandler(new CustomLogoutSuccessHandler());

		// 예외처리
		http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 미인증 계정 예외처리
				.accessDeniedHandler(new CustomAccessDeniedHandler()); // 권한 부족시 예외처리

		// REMEMBER-ME
		http.rememberMe().key("rememberMeKey").rememberMeParameter("remember-me").alwaysRemember(false)
				.tokenValiditySeconds(86400).tokenRepository(tokenRepository());

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// auth.inMemoryAuthentication().withUser("user").password(passwordEncoder.encode("1234")).roles("USER");
		// auth.inMemoryAuthentication().withUser("manager").password(passwordEncoder.encode("1234")).roles("MANAGER");
		// auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("1234")).roles("ADMIN");

		auth.userDetailsService(principalDetailsService).passwordEncoder(passwordEncoder);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public PersistentTokenRepository tokenRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource3);
		return repo;
	}

}
