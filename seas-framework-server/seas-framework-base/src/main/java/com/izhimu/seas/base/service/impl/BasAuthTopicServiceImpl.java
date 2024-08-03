package com.izhimu.seas.base.service.impl;

import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.base.entity.BasAuthTopic;
import com.izhimu.seas.base.mapper.BasAuthTopicMapper;
import com.izhimu.seas.base.service.BasAuthTopicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 角色主题关联表服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasAuthTopicServiceImpl extends BaseServiceImpl<BasAuthTopicMapper, BasAuthTopic> implements BasAuthTopicService {
    @Override
    public boolean removeByRoleId(Serializable roleId) {
        return this.lambdaUpdate()
                .eq(BasAuthTopic::getRoleId, roleId)
                .remove();
    }

    @Override
    public boolean updateAuthTopic(BasAuthTopic entity) {
        removeByRoleId(entity.getRoleId());
        List<Long> topicIds = entity.getTopicIds();
        if (Objects.nonNull(topicIds)) {
            return saveBatch(topicIds.stream()
                    .map(topicId -> {
                        BasAuthTopic authTopic = new BasAuthTopic();
                        authTopic.setRoleId(entity.getRoleId());
                        authTopic.setTopicId(topicId);
                        return authTopic;
                    }).toList());
        }
        return true;
    }

    @Override
    public List<Long> getTopicIdsByRoleId(Serializable roleId) {
        return this.lambdaQuery()
                .eq(BasAuthTopic::getRoleId, roleId)
                .list()
                .stream()
                .map(BasAuthTopic::getTopicId)
                .toList();
    }
}
