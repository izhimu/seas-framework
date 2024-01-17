package ${packageName}.service.impl;

import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import ${packageName}.entity.${className};
import ${packageName}.mapper.${className}Mapper;
import ${packageName}.service.${className}Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ${tableDesc}服务层实现
 *
 * @author ${author}
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ${className}ServiceImpl extends BaseServiceImpl<${className}Mapper, ${className}> implements ${className}Service {
}
