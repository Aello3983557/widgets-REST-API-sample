package com.aello.service;

import com.aello.model.Widget;
import com.aello.model.exception.EmptyWidgetStorageException;
import com.aello.model.exception.WidgetIdValidationException;
import com.aello.model.exception.WidgetNotFoundException;
import com.aello.service.storage.WidgetStorage;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.aello.constants.ControllerDocumentationConstants.*;

@Service
@Transactional(readOnly = true)
public class WidgetService {
    private final WidgetStorage widgetStorage;

    @Autowired
    public WidgetService(WidgetStorage widgetStorage) {
        this.widgetStorage = widgetStorage;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Widget createWidget(Widget widget) {
        if (widget.getUuid() != null) {
            throw new WidgetIdValidationException(WIDGET_ID_IS_NOT_EMPTY_EXCEPTION_MESSAGE);
        }
        return widgetStorage.storeWidget(widget);
    }

    public Widget updateWidget(String widgetUUID, Widget widget) {
        validateWidgetId(widget.getUuid());
        if (!widgetUUID.equals(widget.getUuid())) {
            throw new WidgetIdValidationException(WIDGET_ID_MISMATCH_EXCEPTION_MESSAGE);
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

    public Page<Widget> getAllWidgets(Pageable pageable) {
        List<Widget> storedWidgets = widgetStorage.getAllWidgets();
        if (storedWidgets.isEmpty()) {
            throw new EmptyWidgetStorageException(EMPTY_WIDGET_STORAGE_EXCEPTION_MESSAGE);
        }
        return new PageImpl<>(storedWidgets.stream()
                .skip((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(storedWidgets.size())
                .sorted(Comparator.comparingInt(Widget::getZIndex))
                .collect(Collectors.toCollection(ArrayList::new)), pageable, storedWidgets.size());
    }

    private void validateWidgetId(String widgetUUID) {
        if (Strings.isBlank(widgetUUID)) {
            throw new WidgetIdValidationException(WIDGET_ID_IS_EMPTY_EXCEPTION_MESSAGE);
        }
    }
}
