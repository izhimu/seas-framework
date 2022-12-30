package com.izhimu.seas.core.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * LocalDateTime类型转换
 *
 * @author haoran
 * @version v1.0
 */
@Component
public class DateConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(@NonNull String source) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(source)), ZoneId.systemDefault());
    }
}
