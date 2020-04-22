package com.example.demo.core.aspect;

import com.example.demo.core.exception.BusinessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyin
 * @ClassName: 全局异常处理
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2018年2月28日 上午11:18:03
 */
@ControllerAdvice
public class GlobalExceptionAspect {
    /**
     * 所有异常报错
     *
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map<String, Object> allExceptionHandler(Exception exception) throws Exception {

        if(exception instanceof BusinessException){
            //指定的业务异常,直接往外抛
            throw new Exception(exception);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", false);
        result.put("msg", exception.getMessage());
        return result;
    }


}
