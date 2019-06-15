package com.ubatis.circleserver.modules.common.service;

import com.ubatis.circleserver.bean.param.ParamSysAccessToken;
import com.ubatis.circleserver.modules.apps.service.sql.UserSQLKt;
import com.ubatis.circleserver.modules.common.bean.AccessToken;
import com.ubatis.circleserver.util.DateUtil;
import com.ubatis.circleserver.util.HttpClientUtil;
import com.ubatis.circleserver.util.JsonUtil;
import com.ubatis.circleserver.util.daoutils.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lance on 2017/12/19.
 */
@Service
public class CommonService {

    @Autowired
    private BaseDao baseDao;

    public String getOpenId(long circleCode) {
        HashMap params = new HashMap();
        params.put("circle_id", circleCode);
        List<Map<String, Object>> list = baseDao.queryForList(UserSQLKt.getCircleInfoListSQL(), params);
        if (list.size() == 0) return null;
        Map<String, Object> circleInfoMap = list.get(0);
        if (Integer.parseInt(circleInfoMap.get("mp_expires_time").toString()) < DateUtil.getCurrentTimestamp()) {
            String mp_appid = circleInfoMap.get("mp_appid").toString();
            String mp_secret = circleInfoMap.get("mp_secret").toString();
            int delta = 60;//防止网络延迟间隔
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                    + mp_appid +"&secret=" + mp_secret;
            String strRet = HttpClientUtil.doGet(url, null, null);
            AccessToken accessToken = JsonUtil.fromJson(strRet, AccessToken.class);
            ParamSysAccessToken paramSysAccessToken = new ParamSysAccessToken();
            paramSysAccessToken.setId(Integer.parseInt(circleInfoMap.get("id").toString()));//根据id更新记录
            paramSysAccessToken.setMp_access_token(accessToken.getAccess_token());
            paramSysAccessToken.setMp_expires_time(DateUtil.getCurrentTimestamp() + accessToken.getExpires_in() - delta);
            paramSysAccessToken.update();
            return accessToken.getAccess_token();
        } else {
            return circleInfoMap.get("mp_access_token").toString();
        }
    }

}
