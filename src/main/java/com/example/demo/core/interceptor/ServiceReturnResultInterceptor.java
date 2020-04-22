package com.example.demo.core.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * server层返回结果拦截器
 */
@Component
public class ServiceReturnResultInterceptor implements HandlerInterceptor {

    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> result = new LinkedHashMap<>();
        request.setAttribute("serviceReturnResult", result);
//        System.out.println("已经进行拦截了。。。。。。。。");
        return true;
    }
}
