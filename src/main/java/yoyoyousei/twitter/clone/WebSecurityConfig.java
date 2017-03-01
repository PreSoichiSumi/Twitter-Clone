package yoyoyousei.twitter.clone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import yoyoyousei.twitter.clone.domain.service.TwitterCloneUserDetailsService;

/**
 * Created by s-sumi on 2017/03/01.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    TwitterCloneUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/public/**","/js/**","/css/**","/fonts/**","/images/**","/register","/debug").permitAll()
                //.antMatchers("/resources/public/**","/register").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/loginForm")    //ログインページurl
                .loginProcessingUrl("/login")   //spring securityのurl
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/",true)
                .failureUrl("/loginForm?error=true").permitAll()
                .and()
                .logout()
                .logoutUrl("/logout").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
