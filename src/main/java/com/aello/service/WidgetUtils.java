package com.aello.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.aello.constants.CommonConstants.DATE_PATTERN;

public class WidgetUtils {
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String currentDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }
    private WidgetUtils() {
    }
}
