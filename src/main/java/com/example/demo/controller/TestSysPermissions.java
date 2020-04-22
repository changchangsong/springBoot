package com.example.demo.controller;

import com.example.demo.core.bean.CurrentLoginUser;
import com.example.demo.service.SysPermissionsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sysPermissions")
@Api(value = "/sysPermissions", description = "测试用户登录")
public class TestSysPermissions {
    @Autowired
    private SysPermissionsService sysPermissionsService;

    @ApiOperation(value="登录", notes="登录系统")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "用户登录名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "loginPassword", value = "用户密码", required = true, dataType = "String")
    })
    @RequestMapping(value="/login",method= RequestMethod.POST)
    public void login(@RequestParam(value="loginName") String loginName, @RequestParam(value="loginPassword") String loginPassword) throws Exception{
        this.sysPermissionsService.verifyLogin(loginName, loginPassword);
    }

    @ApiOperation(value="退出登录", notes="退出登录")
    @RequestMapping(value="/exitLogin",method= RequestMethod.GET)
    public Map<String,Object> exitLogin(HttpSession session){
        session.removeAttribute(CurrentLoginUser.class.getSimpleName());
        return new HashMap<String,Object>(){{put("status",true);}};
    }

    @ApiOperation(value="获取当前登录用户信息", notes="获取当前登录用户信息")
    @RequestMapping(value="/currentLoginUser",method= RequestMethod.GET)
    public CurrentLoginUser getCurrentLoginUserInfo(){
        return this.sysPermissionsService.getCurrentLoginUser();
    }
}
