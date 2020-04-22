package com.example.demo.core.utils.easypoi;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * excel导入
 * 数据验证规则
 */
public class DataValidationRulesUtil {

	public <T> Boolean DataValidation(T t) throws Exception {

		Field[] fields = t.getClass().getDeclaredFields(); // 获取该类的字段
		for (Field field : fields) // 遍历字段
		{
			PropertyDescriptor pd = new PropertyDescriptor(field.getName(), t.getClass());
			Method getMethod = pd.getReadMethod();

			Verification annotation = field.getAnnotation(Verification.class);
			if (annotation != null) {
				if (!Pattern.compile(annotation.regular()).matcher((CharSequence) getMethod.invoke(t)).find()
						|| StringUtils.isBlank((CharSequence) getMethod.invoke(t))) {
					return false;
				}
			}
		}

		return true;
	}
}
