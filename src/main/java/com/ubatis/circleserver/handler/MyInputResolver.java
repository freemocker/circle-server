package com.ubatis.circleserver.handler;

import com.ubatis.circleserver.anno.GetInputs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Type;

/**
 * Created by lance on 2017/10/17.
 */
public class MyInputResolver implements HandlerMethodArgumentResolver {

    private final static Logger logger = LoggerFactory.getLogger(HandlerMethodArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(GetInputs.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        logger.info("开始封装参数...");
        Type type = methodParameter.getGenericParameterType();
        logger.info("type:"+type);
        String genericTypeName = null;
        //如果填了泛型
        if(type.getTypeName().contains("<")){
            genericTypeName = type.getTypeName().substring(type.getTypeName().lastIndexOf("<")+1,type.getTypeName().indexOf(">"));
        }
        logger.info("genericTypeName:"+genericTypeName);




//        String[] atts = nativeWebRequest.getAttributeNames(RequestAttributes.SCOPE_REQUEST);
//        for(String att: atts){
//            logger.info(att+"==========================="+nativeWebRequest.getAttribute(att, RequestAttributes.SCOPE_REQUEST));
//        }


//        Class clazz = Class.forName(methodParameter.getParameterType().getDeclaredAnnotation(GetInputs.class).value());
//        methodParameter.getNestedParameterType();


//        InputMap<AUserBean> inputMap = new InputMap();
//        inputMap.putAll(nativeWebRequest.getParameterMap());
//        AAUserBean obj = (AAUserBean)clazz.newInstance();
//        inputMap.setBean(obj);

        return null;
    }
}
