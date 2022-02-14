package com.aello.service;

import com.aello.model.Widget;
import com.aello.model.exception.WidgetIdValidationException;
import com.aello.service.storage.WidgetStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return widgetStorage.storeWidget(widget);
    }

    public Widget updateWidget(String widgetUUID, Widget widget) {
        validateWidgetId(widget.getUuid());
        if (!widgetUUID.equals(widget.getUuid())) {
            throw new WidgetIdValidationException(WIDGET_ID_MISSMATCH_EXCEPTION_MESSAGE);
        }
        return widgetStorage.storeWidget(widget);
    }

    public void deleteWidgetByUUID(String widgetUUID) {
        validateWidgetId(widgetUUID);
        widgetStorage.deleteWidgetByUUID(widgetUUID);
    }

    public Widget getWidgetByUUID(String widgetUUID) {
        validateWidgetId(widgetUUID);
        return widgetStorage.getWidgetByUUID(widgetUUID).get();
    }

    public List<Widget> getAllWidgets() {
//        if (!widgetStorage.isStorageFilled()) {
//            throw new EmptyWidgetStorageException(EMPTY_WIDGET_STORAGE_EXCEPTION_MESSAGE);
//        }
        return widgetStorage.getAllWidgets();
    }

    private void validateWidgetId(String widgetUUID) {
        if (widgetUUID == null) {
            throw new WidgetIdValidationException(WIDGET_ID_IS_EMPTY_EXCEPTION_MESSAGE);
        }
    }
}
