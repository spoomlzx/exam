package com.nudt.spoom.portal.security;

import com.nudt.spoom.security.filter.MyAuthenticationFilter;
import com.nudt.spoom.security.handler.MyAccessDeniedHandler;
import com.nudt.spoom.security.handler.MyAuthenticationEntryPoint;
import com.nudt.spoom.security.handler.MyAuthenticationFailureHandler;
import com.nudt.spoom.security.handler.MyAuthenticationSuccessHandler;
import com.nudt.spoom.security.provider.BearerAuthenticationProvider;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * package com.nudt.spoom.management.security
 *
 * @author spoomlan
 * @date 2019-08-05
 */

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private BearerAuthenticationProvider bearerAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler)
                // 这里是登录认证的时候authentication出错的处理
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                .and().authorizeRequests()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/sys**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin()
                .loginProcessingUrl("/user/login").permitAll()
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .logout().logoutUrl("/logout").permitAll()
                .and()
                .addFilterBefore(myAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(bearerAuthenticationProvider);
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    @Bean
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    @Bean
    public MyAuthenticationFilter myAuthenticationFilter() {
        MyAuthenticationFilter filter = new MyAuthenticationFilter();
        try {
            filter.setAuthenticationManager(authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        return filter;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

}
