package com.aello.service.storage.inMemory;

import com.aello.model.Widget;
import com.aello.service.storage.WidgetStorage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WidgetInMemoryStorage implements WidgetStorage {
    @Override
    public Integer getNewWidgetZIndex() {
        return null;
    }

    @Override
    public void shiftWidgets(Integer widgetZIndex) {

    }

    @Override
    public Widget storeWidget(Widget widget) {
        return widget;
    }

    @Override
    public boolean isWidgetStorageContainWidget(UUID widgetUUID) {
        return false;
    }

    @Override
    public void deleteWidgetByUUID(UUID widgetUUID) {

    }

    @Override
    public Optional<Widget> getWidgetById(UUID widgetUUID) {
        return Optional.empty();
    }

    @Override
    public boolean isStorageFilled() {
        return false;
    }

    @Override
    public List<Widget> getAllWidgets() {
        return null;
    }
}
