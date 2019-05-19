package com.ubatis.circleserver.modules.basic.service;

import com.ubatis.circleserver.modules.basic.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lance on 2017/12/19.
 */
@Service
public class CommonService {

    @Autowired
    private CommonDao dao;

}
