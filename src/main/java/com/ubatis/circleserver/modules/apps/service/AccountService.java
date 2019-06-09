package com.ubatis.circleserver.modules.apps.service;

import com.ubatis.circleserver.bean.basic.JsonBase;
import com.ubatis.circleserver.bean.param.ParamAcUser;
import com.ubatis.circleserver.util.daoutils.CM;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public JsonBase addUser(ParamAcUser paramAcUser) {
        paramAcUser.setId(paramAcUser.getGenId());
        int ret = paramAcUser.save();
//        paramAcUser.get
        return CM.getReturnInfo(ret);
    }

}
