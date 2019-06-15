package com.ubatis.circleserver.modules.apps.service;

import com.ubatis.circleserver.bean.AcUserBean;
import com.ubatis.circleserver.bean.basic.JsonBase;
import com.ubatis.circleserver.bean.param.ParamAcUser;
import com.ubatis.circleserver.modules.apps.service.sql.UserSQLKt;
import com.ubatis.circleserver.modules.common.service.CommonService;
import com.ubatis.circleserver.util.JsonUtil;
import com.ubatis.circleserver.util.constant.CS;
import com.ubatis.circleserver.util.daoutils.CM;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.*;

@Service
public class UserService {

    private final static Logger logger = getLogger(UserService.class);

    @Autowired
    private CommonService commonService;

    public JsonBase getOpenId(ParamAcUser paramAcUser) {
        return CM.getReturnInfo(commonService.getOpenId(paramAcUser.getCircle_id()));
    }

    public JsonBase addOrUpdateUser(ParamAcUser paramAcUser) {
        JsonBase getUserInfoRet = getUserInfo(paramAcUser);
        if (getUserInfoRet.getCode() != CS.RETURN_CODE_SUCCESS) {
            // 新增
            paramAcUser.save();
            return CM.getReturnInfo("new user!");
        }
        // 更新
        paramAcUser.setLogin_times(((AcUserBean)getUserInfoRet.getHeader()).getLogin_times() + 1);
        paramAcUser.update();
        return CM.getReturnInfo("user【"+paramAcUser.getOpenid() + "】login times:" + paramAcUser.getLogin_times());
    }

    public JsonBase getUserInfo(ParamAcUser paramAcUser) {
        AcUserBean acUserBean = (AcUserBean) paramAcUser.getDao().queryForObject(UserSQLKt.getUserInfoListSQL(paramAcUser.getParams()), paramAcUser.getParams(), AcUserBean.class);
        logger.info("userInfo:{}", JsonUtil.toJson(acUserBean));
        if (acUserBean == null) return CM.getFailInfo(CS.RETURN_CODE_INVALID_PARAMETER, "not exist");
        return CM.getReturnHeader(acUserBean);
    }

}
