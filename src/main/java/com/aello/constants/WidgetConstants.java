package com.aello.constants;

public interface WidgetConstants {
    String WIDGET_DESCRIPTION = "A Widget is an object on a plane in a \u200B Cartesian coordinate system that has coordinates (X, Y),\n" +
            "Z-index, width, height, last modification date, and a \u200B unique identifier\u200B. X, Y, and Z-index are\n" +
            "integers (may be negative). Width and height are integers > 0.\n" +
            "Widget attributes should be not null";
    String Z_INDEX_DESCRIPTION = "A Z-index\u200B is a unique sequence common to all widgets that\n" +
            "determines the order of widgets (regardless of their coordinates).\n" +
            "Gaps are allowed.\u200B The higher the value, the higher the widget\n" +
            "lies on the plane.";
    String Z_INDEX_PROP = "zIndex";

    String UUID_EXAMPLE = "22a6dd81-103a-4d3a-8e9b-0ba4b527f5f6";

    String INTEGER_EXAMPLE = "23";


}
