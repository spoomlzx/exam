package com.nudt.spoom.security.handler;

import com.nudt.spoom.common.utils.CommonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * package com.spoom.gear.config
 * 鉴权失败，没有足够权限
 *
 * @author spoomlan
 * @date 2019-03-28
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setHeader("Content-Type", "application/json;charset=utf-8");
    response.getWriter().print(new CommonResult().unauthorized(accessDeniedException.getMessage()).toJson());
    response.getWriter().flush();
  }
}
