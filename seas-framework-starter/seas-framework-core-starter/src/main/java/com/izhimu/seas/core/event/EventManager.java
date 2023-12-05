package com.izhimu.seas.core.event;

import cn.dev33.satoken.stp.StpUtil;
import com.izhimu.seas.core.holder.LoginIdHolder;
import com.izhimu.seas.core.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    
    private static final String LOG_NAME = "EventManager";

    /**
     * 串行事件监听器器Map
     */
    private static final Map<String, List<EventListenerWrapper>> SYNC_EVENT_LISTENER_MAP = new ConcurrentHashMap<>();
    /**
     * 并行事件监听器器Map
     */
    private static final Map<String, List<EventListenerWrapper>> ASYNC_EVENT_LISTENER_MAP = new ConcurrentHashMap<>();

    private static final Set<String> NO_LISTENER_EVENT = new HashSet<>();

    /**
     * 事件处理线程池
     */
    private static final ExecutorService EVENT_POOL = Executors.newThreadPerTaskExecutor(
            Thread.ofVirtual().name("Event-V#", 1).factory()
    );

    private EventManager() {
    }

    /**
     * 注册事件监听器
     *
     * @param wrapper 事件监听器包装
     */
    public static void register(EventListenerWrapper wrapper) {
        String[] events = wrapper.getEvents();
        Map<String, List<EventListenerWrapper>> map = wrapper.isAsync() ? ASYNC_EVENT_LISTENER_MAP : SYNC_EVENT_LISTENER_MAP;
        for (String event : events) {
            if (!map.containsKey(event)) {
                map.put(event, new CopyOnWriteArrayList<>());
            }
            List<EventListenerWrapper> list = map.get(event);
            list.add(wrapper);
            sort(list);
            log.info(LogUtil.format(LOG_NAME, event, "Loading {}", Map.of("Async", wrapper.isAsync())), wrapper.getListener().getClass().getSimpleName());
        }
    }

    /**
     * 触发事件
     *
     * @param key  key
     * @param data data
     */
    public static void trigger(String key, Object data) {
        if (NO_LISTENER_EVENT.contains(key)) {
            return;
        }
        boolean hasSync = SYNC_EVENT_LISTENER_MAP.containsKey(key);
        boolean hasAsync = ASYNC_EVENT_LISTENER_MAP.containsKey(key);
        if (!hasSync && !hasAsync) {
            NO_LISTENER_EVENT.add(key);
            log.warn(LogUtil.format(LOG_NAME, key, "Not Found Listener"));
            return;
        }
        Object loginId = LoginIdHolder.get();
        if (Objects.isNull(loginId)) {
            try {
                loginId = StpUtil.getSession().getLoginId();
            } catch (Exception ignored) {
            }
        }
        Object finalLoginId = loginId;
        try {
            LoginIdHolder.set(finalLoginId);
            if (hasAsync) {
                List<EventListenerWrapper> list = ASYNC_EVENT_LISTENER_MAP.get(key);
                EVENT_POOL.submit(() -> list.parallelStream().map(EventListenerWrapper::getListener).forEach(listener -> {
                    if (Objects.isNull(data)) {
                        log.info(LogUtil.format(LOG_NAME, key, "Trigger Async Listener {}"), listener.getClass().getSimpleName());
                    } else {
                        log.info(LogUtil.format(LOG_NAME, key, "Trigger Async Listener {}", Map.of("Data", data)), listener.getClass().getSimpleName());
                    }
                    try {
                        LoginIdHolder.set(finalLoginId);
                        listener.onEvent(data);
                    } catch (Exception e) {
                        log.error(LogUtil.format(LOG_NAME, key, "Error"), e);
                    } finally {
                        LoginIdHolder.remove();
                    }
                }));
            }
            if (hasSync) {
                Iterator<EventListenerWrapper> iterator = SYNC_EVENT_LISTENER_MAP.get(key).iterator();
                boolean flag = true;
                while (flag && iterator.hasNext()) {
                    IEventListener listener = iterator.next().getListener();
                    if (Objects.isNull(data)) {
                        log.info(LogUtil.format(LOG_NAME, key, "Trigger Sync Listener {}"), listener.getClass().getSimpleName());
                    } else {
                        log.info(LogUtil.format(LOG_NAME, key, "Trigger Sync Listener {}", Map.of("Data", data)), listener.getClass().getSimpleName());
                    }
                    try {
                        flag = listener.onEvent(data);
                    } catch (Exception e) {
                        log.error(LogUtil.format(LOG_NAME, key, "Error"), e);
                    }
                }
            }
        } catch (Exception e) {
            log.error(LogUtil.format(LOG_NAME, key, "Error"), e);
        } finally {
            LoginIdHolder.remove();
        }
    }

    /**
     * 触发无参事件
     *
     * @param key key
     */
    public static void trigger(String key) {
        trigger(key, null);
    }

    /**
     * 排序
     *
     * @param listeners 监听器链集合 {@link EventListenerWrapper EventListenerWrapper}
     */
    private static void sort(List<EventListenerWrapper> listeners) {
        listeners.sort(Comparator.comparing(EventListenerWrapper::getOrder));
    }
}
