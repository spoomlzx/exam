package com.nudt.spoom.security.provider;

import com.nudt.spoom.security.token.BearerAuthenticationToken;
import com.nudt.spoom.security.utils.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * package com.spoom.gear.config.security 对{@link BearerAuthenticationToken}进行认证
 *
 * @author spoomlan
 * @date 2019-03-30
 */
@Component
@AllArgsConstructor
public class BearerAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    /**
     * @param authentication {@link BearerAuthenticationToken}
     * @return {@link UsernamePasswordAuthenticationToken}
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        String username = JwtTokenUtil.getUsernameFromToken(token);
        if (username == null) {
            throw new BadCredentialsException("token invalid");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!JwtTokenUtil.isTokenExpired(token)) {
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } else {
            throw new CredentialsExpiredException("token expired");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BearerAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
