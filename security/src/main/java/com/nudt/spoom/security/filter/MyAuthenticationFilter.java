package com.nudt.spoom.security.filter;

import com.nudt.spoom.security.token.BearerAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * package com.nudt.spoom.security.filter
 *
 * @author spoomlan
 * @date 2019-08-05
 */

public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // header 的parameter 名
    private String tokenHeader = "Authorization";
    // token 的开始标志
    private String tokenBegin = "Bearer ";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        final boolean debug = this.logger.isDebugEnabled();
        String header = request.getHeader(tokenHeader);
        //如果request中没有token，跳过此filter
        if (header == null || !header.startsWith(tokenBegin)) {
            throw new AuthenticationServiceException("token invalid");
        }

        // 截取token
        String token = header.substring(tokenBegin.length());
        if (StringUtils.isEmpty(token)) {
            throw new InsufficientAuthenticationException("token is empty");
        }
        if (debug) {
            this.logger
                    .debug("Bearer Authentication Authorization header found : '" + token + "'");
        }
        AbstractAuthenticationToken authRequest = new BearerAuthenticationToken(token);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetails(HttpServletRequest request,
                            AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
