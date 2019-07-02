package com.ty.yjx.love.schedule;

import com.ty.yjx.love.service.WechatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

/**
 * @Desc
 * @Author yejx
 * @Date 2019/6/10
 */
@Configuration
@EnableScheduling
@Component
@Slf4j
public class WechatScheduing implements SchedulingConfigurer {

    @Value("${wechat.config.delay}")
    private long delay;

    @Autowired
    private WechatService wechatService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {

        scheduledTaskRegistrar.addFixedDelayTask(new Runnable() {
            @Override
            public void run() {
                log.info("scheduling updateAccessToken...");
                try {
                    wechatService.updateAccessToken();
                } catch (Exception e) {
                    log.info("scheduling updateAccessToken... Exception {}", e);
                }
            }
        }, delay);
    }
}
