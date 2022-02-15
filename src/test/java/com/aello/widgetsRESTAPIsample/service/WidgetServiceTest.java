package com.aello.widgetsRESTAPIsample.service;

import com.aello.model.Widget;
import com.aello.model.exception.EmptyWidgetStorageException;
import com.aello.model.exception.WidgetIdValidationException;
import com.aello.model.exception.WidgetNotFoundException;
import com.aello.service.WidgetService;
import com.aello.service.storage.WidgetStorage;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.aello.constants.ControllerDocumentationConstants.*;
import static com.aello.service.WidgetUtils.generateUUID;
import static com.aello.widgetsRESTAPIsample.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class WidgetServiceTest {

    @Mock
    private WidgetStorage storage;

    private WidgetService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new WidgetService(storage);
    }

    @Test
    public void createWidgetWithAllProperties() {
        Widget widget = createNewWidgetForTest();
        assertThatNoException().isThrownBy(() -> service.createWidget(widget));
    }

    @Test
    void createWidgetWithUUID() {
        assertThatThrownBy(() -> service.createWidget(createWidgetForTest()))
                .isInstanceOf(WidgetIdValidationException.class)
                .hasMessage(WIDGET_ID_IS_NOT_EMPTY_EXCEPTION_MESSAGE);
    }

    @Test
    public void testUpdateWidget() {
        Widget updatedWidget = createWidgetForTest();
        assertThatNoException().isThrownBy(() -> service.updateWidget(updatedWidget.getUuid(), updatedWidget));
    }

    @Test
    public void testUpdateWidgetEmptyId() {
        Widget widgetToUpdate = createNewWidgetForTest();
        assertThatThrownBy(() -> service.updateWidget(Strings.EMPTY, widgetToUpdate))
                .isInstanceOf(WidgetIdValidationException.class)
                .hasMessage(WIDGET_ID_IS_EMPTY_EXCEPTION_MESSAGE);
    }

    @Test
    public void testUpdateWidgetMismatchIds() {
        assertThatThrownBy(() -> service.updateWidget(generateUUID(), createWidgetForTest()))
                .isInstanceOf(WidgetIdValidationException.class)
                .hasMessage(WIDGET_ID_MISMATCH_EXCEPTION_MESSAGE);
    }

    @Test
    public void testDeleteWidget() {
        assertThatNoException().isThrownBy(() -> service.deleteWidgetByUUID(generateUUID()));
    }

    @Test
    public void testDeleteWidgetEmptyId() {
        assertThatThrownBy(() -> service.deleteWidgetByUUID(Strings.EMPTY))
                .isInstanceOf(WidgetIdValidationException.class)
                .hasMessage(WIDGET_ID_IS_EMPTY_EXCEPTION_MESSAGE);
    }

    @Test
    public void testGetWidget() {
        when(storage.getWidgetByUUID(Mockito.any())).thenReturn(Optional.of(createWidgetForTest()));
        assertThatNoException().isThrownBy(() -> service.getWidgetByUUID(generateUUID()));
    }

    @Test
    public void testGetWidgetNotFound() {
        assertThatThrownBy(() -> service.getWidgetByUUID(generateUUID()))
                .isInstanceOf(WidgetNotFoundException.class)
                .hasMessage(WIDGET_NOT_FOUND_EXCEPTION_MESSAGE);
    }


    @Test
    public void testGetWidgets() {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(createWidgetForTest());
        when(storage.getAllWidgets()).thenReturn(widgets);
        assertThatNoException().isThrownBy(() -> service.getAllWidgets(getDefaultPageable()));
    }

    @Test
    public void testGetWidgetsNotFound() {
        assertThatThrownBy(() -> service.getAllWidgets(getDefaultPageable()))
                .isInstanceOf(EmptyWidgetStorageException.class)
                .hasMessage(EMPTY_WIDGET_STORAGE_EXCEPTION_MESSAGE);
    }

}
