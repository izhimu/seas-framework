package com.izhimu.seas.core.event;

import com.izhimu.seas.core.scan.IScanHandler;
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
        return "事件监听器扫描处理";
    }

    @Override
    public void scan(Reflections reflections) {
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(EventListener.class);
        for (Class<?> aClass : typesAnnotatedWith) {
            try {
                EventListener annotation = aClass.getAnnotation(EventListener.class);
                if (annotation.autoRegister()) {
                    Object o = aClass.getDeclaredConstructor().newInstance();
                    if (o instanceof IEventListener) {
                        EventManager.register((IEventListener) o);
                    }
                }
            } catch (Exception e) {
                log.error("EventListener Scan Error", e);
            }
        }
    }
}
