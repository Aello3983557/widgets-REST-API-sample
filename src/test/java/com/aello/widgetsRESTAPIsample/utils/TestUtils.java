package com.aello.widgetsRESTAPIsample.utils;

import com.aello.model.Widget;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

import static com.aello.constants.CommonConstants.DATE_PATTERN;

public class TestUtils {
    public static Widget createWidgetForTest() {
        return createWidgetForTest(getRandomInt(), getRandomInt(), getRandomInt(), getRandomInt(), getRandomInt(), LocalDateTime.now());
    }

    public static Widget createWidgetForTest(int zIndex, int x, int y, int width, int height) {
        return createWidgetForTest(zIndex, x, y, width, height, LocalDateTime.now());
    }

    private static int getRandomInt() {
        Random r = new Random();
        return r.nextInt((500) + 1);
    }

    public static Widget createWidgetForTest(int zIndex, int x, int y, int width, int height, LocalDateTime dateTime) {
        Widget widget = Widget.builder()
                .zIndex(zIndex)
                .x(x)
                .y(y)
                .width(width)
                .height(height)
                .build();
        widget.setUuid(UUID.randomUUID().toString());
        widget.setDateModified(formatDate(dateTime));
        return widget;
    }

    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }
    private TestUtils() {
    }
}
