package com.izhimu.seas.core.util;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.izhimu.seas.core.annotation.View;
import com.izhimu.seas.core.web.entity.IView;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 视图转换工具
 *
 * @author 13346
 * @version v1.0
 */
@UtilityClass
public class ViewUtil {

    public static Map<String, Object> toView(Object obj) {
        Map<String, Object> data = new HashMap<>();
        if (!isView(obj)) {
            return data;
        }
        try {
            Field[] fields = ReflectUtil.getFields(obj.getClass());
            for (Field field : fields) {
                String fieldName = ReflectUtil.getFieldName(field);
                String methodName = getMethodName(fieldName, field.getGenericType().getTypeName());
                View view = field.getAnnotation(View.class);
                if (Objects.nonNull(view)) {
                    if (view.ignore()) {
                        continue;
                    }
                    if (StrUtil.isNotBlank(view.value())) {
                        fieldName = view.value();
                    }
                    if (view.recursionLevel() > 0) {

                    }
                }
                Object fieldObj = ReflectUtil.invoke(obj, methodName);
                if (isView(fieldObj)) {
                    fieldObj = toView(fieldObj);
                } else if (fieldObj instanceof Collection<?> collection) {
                    for (Object o : collection) {
                        fieldObj = toView(o);
                    }
                }
                data.put(fieldName, fieldObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 是否是View对象
     *
     * @param obj Object
     * @return boolean
     */
    public static boolean isView(Object obj) {
        return obj instanceof IView;
    }

    /**
     * 获取Get方法名称
     *
     * @param fieldName String
     * @param fieldType String
     * @return String
     */
    public static String getMethodName(String fieldName, String fieldType) {
        String methodName = CharSequenceUtil.upperFirst(fieldName);
        if (fieldType.equals("boolean")) {
            methodName = "is".concat(methodName);
        } else {
            methodName = "get".concat(methodName);
        }
        return methodName;
    }
}
