package com.nudt.spoom.management.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nudt.spoom.common.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * package com.nudt.spoom.management.dao
 * @author spoomlan
 * @date 2019-08-05
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}