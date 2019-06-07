package com.ubatis.circleserver.modules.basic.controller;

import com.ubatis.circleserver.bean.basic.JsonBase;
import com.ubatis.circleserver.util.constant.CS;
import com.ubatis.circleserver.util.daoutils.CM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理 404 页面
 */
@RestController
public class NotFoundErrorController implements ErrorController {

    private final static Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping("/error")
    public JsonBase handleError(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == 404){
            return CM.getFailInfo(CS.RETURN_CODE_TARGET_NOT_FOUND, "404 not found");
        }
        return CM.getFailInfo(CS.RETURN_CODE_UNKNOWN_ERROR, "statusCode: "+statusCode);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
