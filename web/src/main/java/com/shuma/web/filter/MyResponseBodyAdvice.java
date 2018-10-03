package com.shuma.web.filter;

import com.king.kong.tzj.util.ApiDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 响应处理(同一处理返回值,Swaggers上显示具体的响应对象)
 *
 * @author KingKong
 * @create 2018-09-07 0:42
 **/

@Slf4j
@ControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public Object beforeBodyWrite(Object returnValue, MethodParameter methodParameter,
                                  MediaType mediaType, Class clas, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
       /* 通过 ServerHttpRequest的实现类ServletServerHttpRequest 获得HttpServletRequest
        ServletServerHttpRequest sshr=(ServletServerHttpRequest) serverHttpRequest;
        此处获取到request 是为了取到在拦截器里面设置的一个对象 是我项目需要,可以忽略
        HttpServletRequest request=   sshr.getServletRequest();*/
       log.info("响应：{}",returnValue);
        if (returnValue instanceof ApiDataUtil) {
            return returnValue;
        } else {
            return ApiDataUtil.successResult(returnValue);
        }
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class clas) {
        //获取当前处理请求的controller的方法
        String methodName = methodParameter.getMethod().getName();
        // 不拦截/不需要处理返回值 的方法(uiConfiguration 是swgger的接口页面)
        String method = "uiConfiguration";
        String swagger = "swaggerResources";
        //返回true才会进入上面的方法(去除swagger)
        if (method.equals(methodName) || swagger.equals(methodName) || "securityConfiguration".equals(methodName) || "getDocumentation".equals(methodName)) {
            return false;
        }
        //去除错误异常
        if ("resultParamError".equals(methodName) || "resultError".equals(methodName)) {
            return false;
        }
        return true;
    }
}

