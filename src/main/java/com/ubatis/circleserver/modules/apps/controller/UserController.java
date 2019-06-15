package com.ubatis.circleserver.modules.apps.controller;

import com.ubatis.circleserver.anno.RequireParams;
import com.ubatis.circleserver.anno.RequireSign;
import com.ubatis.circleserver.bean.basic.JsonBase;
import com.ubatis.circleserver.bean.param.ParamAcUser;
import com.ubatis.circleserver.modules.apps.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 小程序login获取code，发送到后端获取openid
     * @param paramAcUser
     * @return
     */
    @RequestMapping("/getOpenId")
    @RequireParams("circle_id,login_code")
    public JsonBase getOpenId(ParamAcUser paramAcUser) {
        return userService.getOpenId(paramAcUser);
    }

    /**
     * 用户 add or update <br/>
     * 根据openid，如果不存在就新增
     * @param paramAcUser
     * @return
     */
    @RequestMapping("/add/update")
    @RequireParams("circle_id,openid")
    @RequireSign
    public JsonBase addOrUpdateUser(ParamAcUser paramAcUser) {
        return userService.addOrUpdateUser(paramAcUser);
    }

    @RequestMapping("/info")
    @RequireParams("openid")
    @RequireSign
    public JsonBase getUserInfo(ParamAcUser paramAcUser) {
        return userService.getUserInfo(paramAcUser);
    }

}
