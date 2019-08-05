package com.nudt.spoom.security.handler;

import com.nudt.spoom.common.utils.CommonResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * package com.spoom.gear.config
 * 认证失败
 *
 * @author spoomlan
 * @date 2019-03-28
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    response.setHeader("Content-Type", "application/json;charset=utf-8");
    response.getWriter().print(new CommonResult().unauthenticated(exception.getMessage()).toJson());
    response.getWriter().flush();
  }
}
