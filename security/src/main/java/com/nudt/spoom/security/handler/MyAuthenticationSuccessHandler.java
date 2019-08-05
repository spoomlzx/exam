package com.nudt.spoom.security.handler;

import com.nudt.spoom.common.utils.CommonResult;
import com.nudt.spoom.security.utils.JwtTokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * package com.spoom.gear.config
 * 认证成功，返回包含token的result
 *
 * @author spoomlan
 * @date 2019-03-28
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        String token = JwtTokenUtil.generateToken(username);
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.getWriter().print(new CommonResult().success(token).toJson());
        response.getWriter().flush();
    }


}
