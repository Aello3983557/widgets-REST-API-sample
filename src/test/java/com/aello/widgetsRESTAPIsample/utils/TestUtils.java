package com.aello.widgetsRESTAPIsample.utils;

import com.aello.model.Widget;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static com.aello.constants.CommonConstants.DATE_PATTERN;
import static com.aello.constants.ControllerDocumentationConstants.DEFAULT_PAGE_SIZE;
import static com.aello.constants.ControllerDocumentationConstants.DEFAULT_START_PAGE;
import static com.aello.constants.WidgetConstants.Z_INDEX_PROP;
import static com.aello.service.WidgetUtils.generateUUID;

public class TestUtils {
    public static Widget createNewWidgetForTest() {
        return createWidgetForTest(getRandomInt(), getRandomInt(), getRandomInt(), getRandomInt(), getRandomInt(), LocalDateTime.now(), null);
    }

    public static Widget createWidgetForTest() {
        return createWidgetForTest(getRandomInt(), getRandomInt(), getRandomInt(), getRandomInt(), getRandomInt(), LocalDateTime.now(), generateUUID());
    }

    public static Widget createWidgetWithZIndexForTest(Integer zIndex) {
        return createWidgetForTest(zIndex, getRandomInt(), getRandomInt(), getRandomInt(), getRandomInt(), LocalDateTime.now(), null);
    }


    public static Widget createWidgetForTest(int zIndex, int x, int y, int width, int height) {
        return createWidgetForTest(zIndex, x, y, width, height, LocalDateTime.now(), generateUUID());
    }

    private static int getRandomInt() {
        Random r = new Random();
        return r.nextInt((500) + 1);
    }

    public static Widget createWidgetForTest(Integer zIndex, int x, int y, int width, int height, LocalDateTime dateTime, String widgetUUID) {
        Widget widget = Widget.builder()
                .zIndex(zIndex)
                .x(x)
                .y(y)
                .width(width)
                .height(height)
                .build();
        widget.setUuid(widgetUUID);
        widget.setDateModified(formatDate(dateTime));
        return widget;
    }

    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    public static Pageable getDefaultPageable() {
        return PageRequest.of(DEFAULT_START_PAGE, DEFAULT_PAGE_SIZE, Sort.by(Sort.Direction.ASC, Z_INDEX_PROP));
    }

    private TestUtils() {
    }
}
