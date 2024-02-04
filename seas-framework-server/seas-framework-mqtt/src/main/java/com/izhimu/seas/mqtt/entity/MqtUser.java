package com.izhimu.seas.mqtt.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.data.annotation.Search;
import com.izhimu.seas.data.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * MQTT用户实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("seas_mqt_user")
public class MqtUser extends IdEntity {

    /**
     * 用户名
     */
    @Search
    private String username;
    /**
     * 密码
     */
    @Search
    private String password;
    /**
     * 订阅权限
     */
    private String topicAuth;
}
