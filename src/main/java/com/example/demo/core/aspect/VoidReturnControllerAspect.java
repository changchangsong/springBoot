package com.example.demo.core.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.demo.core.annotations.ClearAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author wangyin
 * @ClassName: ControllerAspect
 * @Description: 控制层切面, 切入到所有带@Controller注解类并且有@RequestMapping申明,返回值为void的方法上
 * @date 2018年3月7日 上午9:06:21
 */
@Order(0)
@Aspect
@Component
public class VoidReturnControllerAspect implements CustomAspect{


    @Pointcut("execution(void *(..))")
    public void includeVoidReturn() {
    }

    @Pointcut("(@target(org.springframework.web.bind.annotation.RestController)||@target(org.springframework.stereotype.Controller))&&(@annotation(org.springframework.web.bind.annotation.RequestMapping)||@annotation(org.springframework.web.bind.annotation.PostMapping)||@annotation(org.springframework.web.bind.annotation.PutMapping)||@annotation(org.springframework.web.bind.annotation.GetMapping)||@annotation(org.springframework.web.bind.annotation.DeleteMapping))")
    public void includeController() {

    }

    /**
     * @param joinPoint
     * @throws Throwable : void
     * @throws
     * @Title : doAroundVoidReturnExecute
     * @Description: 方法执行完了之后执行，跟上返回值（从request获取当前线程存储attr返回至前端）
     * @author :wangyin
     * @Create Date : 2018年5月27日 上午10:12:23
     */
    @AfterReturning("includeVoidReturn()&&includeController()")
    public void doAfterReturning(JoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();

        if (targetMethod.isAnnotationPresent(ClearAspect.class)) {
            //包含排除拦截器
            ClearAspect clearAspect = (ClearAspect) targetMethod.getAnnotation(ClearAspect.class);
            Class<? extends CustomAspect>[] value = clearAspect.value();
            if (Arrays.asList(value).contains(VoidReturnControllerAspect.class)) {
                //包含排除当前拦截器
                return;
            }
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Object result = request.getAttribute("serviceReturnResult");

        String jsonString = JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        try (PrintWriter out = response.getWriter()) {
            out.append(jsonString);
        }
    }

}
