package com.aello.web.controller;

import com.aello.model.Widget;
import com.aello.service.WidgetService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.aello.constants.ControllerDocumentationConstants.*;

@Api(tags = API_TAGS)
@RestController
@RequestMapping(value = WIDGET_MAPPING)
public class WidgetController {
    private final WidgetService widgetService;

    @Autowired
    public WidgetController(WidgetService widgetService) {
        this.widgetService = widgetService;
    }

    @ApiOperation(value = CREATE_WIDGET_DESCRIPTION, notes = CREATE_WIDGET_NOTES)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = WIDGET_CREATED_SUCCESSFULLY),
            @ApiResponse(code = 400, message = WIDGET_CREATED_UNSUCCESSFULLY)})
    @PostMapping
    public ResponseEntity<Widget> createWidget(@ApiParam(name = "widget", value = "Widget to create") @Valid @RequestBody Widget widget) {
        return new ResponseEntity<>(widgetService.createWidget(widget), HttpStatus.CREATED);
    }


}
