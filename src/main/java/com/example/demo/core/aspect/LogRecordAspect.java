package com.example.demo.core.aspect;

import com.alibaba.fastjson.JSON;
import com.example.demo.core.annotations.ClearAspect;
import com.example.demo.core.bean.CurrentLoginUser;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName: ControllerAspect 
 * @Description: 控制层切面,切入到所有带@Controller注解类并且有@RequestMapping申明的方法上
 * @author wangyin 
 * @date 2018年3月7日 上午9:06:21
 */
@Order(1)
@Aspect
@Component
public class LogRecordAspect implements CustomAspect{
	public final Log log = LogFactory.getLog(this.getClass());

//	@Autowired
//	private CommonSysUserOperateRecordDao commonSysUserOperateRecordDao;

	@Pointcut("execution(org.springframework.web.servlet.ModelAndView *(..))")
	public void excludeVoidReturn() {
	}

	@Pointcut("(@target(org.springframework.web.bind.annotation.RestController)||@target(org.springframework.stereotype.Controller))&&(@annotation(org.springframework.web.bind.annotation.RequestMapping)||@annotation(org.springframework.web.bind.annotation.PostMapping)||@annotation(org.springframework.web.bind.annotation.PutMapping)||@annotation(org.springframework.web.bind.annotation.GetMapping)||@annotation(org.springframework.web.bind.annotation.DeleteMapping))")
	public void includeController() {

	}

	@Around("!excludeVoidReturn()&&includeController()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature)signature;
		Method targetMethod = methodSignature.getMethod();

		if(targetMethod.isAnnotationPresent(ClearAspect.class)){
			//包含排除拦截器
			ClearAspect clearAspect = targetMethod.getAnnotation(ClearAspect.class);
			Class<? extends CustomAspect>[] value = clearAspect.value();
			if(Arrays.asList(value).contains(LogRecordAspect.class)){
				//包含排除当前拦截器
				return joinPoint.proceed();
			}
		}

		String operateDesc = "";
		if(targetMethod.isAnnotationPresent(ApiOperation.class)){
			//包含ApiOperation注解
			ApiOperation apiOperation = targetMethod.getAnnotation(ApiOperation.class);
			operateDesc = apiOperation.value();
			log.info("操作描述:"+operateDesc);
		}

		String url = request.getRequestURL().toString();
		String method = request.getMethod();

		CurrentLoginUser currentLoginUser = (CurrentLoginUser) request.getSession()
				.getAttribute(CurrentLoginUser.class.getSimpleName());
		if (currentLoginUser == null)
			currentLoginUser= new CurrentLoginUser();

		String token=request.getHeader("authorization");
		if(StringUtils.isBlank(token)){
			token=request.getParameter("Authorization");
		}

		String[] parameterNames = methodSignature.getParameterNames();

		Object[] param = joinPoint.getArgs();
		Map<String,Object> parmasMap=new HashMap<String,Object>();
		if (parameterNames != null) {
			for (int i = 0; i < parameterNames.length; i++) {
				if(!"request".equals(parameterNames[i])){
					parmasMap.put(parameterNames[i], param[i]);
				}
			}

		}
		log.info("请求url:"+url);
		log.info("请求方式:"+method);
		log.info("请求参数:"+ JSON.toJSONString(parmasMap));

		//保存用户操作系统记录
//		SysUserOperateRecord sysUserOperateRecord = new SysUserOperateRecord();
//		Timestamp now = new Timestamp(System.currentTimeMillis());
//		sysUserOperateRecord.setId(UUID.randomUUID().toString());
//		sysUserOperateRecord.setAddTime(now);
//		sysUserOperateRecord.setControllerName(reqMap[0]);
//		sysUserOperateRecord.setIsDelete("0");
//		sysUserOperateRecord.setLoginUserId(currentLoginUser.getUserId());
//		sysUserOperateRecord.setLoginUserType(loginUserType);
//		sysUserOperateRecord.setOperateDesc(operateDesc);
//		sysUserOperateRecord.setRequestMethod(method);
//		sysUserOperateRecord.setRequestParam(JSON.toJSONString(parmasMap));
//		sysUserOperateRecord.setRequestUrl(url);
//		commonSysUserOperateRecordDao.save(sysUserOperateRecord);

		Object object = null;
		try {
			object = joinPoint.proceed();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return object;
	}
}
