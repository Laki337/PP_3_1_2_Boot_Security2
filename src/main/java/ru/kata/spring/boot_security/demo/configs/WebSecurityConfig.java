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
		http.formLogin().loginPage("/login").successHandler(successUserHandler).loginProcessingUrl("/login").usernameParameter("j_username").passwordParameter("j_password").permitAll();
		
		
		http.authorizeRequests().antMatchers("/login").anonymous().antMatchers("/admin").permitAll()//access("hasRole('ADMIN')")
				.antMatchers("/user").permitAll()//access("hasAnyRole('ADMIN', 'USER')")
		 .antMatchers("/").authenticated().anyRequest().authenticated();
		
		http.logout().permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout").and().csrf().disable();
		
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