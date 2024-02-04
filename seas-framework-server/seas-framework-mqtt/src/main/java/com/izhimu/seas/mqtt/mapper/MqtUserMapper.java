package com.izhimu.seas.mqtt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.izhimu.seas.mqtt.entity.MqtUser;
import org.springframework.stereotype.Repository;

/**
 * MQTT用户映射层
 *
 * @author haoran
 * @version v1.0
 */
@Repository
public interface MqtUserMapper extends BaseMapper<MqtUser> {
}
