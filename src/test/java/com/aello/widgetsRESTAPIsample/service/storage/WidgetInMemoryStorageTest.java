package com.aello.widgetsRESTAPIsample.service.storage;

import com.aello.model.Widget;
import com.aello.service.storage.inMemory.WidgetInMemoryStorage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.aello.constants.CommonConstants.Z_INDEX_SHIFT_VALUE;
import static com.aello.service.WidgetUtils.generateUUID;
import static com.aello.widgetsRESTAPIsample.utils.TestUtils.createNewWidgetForTest;
import static com.aello.widgetsRESTAPIsample.utils.TestUtils.createWidgetWithZIndexForTest;
import static org.assertj.core.api.Assertions.assertThat;

public class WidgetInMemoryStorageTest {
    private final WidgetInMemoryStorage storage = new WidgetInMemoryStorage();

    @Test
    public void testStoreNewWidget() {
        Widget newWidget = createNewWidgetForTest();
        newWidget = storage.storeWidget(newWidget);
        Widget storedWidget = storage.getWidgetByUUID(newWidget.getUuid()).orElse(null);

        assertThat(newWidget).isNotNull();
        assertThat(newWidget.getUuid()).isNotNull();
        assertThat(newWidget.getUuid()).isNotNull();
        assertThat(storedWidget).isNotNull();
        assertThat(newWidget.getX()).isEqualTo(storedWidget.getX());
        assertThat(newWidget.getY()).isEqualTo(storedWidget.getY());
        assertThat(newWidget.getZIndex()).isEqualTo(storedWidget.getZIndex());
        assertThat(newWidget.getWidth()).isEqualTo(storedWidget.getWidth());
        assertThat(newWidget.getHeight()).isEqualTo(storedWidget.getHeight());
    }

    @Test
    public void testStoreNewWidgetWithoutZIndexEmptyStorage() {
        Widget newWidget = createWidgetWithZIndexForTest(null);
        newWidget = storage.storeWidget(newWidget);

        assertThat(newWidget).isNotNull();
        assertThat(newWidget.getZIndex()).isEqualTo(0);
    }

    @Test
    public void testStoreNewWidgetWithoutZIndexFilledStorage() {
        Widget existWidget = createNewWidgetForTest();
        existWidget = storage.storeWidget(existWidget);
        Widget newWidget = createWidgetWithZIndexForTest(null);
        newWidget = storage.storeWidget(newWidget);

        assertThat(newWidget).isNotNull();
        assertThat(newWidget.getZIndex()).isEqualTo(existWidget.getZIndex() + Z_INDEX_SHIFT_VALUE);
    }

    @Test
    public void testStoreNewWidgetAndShiftExistWidget() {
        Widget existWidget = createNewWidgetForTest();
        int existWidgetZIndex = existWidget.getZIndex();
        existWidget = storage.storeWidget(existWidget);
        Widget newWidget = createWidgetWithZIndexForTest(existWidgetZIndex);
        newWidget = storage.storeWidget(newWidget);

        assertThat(newWidget).isNotNull();
        assertThat(newWidget.getZIndex()).isEqualTo(existWidgetZIndex);

        existWidget = storage.getWidgetByUUID(existWidget.getUuid()).orElse(null);
        assertThat(existWidget).isNotNull();
        assertThat(existWidget.getZIndex()).isEqualTo(existWidgetZIndex + Z_INDEX_SHIFT_VALUE);
    }

    @Test
    public void testStoreNewWidgetAndShiftExistWidgets() {
        Widget firstExistWidget = createNewWidgetForTest();
        int existWidgetZIndex = firstExistWidget.getZIndex();
        firstExistWidget = storage.storeWidget(firstExistWidget);
        Widget secondExistWidget = createWidgetWithZIndexForTest(existWidgetZIndex + Z_INDEX_SHIFT_VALUE);
        secondExistWidget = storage.storeWidget(secondExistWidget);
        Widget newWidget = createWidgetWithZIndexForTest(existWidgetZIndex);
        newWidget = storage.storeWidget(newWidget);

        assertThat(newWidget).isNotNull();
        assertThat(newWidget.getZIndex()).isEqualTo(existWidgetZIndex);

        firstExistWidget = storage.getWidgetByUUID(firstExistWidget.getUuid()).orElse(null);
        assertThat(firstExistWidget).isNotNull();
        assertThat(firstExistWidget.getZIndex()).isEqualTo(existWidgetZIndex + Z_INDEX_SHIFT_VALUE);

        secondExistWidget = storage.getWidgetByUUID(secondExistWidget.getUuid()).orElse(null);
        assertThat(secondExistWidget).isNotNull();
        assertThat(secondExistWidget.getZIndex()).isEqualTo(existWidgetZIndex + Z_INDEX_SHIFT_VALUE + Z_INDEX_SHIFT_VALUE);
    }

    @Test
    public void testStoreNewWidgetAndDontShiftExistWidget() {
        Widget existWidget = createNewWidgetForTest();
        int existWidgetZIndex = existWidget.getZIndex();
        existWidget = storage.storeWidget(existWidget);
        Widget newWidget = createWidgetWithZIndexForTest(existWidgetZIndex - Z_INDEX_SHIFT_VALUE);
        newWidget = storage.storeWidget(newWidget);

        assertThat(newWidget).isNotNull();
        assertThat(newWidget.getZIndex()).isEqualTo(existWidgetZIndex - Z_INDEX_SHIFT_VALUE);

        existWidget = storage.getWidgetByUUID(existWidget.getUuid()).orElse(null);
        assertThat(existWidget).isNotNull();
        assertThat(existWidget.getZIndex()).isEqualTo(existWidgetZIndex);
    }

    @Test
    public void testGetWidget() {
        Widget existWidget = createNewWidgetForTest();
        int existWidgetZIndex = existWidget.getZIndex();
        existWidget = storage.storeWidget(existWidget);

        existWidget = storage.getWidgetByUUID(existWidget.getUuid()).orElse(null);
        assertThat(existWidget).isNotNull();
        assertThat(existWidget.getZIndex()).isEqualTo(existWidgetZIndex);
    }

    @Test
    public void testGetWidgetNonExistUUID() {
        Widget nonExist = storage.getWidgetByUUID(generateUUID()).orElse(null);
        assertThat(nonExist).isNull();
    }

    @Test
    public void testGetWidgetsEmptyStorage() {
        List<Widget> nonExist = storage.getAllWidgets();
        assertThat(nonExist).isEmpty();
    }

    @Test
    public void testGetWidgetsFilledStorage() {
        Widget newWidget = createNewWidgetForTest();
        newWidget = storage.storeWidget(newWidget);
        List<Widget> widgets = storage.getAllWidgets();

        assertThat(widgets).isNotEmpty();
        assertThat(newWidget).isNotNull();
        assertThat(widgets).contains(newWidget);

        Widget nextWidget = createNewWidgetForTest();
        nextWidget = storage.storeWidget(nextWidget);
        widgets = storage.getAllWidgets();

        assertThat(widgets).isNotEmpty();
        assertThat(nextWidget).isNotNull();
        assertThat(widgets).contains(newWidget);
        assertThat(widgets).contains(nextWidget);
    }

    /*
        Examples:
            1) Given - 1,2,3; New -  2; Result - 1,2,3,4; Explanation: 2 and 3 has been shifted;
            2) Given - 1,5,6; New -  2; Result - 1,2,5,6; Explanation: No one shifted;
            3) Given - 1,2,4; New -  2; Result - 1,2,3,4; Explanation: Only 2 has been shifted;

     */
    @Test
    public void testFirstExample() {
        Widget firstWidget = createWidgetWithZIndexForTest(1);
        firstWidget = storage.storeWidget(firstWidget);
        Widget secondWidget = createWidgetWithZIndexForTest(2);
        secondWidget = storage.storeWidget(secondWidget);
        Widget thirdWidget = createWidgetWithZIndexForTest(3);
        thirdWidget = storage.storeWidget(thirdWidget);

        Widget newWidget = createWidgetWithZIndexForTest(2);
        newWidget = storage.storeWidget(newWidget);

        firstWidget = storage.getWidgetByUUID(firstWidget.getUuid()).orElse(null);
        assertThat(firstWidget).isNotNull();
        assertThat(firstWidget.getZIndex()).isEqualTo(1);

        secondWidget = storage.getWidgetByUUID(secondWidget.getUuid()).orElse(null);
        assertThat(secondWidget).isNotNull();
        assertThat(secondWidget.getZIndex()).isEqualTo(3);

        thirdWidget = storage.getWidgetByUUID(thirdWidget.getUuid()).orElse(null);
        assertThat(thirdWidget).isNotNull();
        assertThat(thirdWidget.getZIndex()).isEqualTo(4);

        newWidget = storage.getWidgetByUUID(newWidget.getUuid()).orElse(null);
        assertThat(newWidget).isNotNull();
        assertThat(newWidget.getZIndex()).isEqualTo(2);
    }

    @Test
    public void testSecondExample() {
        Widget firstWidget = createWidgetWithZIndexForTest(1);
        firstWidget = storage.storeWidget(firstWidget);
        Widget secondWidget = createWidgetWithZIndexForTest(2);
        secondWidget = storage.storeWidget(secondWidget);
        Widget thirdWidget = createWidgetWithZIndexForTest(4);
        thirdWidget = storage.storeWidget(thirdWidget);

        Widget newWidget = createWidgetWithZIndexForTest(2);
        newWidget = storage.storeWidget(newWidget);

        firstWidget = storage.getWidgetByUUID(firstWidget.getUuid()).orElse(null);
        assertThat(firstWidget).isNotNull();
        assertThat(firstWidget.getZIndex()).isEqualTo(1);

        secondWidget = storage.getWidgetByUUID(secondWidget.getUuid()).orElse(null);
        assertThat(secondWidget).isNotNull();
        assertThat(secondWidget.getZIndex()).isEqualTo(3);

        thirdWidget = storage.getWidgetByUUID(thirdWidget.getUuid()).orElse(null);
        assertThat(thirdWidget).isNotNull();
        assertThat(thirdWidget.getZIndex()).isEqualTo(4);

        newWidget = storage.getWidgetByUUID(newWidget.getUuid()).orElse(null);
        assertThat(newWidget).isNotNull();
        assertThat(newWidget.getZIndex()).isEqualTo(2);
    }

    @Test
    public void testThirdExample() {
        Widget firstWidget = createWidgetWithZIndexForTest(1);
        firstWidget = storage.storeWidget(firstWidget);
        Widget secondWidget = createWidgetWithZIndexForTest(5);
        secondWidget = storage.storeWidget(secondWidget);
        Widget thirdWidget = createWidgetWithZIndexForTest(6);
        thirdWidget = storage.storeWidget(thirdWidget);

        Widget newWidget = createWidgetWithZIndexForTest(2);
        newWidget = storage.storeWidget(newWidget);

        firstWidget = storage.getWidgetByUUID(firstWidget.getUuid()).orElse(null);
        assertThat(firstWidget).isNotNull();
        assertThat(firstWidget.getZIndex()).isEqualTo(1);

        secondWidget = storage.getWidgetByUUID(secondWidget.getUuid()).orElse(null);
        assertThat(secondWidget).isNotNull();
        assertThat(secondWidget.getZIndex()).isEqualTo(5);

        thirdWidget = storage.getWidgetByUUID(thirdWidget.getUuid()).orElse(null);
        assertThat(thirdWidget).isNotNull();
        assertThat(thirdWidget.getZIndex()).isEqualTo(6);

        newWidget = storage.getWidgetByUUID(newWidget.getUuid()).orElse(null);
        assertThat(newWidget).isNotNull();
        assertThat(newWidget.getZIndex()).isEqualTo(2);
    }
}
