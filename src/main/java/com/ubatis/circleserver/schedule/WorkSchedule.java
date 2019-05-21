package com.ubatis.circleserver.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 跑批
 * cron表达式生成：http://cron.qqe2.com/
 */
@Component
public class WorkSchedule {

    private final static Logger logger = LoggerFactory.getLogger(WorkSchedule.class);

    /**
     * 自动收货<br/>
     * 如果超过15天，设置为已完成状态
     * @Scheduled(cron = "0/20 * * * * ? ") 测试
     */
    @Scheduled(cron = "0 0 3 * * ? ")
    public void confirmReceived() {

    }

    /**
     * 删除未支付订单
     * 超过2天未支付
     */
//     @Scheduled(cron = "0/20 * * * * ? ")
    @Scheduled(cron = "0 0 2 * * ? ")
    public void deleteUnpaidOrder() {

    }

}
