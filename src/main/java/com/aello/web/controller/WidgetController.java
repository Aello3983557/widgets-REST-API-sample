package com.aello.web.controller;

import com.aello.model.Widget;
import com.aello.service.WidgetService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<Widget> createWidget(
            @ApiParam(name = WIDGET, value =WIDGET_TO_CREATE)
            @Valid
            @RequestBody Widget widget) {
        return new ResponseEntity<>(widgetService.createWidget(widget), HttpStatus.CREATED);
    }

    @ApiOperation(value = UPDATE_WIDGET_DESCRIPTION, notes = UPDATE_WIDGET_NOTES)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = WIDGET_CREATED_SUCCESSFULLY),
            @ApiResponse(code = 204, message = WIDGET_NOT_FOUND),
            @ApiResponse(code = 400, message = WIDGET_ID_VALIDATION_EXCEPTION),
            @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR)})
    @PutMapping()
    public ResponseEntity<Widget> updateWidget(
            @ApiParam(name = WIDGET, value = WIDGET_TO_UPDATE)
            @Valid
            @RequestBody Widget widget) {
        return new ResponseEntity<>(widgetService.updateWidget(widget), HttpStatus.OK);
    }

    @ApiOperation(value = DELETE_WIDGET_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = WIDGET_CREATED_SUCCESSFULLY),
            @ApiResponse(code = 204, message = WIDGET_NOT_FOUND)})
    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteWidget(
            @ApiParam(name = WIDGET_UUID, value = WIDGET_UUID_DESCRIPTION)
            @RequestParam UUID widgetUUID) {
        widgetService.deleteWidgetByUUID(widgetUUID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = GET_WIDGET_BY_UUID_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = WIDGET_FOUND),
            @ApiResponse(code = 204, message = WIDGET_NOT_FOUND)})
    @GetMapping()
    public ResponseEntity<Widget> getWidget(
            @ApiParam(name = WIDGET_UUID, value = WIDGET_UUID_DESCRIPTION)
            @RequestParam UUID widgetUUID) {
        return new ResponseEntity<>(widgetService.getWidgetByUUID(widgetUUID), HttpStatus.OK);
    }

    @ApiOperation(value = GET_ALL_WIDGET_DESCRIPTION, notes = GET_ALL_WIDGET_NOTES)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = WIDGET_LIST_PROVIDED),
            @ApiResponse(code = 204, message = EMPTY_WIDGET_STORAGE)})
    @GetMapping(value = ALL_WIDGET_MAPPING)
    public ResponseEntity<List<Widget>> getWidgetsList() {
        return new ResponseEntity<>(widgetService.getAllWidgets(), HttpStatus.OK);
    }
}
