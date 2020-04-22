package com.example.demo.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.example.demo.core.bean.CurrentLoginUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 权限拦截器
 * @ClassName: PermissionsInterceptor 
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author wangyin 
 * @date 2018年4月8日 上午11:33:48
 */
@Component
public class PermissionsInterceptor implements HandlerInterceptor {

	/**
	 * 是否启用登录拦截
	 */
	@Value("${permission.enable-login-interceptor:false}")
	private boolean enableLoginInterceptor;
	
	/**
	 * 是否启用请求url拦截
	 */
	@Value("${permission.enable-url-interceptor:false}")
	private boolean enableUrlInterceptor;
	
	private static final Pattern skipedUrlPattern = Pattern
			.compile("(/sysPermissions/(authoriz|login|findNewFirst)).*|^(.*(swagger|api-docs)).*|.*\\.(html|js|png|jpg).*|(/dataExtract/*).*|(/error)");
	/**
	 * 
	 * @Title      : noLoginAccess 
	 * @Description: 免验证访问
	 * @param
	 * @return     : boolean
	 * @author     :wangyin
	 * @Create Date : 2018年3月19日 下午6:25:11
	 * @throws
	 */
	private boolean isNoAuthorizAccess(String resourceUrl) {
		return skipedUrlPattern.matcher(resourceUrl).matches();
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String method =request.getMethod();
		if (method.equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
		}else{
			String resourceUrl = request.getRequestURI().toString();
			HttpSession session = request.getSession();
			if (this.isNoAuthorizAccess(resourceUrl))
				return true;

			String token=request.getHeader("authorization");
			if(StringUtils.isBlank(token)){
				token=request.getParameter("Authorization");
			}
			CurrentLoginUser currentLoginUser= (CurrentLoginUser) session.getAttribute(CurrentLoginUser.class.getSimpleName());
			Map<String, Object> result = new HashMap<String, Object>();
			if(enableLoginInterceptor) {
				//启用登陆拦截
				if (currentLoginUser == null) {
					result.put("status", false);
					result.put("code", "noLogin");
					result.put("msg", "请先登录");
					this.doSendMsg(response,result);
					return false;
				}
			}
		}
		return true;
	}

	private void doSendMsg(HttpServletResponse response, Map<String, Object> result) {
		String jsonString = JSON.toJSONString(result);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
