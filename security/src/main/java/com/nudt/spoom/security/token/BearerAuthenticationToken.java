package com.nudt.spoom.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * package com.spoom.gear.config.security
 *
 * @author spoomlan
 * @date 2019-03-30
 */

public class BearerAuthenticationToken extends AbstractAuthenticationToken {

  private static final long serialVersionUID = 4649644779033985092L;
  private final Object principal;

  /**
   * @param principal Bearer token
   */
  public BearerAuthenticationToken(Object principal) {
    super(null);
    this.principal = principal;
    setAuthenticated(false);
  }

  public BearerAuthenticationToken(Collection<? extends GrantedAuthority> authorities,
                                   Object principal) {
    super(authorities);
    this.principal = principal;
    super.setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return this.principal;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }

  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    if (isAuthenticated) {
      throw new IllegalArgumentException(
          "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    }

    super.setAuthenticated(false);
  }
}
