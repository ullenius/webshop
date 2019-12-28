package se.anosh.webshop.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {
	
	private static final String ADMIN = "ADMIN";
	private static final String USER = "USER";
	
	@Autowired
    DataSource dataSource;

	//Enable jdbc authentication
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder());
    }
    
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests().antMatchers("/","/addUser","/saveUser","/login.html","/css/*","/menu.html").permitAll()
    	.antMatchers("/shop/*").hasAnyRole(USER, ADMIN)
    	.antMatchers("/product/*").hasAnyRole(USER, ADMIN)
    	.antMatchers("/shoppingCart/*").hasAnyRole(USER, ADMIN)
    	.antMatchers("/admin/*").hasAnyRole(ADMIN).anyRequest().authenticated()
    	.and().formLogin().loginPage("/login").permitAll()
    	.and().logout().permitAll();

    	http.csrf().disable();
    }

}
