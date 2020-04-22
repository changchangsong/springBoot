package com.example.demo.service;

import com.example.demo.core.bean.CurrentLoginUser;
import com.example.demo.core.service.BaseService;
import com.example.demo.entity.SysUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SysPermissionsService  extends BaseService {

    /**
     * @Title : 登录验证
     */
    public void verifyLogin(String loginName, String password) {
        SysUser user = new SysUser("admin","admin","系统管理员");

        Map<String, Object> result = new HashMap<String, Object>();
        if (!loginName.equals(user.getLoginName())) {
            result.put("status", false);
            result.put("msg", "未检索到相关用户信息,请核实");
        } else {
            if (user.getLoginPassword().equals(password)) {

                //放置当前用户的信息
                String token = UUID.randomUUID().toString() + ":" + loginName;

                CurrentLoginUser currentLoginUser = new CurrentLoginUser();
                currentLoginUser.setUserId(loginName);
                currentLoginUser.setToken(token);
                currentLoginUser.setIsRootUser(true);
                currentLoginUser.setLoginName(user.getLoginName());
                currentLoginUser.setRealName(user.getRealName());
                //基础信息存session
                this.setCurrentLoginUser(currentLoginUser);

                //保存登录状态
                result.put("status", true);
            } else {
                result.put("status", false);
                result.put("msg", "密码错误,请重新输入");
            }
        }
        this.setResult(result);
    }
}
