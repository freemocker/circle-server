package com.ubatis.circleserver.handler;

import com.ubatis.circleserver.bean.basic.JsonBase;
import com.ubatis.circleserver.exception.ValidException;
import com.ubatis.circleserver.util.JsonUtil;
import com.ubatis.circleserver.util.constant.CS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** 统一异常处理
 * Created by lance on 2017/4/25.
 */
@RestControllerAdvice
public class AuthExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(AuthExceptionHandler.class);

    @ExceptionHandler(value = ValidException.class)
    public JsonBase handle(ValidException ex){
        JsonBase jsonBase = new JsonBase(ex.getCode(),ex.getMessage());
        return  jsonBase;
    }

    @ExceptionHandler(value = Exception.class)
    public JsonBase handleUnCatchException(Exception ex){
        ex.printStackTrace();
        JsonBase jsonBase = new JsonBase();
        jsonBase.setCode(CS.RETURN_CODE_UNKNOWN_ERROR);
        jsonBase.setInfo(ex.getMessage());
        logger.error("统一错误处理:{}", JsonUtil.toJson(jsonBase));
        return  jsonBase;
    }


}
