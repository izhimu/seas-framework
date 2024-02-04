package com.izhimu.seas.mqtt.controller;

import com.izhimu.seas.data.controller.AbsBaseController;
import com.izhimu.seas.mqtt.entity.MqtUser;
import com.izhimu.seas.mqtt.service.MqtUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MQTT用户控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/mqt/user")
public class MqtUserController extends AbsBaseController<MqtUserService, MqtUser> {
    @Override
    public String logPrefix() {
        return "MQTT用户";
    }
}
