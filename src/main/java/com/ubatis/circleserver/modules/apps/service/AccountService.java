package com.ubatis.circleserver.modules.apps.service;

import com.ubatis.circleserver.bean.basic.JsonBase;
import com.ubatis.circleserver.bean.param.ParamAcUser;
import com.ubatis.circleserver.util.daoutils.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private BaseDao dao;

    public JsonBase addUser(ParamAcUser paramAcUser) {
        paramAcUser.setId(dao.getID());
//        paramAcUser.get
        return null;
    }

}
