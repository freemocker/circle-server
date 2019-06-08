package com.ubatis.circleserver.modules.apps.service;

import com.ubatis.circleserver.bean.basic.JsonBase;
import com.ubatis.circleserver.bean.param.ParamAcUser;
import com.ubatis.circleserver.modules.apps.dao.AppDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AppDao appDao;

    public JsonBase addUser(ParamAcUser paramAcUser) {
        paramAcUser.setId(appDao.getID());
//        paramAcUser.get
        return null;
    }

}
