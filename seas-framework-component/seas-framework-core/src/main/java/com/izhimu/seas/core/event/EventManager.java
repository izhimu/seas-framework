package com.izhimu.seas.core.event;

import cn.hutool.core.util.ReflectUtil;
import com.izhimu.seas.common.utils.KryoUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

/**
 * 事件管理器
 *
 * @author haoran
 * @version v1.0
 * 2021/11/2 14:06
 */
@SuppressWarnings("unused")
@Slf4j
public class EventManager {

    /**
     * 事件监听器器Map
     */
    private static final Map<String, List<IEventListener>> EVENT_LISTENER_MAP = new ConcurrentHashMap<>();

    /**
     * 事件处理线程池
     */
    private static final ForkJoinPool EVENT_POOL = new ForkJoinPool();

    private EventManager() {
    }

    /**
     * 注册事件监听器
     *
     * @param listener 监听器 {@link IEventListener EventListener}
     */
    public static void register(IEventListener listener) {
        if (Objects.nonNull(listener.getEvent())) {
            register(listener.getEvent(), listener);
        }
        if (Objects.nonNull(listener.getEvents())) {
            listener.getEvents().forEach(v -> register(v, listener));
        }
    }

    /**
     * 注册事件监听器
     *
     * @param event    事件 {@link IEvent Event}
     * @param listener 监听器 {@link IEventListener EventListener}
     */
    public static void register(IEvent event, IEventListener listener) {
        String eventTag = event.toString();
        if (!EVENT_LISTENER_MAP.containsKey(eventTag)) {
            EVENT_LISTENER_MAP.put(eventTag, new CopyOnWriteArrayList<>());
        }
        List<IEventListener> filters = EVENT_LISTENER_MAP.get(eventTag);
        if (filters.isEmpty() || !repeat(filters, listener)) {
            filters.add(listener);
        }
        sort(filters);
        log.info("加载[{}]事件监听器 => {}", event, listener.getClass().getSimpleName());
    }

    /**
     * 取消事件注册
     *
     * @param key key {@link IEvent Event}
     */
    public static void unRegister(IEvent key) {
        EVENT_LISTENER_MAP.remove(key.toString());
        log.info("取消[{}]事件注册", key);
    }

    /**
     * 触发无参事件
     *
     * @param key key {@link IEvent Event}
     */
    public static void trigger(IEvent key) {
        trigger(key.toString());
    }

    /**
     * 触发无参事件
     *
     * @param key key {@link IEvent Event}
     */
    public static void trigger(String key) {
        if (!EVENT_LISTENER_MAP.containsKey(key)) {
            log.warn("[{}]事件未注册", key);
            return;
        }
        List<IEventListener> listeners = EVENT_LISTENER_MAP.get(key);
        Map<Boolean, List<IEventListener>> asyncMap = listeners.stream().collect(Collectors.groupingBy(IEventListener::async));
        asyncMap.forEach((k, ls) -> {
            if (k) {
                EVENT_POOL.submit(() -> ls.parallelStream().forEach(v -> {
                    log.info("触发[{}]并行事件监听器 => {}", key, v.getClass().getSimpleName());
                    try {
                        v.onEvent(null);
                    } catch (Exception e) {
                        log.error("事件监听器 => {} | error : {}", v.getClass().getSimpleName(), e.getMessage());
                    }
                }));
            } else {
                Iterator<IEventListener> iterator = ls.iterator();
                boolean flag = true;
                while (flag && iterator.hasNext()) {
                    IEventListener listener = iterator.next();
                    log.info("触发[{}]串行事件监听器 => {}", key, listener.getClass().getSimpleName());
                    try {
                        flag = listener.onEvent(null);
                    } catch (Exception e) {
                        log.error("事件监听器 => {} | error : {}", listener.getClass().getSimpleName(), e.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 触发事件
     *
     * @param key  {@link IEvent Event}
     * @param data 数据
     */
    public static void trigger(IEvent key, Object data) {
        trigger(key.toString(), data);
    }

    /**
     * 触发事件
     *
     * @param key  key
     * @param data 数据
     */
    public static void trigger(String key, Object data) {
        Method getTrackId = ReflectUtil.getMethodByName(data.getClass(), "getTrackId");
        String trackId;
        try {
            trackId = Objects.nonNull(getTrackId) ? (String) getTrackId.invoke(data) : "";
        } catch (Exception e) {
            log.error(e.getMessage());
            trackId = "";
        }
        if (!EVENT_LISTENER_MAP.containsKey(key)) {
            log.warn("<{}> [{}]事件未注册 => {}", trackId, key, data);
            return;
        }
        List<IEventListener> listeners = EVENT_LISTENER_MAP.get(key);
        Map<Boolean, List<IEventListener>> asyncMap = listeners.stream().collect(Collectors.groupingBy(IEventListener::async));
        String finalTrackId = trackId;
        asyncMap.forEach((k, ls) -> {
            if (k) {
                EVENT_POOL.submit(() -> ls.parallelStream().forEach(v -> {
                    log.info("<{}> 触发[{}]并行事件监听器 => {} | {}", finalTrackId, key, v.getClass().getSimpleName(), data);
                    try {
                        v.onEvent(v.copy(data));
                    } catch (Exception e) {
                        log.error("事件监听器 => {} | error : {}", v.getClass().getSimpleName(), e.getMessage());
                    }
                }));
            } else {
                Iterator<IEventListener> iterator = ls.iterator();
                boolean flag = true;
                while (flag && iterator.hasNext()) {
                    IEventListener listener = iterator.next();
                    log.info("<{}> 触发[{}]串行事件监听器 => {} | {}", finalTrackId, key, listener.getClass().getSimpleName(), data);
                    try {
                        flag = listener.onEvent(KryoUtil.deserialize(KryoUtil.serialize(data)));
                    } catch (Exception e) {
                        log.error("事件监听器 => {} | error : {}", listener.getClass().getSimpleName(), e.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 排序
     *
     * @param listeners 监听器链集合 {@link IEventListener EventListener}
     */
    private static void sort(List<IEventListener> listeners) {
        listeners.sort(Comparator.comparing(IEventListener::getOrder));
    }

    /**
     * 检查重复
     *
     * @param listeners 监听器链集合 {@link IEventListener EventListener}
     * @param listener  待检查监听器 {@link IEventListener EventListener}
     * @return true|false
     */
    private static boolean repeat(List<IEventListener> listeners, IEventListener listener) {
        return listeners.stream()
                .map(v -> v.getClass().getName())
                .anyMatch(v -> v.equals(listener.getClass().getName()));
    }
}
