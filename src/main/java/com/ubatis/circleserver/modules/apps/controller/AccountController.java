package com.ubatis.circleserver.modules.apps.controller;

import com.ubatis.circleserver.anno.RequireParams;
import com.ubatis.circleserver.bean.basic.JsonBase;
import com.ubatis.circleserver.bean.param.ParamAcUser;
import com.ubatis.circleserver.modules.apps.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ac")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/user/add")
    @RequireParams("circle_id,openid")
    public JsonBase addUser(ParamAcUser paramAcUser) {
        return accountService.addUser(paramAcUser);
    }

    @RequestMapping("/user/update")
    public JsonBase updateUser() {

        return null;
    }

    @RequestMapping("/user/list")
    public JsonBase queryUser() {

        return null;
    }

    @RequestMapping("/manager/add")
    public JsonBase addManager() {

        return null;
    }

    @RequestMapping("/manager/update")
    public JsonBase updateManager() {

        return null;
    }

    @RequestMapping("/manager/list")
    public JsonBase queryManager() {

        return null;
    }

    @RequestMapping("/circle/add")
    public JsonBase addCircle() {

        return null;
    }

    @RequestMapping("/circle/update")
    public JsonBase updateCircle() {

        return null;
    }

    @RequestMapping("/circle/list")
    public JsonBase queryCircle() {

        return null;
    }

}
