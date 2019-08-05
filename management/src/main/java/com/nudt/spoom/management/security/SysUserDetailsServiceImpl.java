package com.nudt.spoom.management.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nudt.spoom.common.model.SysUser;
import com.nudt.spoom.management.service.SysUserService;
import com.nudt.spoom.security.model.SysUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * package com.nudt.spoom.management.security
 *
 * @author spoomlan
 * @date 2019-08-05
 */
@Service("sysUserDetailsService")
public class SysUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public SysUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", username));
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        return new SysUserDetail(sysUser.getId(), sysUser.getUsername(), sysUser.getPassword(), sysUser.getStatus(),
                true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList(sysUser.getRoles()));
    }
}
