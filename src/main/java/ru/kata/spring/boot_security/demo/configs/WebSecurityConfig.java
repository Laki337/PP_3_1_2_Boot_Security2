package ru.kata.spring.boot_security.demo.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
	private final UserDetailsService userDetailsService;
	private final SuccessUserHandler successUserHandler;
	
	public WebSecurityConfig(UserDetailsService userDetailsService, SuccessUserHandler successUserHandler) {
		this.userDetailsService = userDetailsService;
		this.successUserHandler = successUserHandler;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/login").permitAll()
		.antMatchers("/user/**").hasAuthority("USER")
		.antMatchers("/admin/**").hasAuthority("ADMIN")
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.successHandler(successUserHandler)
		.usernameParameter("j_username")
		.passwordParameter("j_password")
		.and().logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/login");

	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
