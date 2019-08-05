package com.nudt.spoom.management.controller;

import com.nudt.spoom.common.model.SysUser;
import com.nudt.spoom.common.utils.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * package com.spoom.gear.controller
 *
 * @author spoomlan
 * @date 2019-03-28
 */
@RestController
class UserController {

    @GetMapping("/admin/info")
    public CommonResult getUserInfo() {
        return new CommonResult().success("admin");
    }

    @GetMapping("/teacher/info")
    public CommonResult getUserInfo3() {
        return new CommonResult().success("teacher");
    }

    @GetMapping("/api/info")
    public CommonResult getUserInfo2() {
        return new CommonResult().success("api");
    }


    @PostMapping("/admin/login")
    public CommonResult login(SysUser sysUser) {
        return new CommonResult().success(sysUser);
    }
}
