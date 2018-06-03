package com.cll.security.web.config;

import com.cll.security.web.security.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author chenliangliang
 * @date 2018/6/2
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("myUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //UserDetailsService
        http.addFilterBefore(validateCodeFilter(),UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/page/login") //登录页面
                .loginProcessingUrl("/user/login")  //登录处理url
                .successHandler(successHandler()) //登录成功后处理
                .failureHandler(failureHandler())  //登录失败处理
                //http.httpBasic()
                .and()
                .rememberMe()
                .tokenRepository(tokenRepository())
                .tokenValiditySeconds(3600)
                .rememberMeCookieName("cll")
                .userDetailsService(userDetailsService)
                .and()
                .authorizeRequests()
                .antMatchers("/page/login","/user/login" ,"/page/register", "/user/register",
                        "/auth/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (httpServletRequest, httpServletResponse, authentication) -> {
            System.out.println(authentication);
            httpServletResponse.sendRedirect("/page/index");
        };
    }


    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return (httpServletRequest, httpServletResponse, e) -> {
            String msg = e.getMessage();
            System.out.println(msg);
            httpServletRequest.setAttribute("msg", msg);
            httpServletRequest.getRequestDispatcher("/auth/fail")
                    .forward(httpServletRequest, httpServletResponse);
            //throw e;
        };
    }


    @Bean
    public ValidateCodeFilter validateCodeFilter() {
        return new ValidateCodeFilter(failureHandler());
    }

    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository=new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
}
