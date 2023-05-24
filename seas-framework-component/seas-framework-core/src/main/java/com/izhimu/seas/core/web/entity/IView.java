package com.izhimu.seas.core.web.entity;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.izhimu.seas.common.utils.JsonUtil;
import com.izhimu.seas.core.annotation.View;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author haoran
 * @version v1.0
 */
public interface IView {

    default String toView() {
        Map<String, Object> data = new HashMap<>();
        try {
            Field[] fields = ReflectUtil.getFields(this.getClass());
            for (Field field : fields) {
                field.setAccessible(true);
                View view = field.getAnnotation(View.class);
                if (Objects.isNull(view)) {
                    data.put(field.getName(), field.get(this));
                } else {
                    if (view.ignore()) {
                        continue;
                    }
                    String fieldName = view.value();
                    if (StrUtil.isBlank(fieldName)) {
                        fieldName = field.getName();
                    }
                    data.put(fieldName, field.get(this));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonUtil.toJsonStr(data);
    }
}
