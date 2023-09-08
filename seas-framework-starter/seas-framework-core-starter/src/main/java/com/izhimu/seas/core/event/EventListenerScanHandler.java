package com.izhimu.seas.core.event;

import com.izhimu.seas.core.scan.IScanHandler;
import com.izhimu.seas.core.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.Set;

/**
 * 事件监听器扫描处理
 *
 * @author haoran
 * @version v1.0
 */
@SuppressWarnings("unused")
@Slf4j
public class EventListenerScanHandler implements IScanHandler {

    @Override
    public String name() {
        return "EventListener";
    }

    @Override
    public void scan(Reflections reflections) {
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(EventListener.class);
        for (Class<?> aClass : typesAnnotatedWith) {
            try {
                EventListener annotation = aClass.getAnnotation(EventListener.class);
                if (annotation.autoRegister()) {
                    Object o = aClass.getDeclaredConstructor().newInstance();
                    if (o instanceof IEventListener listener) {
                        EventListenerWrapper wrapper = new EventListenerWrapper();
                        wrapper.setListener(listener);
                        wrapper.setEvents(annotation.value());
                        wrapper.setOrder(annotation.order());
                        wrapper.setAsync(annotation.async());
                        EventManager.register(wrapper);
                    }
                }
            } catch (Exception e) {
                log.error(LogUtil.format("ScanServer", "EventListener", "Error"), e);
            }
        }
    }
}
