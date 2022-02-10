package com.aello.service;

import com.aello.model.Widget;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class WidgetService {
    public Widget createWidget(Widget widget) {
        return widget;
    }

    public Widget updateWidget(Widget widget) {
        return widget;
    }

    public void deleteWidgetByUUID(UUID widgetUUID) {
    }

    public Widget getWidgetByUUID(UUID widgetUUID) {
        return null;
    }

    public List<Widget> getAllWidgets() {
        return null;
    }
}
