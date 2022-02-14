package com.aello.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.aello.constants.CommonConstants.DATE_PATTERN;

public class WidgetUtils {
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String currentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }
    private WidgetUtils() {
    }
}
