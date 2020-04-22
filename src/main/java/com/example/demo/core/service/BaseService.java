package com.example.demo.core.service;

import com.example.demo.core.bean.CurrentLoginUser;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "base") //抽取缓存公共配置，可以标注在类上
public class BaseService {

    /**
     * @Title : removeAttr @Description: 删除返回结果属性 @param name @return :
     * BaseService @author :wangyin @Create Date : 2018年7月2日
     * 下午5:51:00 @throws
     */
    protected BaseService removeAttr(String name) {
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.removeAttribute(name);
        return this;
    }

    /**
     * @Title : setAttr @Description: 设置返回结果属性 @param name @param value @return:
     * BaseService @author :wangyin @Create Date : 2018年7月2日下午5:50:33 @throws
     */
    protected BaseService setAttr(String name, Object value) {
        HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        Object attribute = request.getAttribute("serviceReturnResult");
        //instanceof关键字的作用是判断一个对象是否是一个具体类的实例
        if (attribute instanceof LinkedHashMap) {
            @SuppressWarnings("unchecked")//告诉编译器忽略 unchecked 警告信息，如使用List，ArrayList等未进行参数化产生的警告信息。
            Map<String, Object> result = (LinkedHashMap<String, Object>) attribute;
            result.put(name, value);
        }
        return this;
    }

    /**
     * @Title : setResult @Description: 设置返回结果 @param value @return :
     * BaseService @author :wangyin @Create Date : 2018年7月2日
     * 下午5:50:52 @throws
     */
    protected BaseService setResult(Object value) {
        HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        request.setAttribute("serviceReturnResult", value);
        return this;
    }

    /**
     * @Title : setCurrentLoginUser @Description: 设置当前登录用户 @param
     * currentLoginUser : void @author :wangyin @Create Date : 2018年7月2日
     * 下午5:50:20 @throws
     */
    protected void setCurrentLoginUser(CurrentLoginUser currentLoginUser) {
        HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().setAttribute(CurrentLoginUser.class.getSimpleName(), currentLoginUser);
    }

    /**
     * @Title : getCurrentLoginUser @Description: 获取当前登录用户 @return :
     * CurrentLoginUser @author :wangyin @Create Date : 2018年3月5日
     * 下午4:32:44 @throws
     */
    public CurrentLoginUser getCurrentLoginUser() {
        HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        CurrentLoginUser currentLoginUser = (CurrentLoginUser) request.getSession()
                .getAttribute(CurrentLoginUser.class.getSimpleName());
        if (currentLoginUser == null)
            return new CurrentLoginUser();
        return currentLoginUser;
    }
}
