package com.example.demo.core.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.example.demo.core.interceptor.PermissionsInterceptor;
import com.example.demo.core.interceptor.ServiceReturnResultInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
public class AdapterConfig implements WebMvcConfigurer {

    @Autowired
    private PermissionsInterceptor permissionsInterceptor;

    @Autowired
    private ServiceReturnResultInterceptor serviceReturnResultInterceptor;

    @Value("${spring.http.converters.preferred-json-mapper}")
    private String preferredJsonMapper;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    //注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截规则：所有请求都拦截
        registry.addInterceptor(serviceReturnResultInterceptor).addPathPatterns("/**");
        registry.addInterceptor(permissionsInterceptor).addPathPatterns("/**");
    }

    /**
     * @Title : fastJsonHttpMessageConverters
     * @Description:使用fastJson进行json互转
     * @return : HttpMessageConverters
     * @author:wangyin
     * @Create Date : 2018年3月3日 下午2:08:11
     * @throws
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        if ("fastjson".equals(preferredJsonMapper)) {
            FastJsonHttpMessageConverter fastConverter = new MyFastJsonHttpMessageConverter();
            FastJsonConfig fastJsonConfig = new FastJsonConfig();
            fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
            fastConverter.setFastJsonConfig(fastJsonConfig);

            ValueFilter valueFilter = new ValueFilter() {
                public Object process(Object o, String s, Object o1) {
                    return o1==null?"":o1;
                }
            };
            fastJsonConfig.setSerializeFilters(valueFilter);

            // 处理中文乱码问题
            List<MediaType> fastMediaTypes = new ArrayList<>();
            fastMediaTypes.add(MediaType.APPLICATION_JSON);
            fastConverter.setSupportedMediaTypes(fastMediaTypes);
            fastConverter.setFastJsonConfig(fastJsonConfig);

            converters.add(fastConverter);
        }

    }

}
