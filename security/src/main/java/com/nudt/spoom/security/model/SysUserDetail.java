package com.nudt.spoom.security.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * package com.nudt.spoom.security.model
 *
 * @author spoomlan
 * @date 2019-08-05
 */

@Getter
public class SysUserDetail extends User {

    private static final long serialVersionUID = 371972480162612141L;
    private Long sysUserId;

    public SysUserDetail(Long sysUserId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.sysUserId = sysUserId;
    }
}
