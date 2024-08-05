package com.izhimu.seas.ai.service.impl;

import com.izhimu.seas.ai.entity.AiHistory;
import com.izhimu.seas.ai.mapper.AiHistoryMapper;
import com.izhimu.seas.ai.service.AiHistoryService;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AI聊天记录服务层实现
 *
 * @author haoran
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AiHistoryServiceImpl extends BaseServiceImpl<AiHistoryMapper, AiHistory> implements AiHistoryService {
}
