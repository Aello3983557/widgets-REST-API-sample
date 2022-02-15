package com.aello.service.storage.inMemory;

import com.aello.model.Widget;
import com.aello.model.exception.WidgetNotFoundException;
import com.aello.service.storage.WidgetStorage;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import static com.aello.constants.CommonConstants.Z_INDEX_SHIFT_VALUE;
import static com.aello.constants.ControllerDocumentationConstants.WIDGET_NOT_FOUND_EXCEPTION_MESSAGE;
import static com.aello.service.WidgetUtils.currentDate;
import static com.aello.service.WidgetUtils.generateUUID;
import static java.util.Optional.ofNullable;

@Service
public class WidgetInMemoryStorage implements WidgetStorage {
    private final Lock readLock;
    private final Lock writeLock;
    private final Map<String, Widget> widgetByUUID = new HashMap<>();
    private final NavigableMap<Integer, String> widgetUUIDByZIndex = new TreeMap<>();

    public WidgetInMemoryStorage() {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
    }

    @Override
    public Widget storeWidget(Widget widget) {
        writeLock.lock();
        try {
            if (widget.getZIndex() == null) {
                widget.setZIndex(getMaxZIndex());
            } else {
                shiftWidgetsZIndex(widget.getZIndex());
            }
            if (widget.getUuid() == null) {
                widget.setUuid(generateUUID());
            } else {
                deleteWidgetByUUID(widget.getUuid());
            }
            return save(widget);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void deleteWidgetByUUID(String widgetUUID) {
        writeLock.lock();
        try {
            Optional<Widget> widgetToDelete = getWidgetByUUID(widgetUUID);
            if (widgetToDelete.isEmpty()) {
                throw new WidgetNotFoundException(WIDGET_NOT_FOUND_EXCEPTION_MESSAGE);
            } else {
                Widget widget = widgetToDelete.get();
                widgetUUIDByZIndex.remove(widget.getZIndex());
                widgetByUUID.remove(widget.getUuid());
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Optional<Widget> getWidgetByUUID(String widgetUUID) {
        readLock.lock();
        try {
            return ofNullable(widgetByUUID.get(widgetUUID));
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Widget> getAllWidgets() {
        if (widgetUUIDByZIndex.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        readLock.lock();
        try {
            return widgetUUIDByZIndex.values().stream()
                    .map(widgetByUUID::get)
                    .collect(Collectors.toList());
        } finally {
            readLock.unlock();
        }
    }

    private Integer getMaxZIndex() {
        if (widgetUUIDByZIndex.isEmpty()) {
            return 0;
        }
        return widgetUUIDByZIndex.lastKey() + Z_INDEX_SHIFT_VALUE;
    }

    private void shiftWidgetsZIndex(Integer zIndex) {
        if (widgetUUIDByZIndex.size() == 0 || zIndex > widgetUUIDByZIndex.lastKey()) {
            return;
        }
        String existingWidgetUUID = widgetUUIDByZIndex.get(zIndex);
        if (existingWidgetUUID == null) {
            return;
        }
        findWidgetsToShift(zIndex).forEach(shiftedWidget -> {
            Integer shiftedWidgetZIndex = shiftedWidget.getZIndex();
            widgetUUIDByZIndex.remove(shiftedWidgetZIndex);
            shiftedWidget.setZIndex(shiftedWidgetZIndex + Z_INDEX_SHIFT_VALUE);
            save(shiftedWidget);
        });
    }

    private List<Widget> findWidgetsToShift(Integer zIndex) {
        List<Widget> tailWidgetsSubset = widgetUUIDByZIndex.tailMap(zIndex).values().stream()
                .map(widgetByUUID::get)
                .collect(Collectors.toList());
        List<Widget> widgetsToShift = new ArrayList<>();
        Integer previousZIndex = tailWidgetsSubset.get(0).getZIndex() - 1;
        for (Widget widget : tailWidgetsSubset) {
            if (!widget.getZIndex().equals(previousZIndex + 1)) {
                break;
            }
            previousZIndex = widget.getZIndex();
            widgetsToShift.add(widget);
        }
        return widgetsToShift;
    }

    private Widget save(Widget widget) {
        widget.setDateModified(currentDate());
        widgetByUUID.put(widget.getUuid(), widget);
        widgetUUIDByZIndex.put(widget.getZIndex(), widget.getUuid());
        return widget;
    }
}
