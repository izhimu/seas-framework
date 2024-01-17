package ${packageName}.controller;

import com.izhimu.seas.data.controller.AbsBaseController;
import ${packageName}.entity.${className};
import ${packageName}.service.${className}Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${tableDesc}控制层
 *
 * @author ${author}
 * @version v1.0
 */
@RestController
@RequestMapping("${pathName}")
public class ${className}Controller extends AbsBaseController<${className}Service, ${className}> {
    @Override
    public String logPrefix() {
        return "${tableDesc}";
    }
}
