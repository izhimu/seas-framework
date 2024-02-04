package com.izhimu.seas.mqtt.service.impl;

import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.mqtt.entity.MqtUser;
import com.izhimu.seas.mqtt.mapper.MqtUserMapper;
import com.izhimu.seas.mqtt.service.MqtUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * MQTT用户服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MqtUserServiceImpl extends BaseServiceImpl<MqtUserMapper, MqtUser> implements MqtUserService {
}
