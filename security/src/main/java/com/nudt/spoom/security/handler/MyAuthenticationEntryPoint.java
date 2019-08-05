package com.nudt.spoom.security.handler;

import com.nudt.spoom.common.utils.CommonResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * package com.spoom.gear.config
 * 认证出错的时候的处理函数，登录认证和鉴权认证的时候都用到
 *
 * @author spoomlan
 * @date 2019-03-28
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    response.setHeader("Content-Type", "application/json;charset=utf-8");
    response.getWriter().print(new CommonResult().unauthenticated(authException.getMessage()).toJson());
    response.getWriter().flush();
  }
}
