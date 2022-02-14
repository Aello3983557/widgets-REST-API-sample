package com.aello.service.storage;

import com.aello.model.Widget;

import java.util.List;
import java.util.Optional;

public interface WidgetStorage {
    Widget storeWidget(Widget widget);

    void deleteWidgetByUUID(String widgetUUID);

    Optional<Widget> getWidgetByUUID(String widgetUUID);

    List<Widget> getAllWidgets();
}
