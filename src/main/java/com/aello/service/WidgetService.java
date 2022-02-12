package com.aello.service;

import com.aello.model.Widget;
import com.aello.model.exception.EmptyWidgetStorageException;
import com.aello.model.exception.WidgetIdValidationException;
import com.aello.model.exception.WidgetNotFoundException;
import com.aello.service.storage.WidgetStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.aello.constants.ControllerDocumentationConstants.*;

@Service
public class WidgetService {
    private final WidgetStorage widgetStorage;

    @Autowired
    public WidgetService(WidgetStorage widgetStorage) {
        this.widgetStorage = widgetStorage;
    }

    public Widget createWidget(Widget widget) {
        if (widget.getUuid() != null) {
            throw new WidgetIdValidationException(WIDGET_ID_IS_NOT_EMPTY_EXCEPTION_MESSAGE);
        }
        widget.setUuid(UUID.randomUUID());
        widget.setZIndex(processWidgetZIndex(widget.getZIndex()));
        return widgetStorage.storeWidget(widget);
    }

    public Widget updateWidget(Widget widget) {
        validateWidgetId(widget.getUuid());
        widget.setZIndex(processWidgetZIndex(widget.getZIndex()));
        return widget;
    }

    public void deleteWidgetByUUID(UUID widgetUUID) {
        validateWidgetId(widgetUUID);
        widgetStorage.deleteWidgetByUUID(widgetUUID);
    }

    public Widget getWidgetByUUID(UUID widgetUUID) {
        validateWidgetId(widgetUUID);
        return widgetStorage.getWidgetById(widgetUUID).get();
    }

    public List<Widget> getAllWidgets() {
        if (!widgetStorage.isStorageFilled()) {
            throw new EmptyWidgetStorageException(EMPTY_WIDGET_STORAGE_EXCEPTION_MESSAGE);
        }
        return widgetStorage.getAllWidgets();
    }

    private Integer processWidgetZIndex(Integer zIndex) {
        if (zIndex == null) {
            zIndex =widgetStorage.getNewWidgetZIndex();
        } else {
            widgetStorage.shiftWidgets(zIndex);
        }
        return zIndex;
    }

    private void validateWidgetId(UUID widgetUUID) {
        if (widgetUUID == null) {
            throw new WidgetIdValidationException(WIDGET_ID_IS_EMPTY_EXCEPTION_MESSAGE);
        }
        if (!widgetStorage.isWidgetStorageContainWidget(widgetUUID)) {
            throw new WidgetNotFoundException(WIDGET_NOT_FOUND_EXCEPTION_MESSAGE);
        }
    }
}
