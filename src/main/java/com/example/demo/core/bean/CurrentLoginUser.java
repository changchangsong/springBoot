package com.example.demo.core.bean;

import java.io.Serializable;

/**
 * 
 * @ClassName: CurrentLoginUser 
 * @Description: 当前登录用户
 * @author wangyin 
 * @date 2018年2月28日 下午6:20:13
 */
public class CurrentLoginUser implements Serializable{
	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */ 
	private static final long serialVersionUID = 1L;
	/**
	 * 用户编号
	 */
	private String userId;
	/**
	 * 登录token
	 */
	private String token;
	
	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 是否超级管理员
	 */
	private boolean isRootUser;
	/**
	 * 真实姓名
	 */
	private String realName;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setIsRootUser(boolean isRootUser) {
		this.isRootUser = isRootUser;
	}

	public boolean getIsRootUser() {
		return isRootUser;
	}
}
