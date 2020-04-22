package com.example.demo.core.annotations;


import com.example.demo.core.aspect.CustomAspect;

import java.lang.annotation.*;

/**
 * 
 * @ClassName: AspectClear 
 * @Description: 自定义拦截器清除
 * @author wangyin 
 * @date 2018年5月10日 上午9:22:30
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ClearAspect {
	Class<? extends CustomAspect>[] value() default {};
}
