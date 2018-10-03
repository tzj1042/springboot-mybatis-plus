package com.shuma.web.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * AOP日志处理
 *
 * @author KingKong
 * @create 2018-09-06 10:17
 **/

@Slf4j
@Aspect
@Configuration
public class ApiLogAspect {

    /**
     * 定义Pointcut，Pointcut的名称 就是simplePointcut，此方法不能有返回值，该方法只是一个标示
     * @annotation 指定自定义注解
     */
    @Pointcut("@annotation(com.shuma.web.config.ApiLog)")
    public void declareJoinPointExpression() {

    }

    /**
     *
     * @param joinPoint
     */
    @Before("declareJoinPointExpression()")
    public void addBeforeLogger(JoinPoint joinPoint) {
        try {
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();

            String url = request.getRequestURL().toString();
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String ip = request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");
            Cookie cookie = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().endsWith("Token")) {
                        cookie = c;
                        break;
                    }
                }
            }
            String CookieString=cookie==null?"": JSONObject.toJSONString(cookie);
            String params =JSONObject.toJSONString(request.getParameterMap());

            String apiName = "";
            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();

            for (Method m : methods) {
                if (m.getName().equals(methodName)) {
                    Class[] clazzs = m.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        if (m.getDeclaredAnnotation(ApiLog.class) != null) {
                            apiName = m.getDeclaredAnnotation(ApiLog.class).value();
                            break;
                        }
                    }
                }
            }
            log.info("apiName:{},IP:{}, url: {},uri: {}, method: {}, params: {},Cookie:{},UserAgent:{}",
                    apiName,ip,url,uri, method, params, CookieString,userAgent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

   /* @AfterReturning(value = "declareJoinPointExpression()", returning = "returnObj")
    public void addAfterReturningLogger(JoinPoint joinPoint, Object returnObj) {
        System.out.println("结束");
    }

    @AfterThrowing(pointcut = "declareJoinPointExpression()", throwing = "ex")
    public void addAfterThrowingLogger(JoinPoint joinPoint, Exception ex) {
        System.out.println("异常");
    }*/

    @Around(value = "declareJoinPointExpression()")
    public Object doAround(ProceedingJoinPoint proceeding) throws Throwable {
        long beforeTime=System.currentTimeMillis();
        //执行被拦截的方法 result是返回结果
        Object result = proceeding.proceed();
        //debug模式下才计算方法耗时
        if (log.isDebugEnabled()) {
            long afterTime=System.currentTimeMillis();
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();
            log.info("请求：{} , 耗时：{}ms",request.getRequestURI(),afterTime-beforeTime);
        }
        //此处可以在log输出result，依据业务要求处理
        return result;
    }
}
