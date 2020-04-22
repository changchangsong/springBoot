package com.example.demo.entity;


import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * ${comments}
 *
 * @date 2020-03-27 10:20:24
 */
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录名", dataType = "String", required = false, hidden = false)
    private String loginName;
    @ApiModelProperty(value = "登录密码", dataType = "String", required = false, hidden = false)
    private String loginPassword;
    @ApiModelProperty(value = "真实姓名", dataType = "String", required = false, hidden = false)
    private String realName;

    public SysUser(String loginName, String loginPassword, String realName) {
        this.loginName = loginName;
        this.loginPassword = loginPassword;
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
