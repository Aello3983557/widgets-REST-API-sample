package com.aello.constants;

public interface ControllerDocumentationConstants {
    String API_TAGS = "A web service to work with widgets via HTTP REST API. ";
    String WIDGET_MAPPING = "/widgets";

    int DEFAULT_START_PAGE = 0;
    int DEFAULT_PAGE_SIZE = 10;

    String CREATE_WIDGET_DESCRIPTION = "Creating a widget";
    String UPDATE_WIDGET_DESCRIPTION = "Changing widget data by UUID";
    String DELETE_WIDGET_DESCRIPTION = "Delete stored widget data by UUID";
    String GET_WIDGET_DESCRIPTION = "Getting a widget by UUID";
    String GET_ALL_WIDGET_DESCRIPTION = "Getting a list of widgets";

    String CREATE_WIDGET_NOTES =
            "Creating a widget. Having a set of coordinates, Z-index, width, and height, we get a\n" +
                    "complete widget description in the response. The server generates the identifier. If a\n" +
                    "Z-index is not specified, the widget moves to the foreground (becomes maximum). If the\n" +
                    "existing Z-index is specified, then the new widget shifts widget with the same (and\n" +
                    "greater if needed) upwards.\n" +
                    "Examples:\n" +
                    "1) Given - 1,2,3; New - \u200B 2\u200B; Result - 1,\u200B2\u200B,3,4; Explanation: 2 and 3 has been shifted;\n" +
                    "2) Given - 1,5,6; New - \u200B 2\u200B; Result - 1,\u200B2\u200B,5,6; Explanation: No one shifted;\n" +
                    "3) Given - 1,2,4; New - \u200B 2\u200B; Result - 1,\u200B2\u200B,3,4; Explanation: Only 2 has been shifted;";
    String UPDATE_WIDGET_NOTES =
            "Changing widget data by Id. In response, we get an updated full description of the\n" +
                    "widget. We cannot change the widget id. All changes to widgets must occur atomically.\n" +
                    "That is, if we change the \u200B XY\u200B coordinates of the widget, then we should not get an\n" +
                    "intermediate state during concurrent reading. The rules related to the Z-index are the\n" +
                    "same as when creating a widget.";
    String GET_ALL_WIDGET_NOTES = "Getting a list of all widgets sorted by Z-index, from smallest to largest";

    String WIDGET_CREATED_SUCCESSFULLY = "Widget created successfully";
    String WIDGET_UPDATED_SUCCESSFULLY = "Widget updated successfully";
    String WIDGET_DELETED_SUCCESSFULLY = "Widget deleted successfully";
    String WIDGET_FOUND = "Widget found";
    String WIDGET_LIST_PROVIDED = "Widget list provided";

    String WIDGET_ID_IS_NOT_EMPTY_EXCEPTION_MESSAGE = "Widget UUID should be generated by server";
    String WIDGET_ID_VALIDATION_EXCEPTION_MESSAGE = "Widget UUID validation exceptions";
    String WIDGET_ID_IS_EMPTY_EXCEPTION_MESSAGE = "Widget UUID should be provided";
    String WIDGET_ID_MISMATCH_EXCEPTION_MESSAGE = "Widget UUID path variable and request body should be equal";
    String WIDGET_NOT_FOUND_EXCEPTION_MESSAGE = "Widget with provided id not found";
    String EMPTY_WIDGET_STORAGE_EXCEPTION_MESSAGE = "Empty widget storage";

    String WIDGET = "Widget";
    String WIDGET_UUID = "widgetUUID";
    String WIDGET_UUID_MAPPING = "/{widgetUUID}";
    String WIDGET_UUID_DESCRIPTION = "Unique widget identifier";
    String WIDGET_TO_CREATE = "Widget to create";
    String WIDGET_TO_UPDATE = "Widget to update";
}
