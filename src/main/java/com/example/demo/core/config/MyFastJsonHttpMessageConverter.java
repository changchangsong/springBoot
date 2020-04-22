package com.example.demo.core.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.commons.io.IOUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 *
 * @ClassName: MyFastJsonHttpMessageConverter
 * @Description: 配合控制层切面，利用request缓存参数
 * @author wangyin
 * @date 2018年4月28日 上午11:39:16
 */
public class MyFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {

	@Override
	public Object read(Type type, //
					   Class<?> contextClass, //
					   HttpInputMessage inputMessage //
	) throws IOException, HttpMessageNotReadableException {

		InputStream in = inputMessage.getBody();
		Object obj = null;
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();

		if (servletRequestAttributes != null) {
			HttpServletRequest request = servletRequestAttributes.getRequest();
			Class<?> clazz = getClass(type, 0);
			Table table = AnnotationUtils.findAnnotation(clazz, Table.class);
			if (RequestMethod.PUT.name().equals(request.getMethod())) {
				String result = IOUtils.toString(in, getFastJsonConfig().getCharset());
				request.setAttribute("requestBody", result);
				if (table != null) {
					obj = JSON.parseObject(result, clazz);
				} else {
					obj = JSON.parseObject(result, type, getFastJsonConfig().getFeatures());
				}
			} else {
				obj = JSON.parseObject(in, getFastJsonConfig().getCharset(), type, getFastJsonConfig().getFeatures());
			}
		} else {
			obj = JSON.parseObject(in, getFastJsonConfig().getCharset(), type, getFastJsonConfig().getFeatures());
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	private Class<Object> getClass(Type type, int i) {
		if (type instanceof ParameterizedType) { // 处理泛型类型
			return getGenericClass((ParameterizedType) type, i);
		} else if (type instanceof TypeVariable) {
			return (Class<Object>) getClass(((TypeVariable<?>) type).getBounds()[0], 0); // 处理泛型擦拭对象
		} else {// class本身也是type，强制转型
			return (Class<Object>) type;
		}
	}

	@SuppressWarnings("unchecked")
	private Class<Object> getGenericClass(ParameterizedType parameterizedType, int i) {

		Object genericClass = parameterizedType.getActualTypeArguments()[i];
		if (genericClass instanceof ParameterizedType) { // 处理多级泛型
			return (Class<Object>) ((ParameterizedType) genericClass).getRawType();
		} else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
			return (Class<Object>) ((GenericArrayType) genericClass).getGenericComponentType();
		} else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
			return (Class<Object>) getClass(((TypeVariable<?>) genericClass).getBounds()[0], 0);
		} else {
			return (Class<Object>) genericClass;
		}
	}
}