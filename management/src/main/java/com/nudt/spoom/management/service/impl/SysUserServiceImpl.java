package com.nudt.spoom.management.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nudt.spoom.management.dao.SysUserMapper;
import com.nudt.spoom.common.model.SysUser;
import com.nudt.spoom.management.service.SysUserService;
/**
 * package com.nudt.spoom.management.service.impl
 * @author spoomlan
 * @date 2019-08-05
 */

@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

}
