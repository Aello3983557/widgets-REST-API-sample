package com.aello.service.storage;

import com.aello.model.Widget;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WidgetStorage {

    Integer getNewWidgetZIndex();

    void shiftWidgets(Integer widgetZIndex);

    Widget storeWidget(Widget widget);

    boolean isWidgetStorageContainWidget(UUID widgetUUID);

    void deleteWidgetByUUID(UUID widgetUUID);

    Optional<Widget> getWidgetById(UUID widgetUUID);

    boolean isStorageFilled();

    List<Widget> getAllWidgets();
}
