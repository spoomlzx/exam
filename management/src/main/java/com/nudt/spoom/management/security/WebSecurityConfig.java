package com.nudt.spoom.management.security;

import com.nudt.spoom.security.filter.BearerAuthenticationFilter;
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
    private SysUserDetailsServiceImpl sysUserDetailsService;
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
                .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                .antMatchers("/teacher/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .loginProcessingUrl("/admin/login").permitAll()
                .successHandler(new MyAuthenticationSuccessHandler())
                .failureHandler(new MyAuthenticationFailureHandler())
                .and()
                .addFilterBefore(bearerAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
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
    public BearerAuthenticationFilter bearerAuthenticationFilter() {
        return new BearerAuthenticationFilter(authenticationManagerBean(), myAuthenticationEntryPoint);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(sysUserDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

}
