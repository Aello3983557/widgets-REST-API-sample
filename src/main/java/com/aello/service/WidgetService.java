package com.aello.service;

import com.aello.model.Widget;
import com.aello.model.exception.EmptyWidgetStorageException;
import com.aello.model.exception.WidgetIdValidationException;
import com.aello.model.exception.WidgetNotFoundException;
import com.aello.service.storage.WidgetStorage;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Widget> optionalWidget = widgetStorage.getWidgetByUUID(widgetUUID);
        if (optionalWidget.isEmpty()) {
            throw new WidgetNotFoundException(WIDGET_NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return optionalWidget.get();
    }

    public List<Widget> getAllWidgets() {
        List<Widget> storedWidgets = widgetStorage.getAllWidgets();
        if (storedWidgets.isEmpty()) {
            throw new EmptyWidgetStorageException(EMPTY_WIDGET_STORAGE_EXCEPTION_MESSAGE);
        }
        return storedWidgets;
    }

    private void validateWidgetId(String widgetUUID) {
        if (Strings.isBlank(widgetUUID)) {
            throw new WidgetIdValidationException(WIDGET_ID_IS_EMPTY_EXCEPTION_MESSAGE);
        }
    }
}
