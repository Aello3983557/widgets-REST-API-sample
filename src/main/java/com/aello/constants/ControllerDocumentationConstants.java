package com.aello.constants;

public interface ControllerDocumentationConstants {
    String API_TAGS = "A web service to work with widgets via HTTP REST API. " ;
    String WIDGET_MAPPING = "/widgets";

    String CREATE_WIDGET_DESCRIPTION = "Creating a widget";
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
    String WIDGET_CREATED_SUCCESSFULLY = "Widget created successfully";
    String WIDGET_CREATED_UNSUCCESSFULLY = "Widget created unsuccessfully";
}
