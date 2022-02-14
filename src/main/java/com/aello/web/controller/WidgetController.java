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

import static com.aello.constants.ControllerDocumentationConstants.*;

@Api(tags = API_TAGS, value = "1")
@RestController
@RequestMapping(value = WIDGET_MAPPING)
public class WidgetController {
    private final WidgetService widgetService;

    @Autowired
    public WidgetController(WidgetService widgetService) {
        this.widgetService = widgetService;
    }

    @ApiOperation(value = CREATE_WIDGET_DESCRIPTION, notes = CREATE_WIDGET_NOTES, response = Widget.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = WIDGET_CREATED_SUCCESSFULLY),
            @ApiResponse(code = 400, message = WIDGET_ID_IS_NOT_EMPTY_EXCEPTION_MESSAGE)})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Widget> createWidget(
            @ApiParam(name = WIDGET, value = WIDGET_TO_CREATE)
            @RequestBody @Valid Widget widget) {
        return new ResponseEntity<>(widgetService.createWidget(widget), HttpStatus.CREATED);
    }

    @ApiOperation(value = UPDATE_WIDGET_DESCRIPTION, notes = UPDATE_WIDGET_NOTES, response = Widget.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = WIDGET_UPDATED_SUCCESSFULLY),
            @ApiResponse(code = 400, message = WIDGET_ID_MISMATCH_EXCEPTION_MESSAGE),
            @ApiResponse(code = 404, message = WIDGET_NOT_FOUND_EXCEPTION_MESSAGE)})
    @PatchMapping(WIDGET_UUID_MAPPING)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Widget> updateWidget(
            @ApiParam(name = WIDGET, value = WIDGET_TO_UPDATE)
            @PathVariable(WIDGET_UUID) String widgetUUID,
            @RequestBody @Valid Widget widget) {
        return new ResponseEntity<>(widgetService.updateWidget(widgetUUID, widget), HttpStatus.OK);
    }

    @ApiOperation(value = DELETE_WIDGET_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = WIDGET_DELETED_SUCCESSFULLY),
            @ApiResponse(code = 404, message = WIDGET_NOT_FOUND_EXCEPTION_MESSAGE)})
    @DeleteMapping(WIDGET_UUID_MAPPING)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> deleteWidget(
            @ApiParam(name = WIDGET_UUID, value = WIDGET_UUID_DESCRIPTION)
            @PathVariable(WIDGET_UUID) String widgetUUID) {
        widgetService.deleteWidgetByUUID(widgetUUID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = GET_WIDGET_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = WIDGET_FOUND),
            @ApiResponse(code = 404, message = WIDGET_NOT_FOUND_EXCEPTION_MESSAGE)})
    @GetMapping(WIDGET_UUID_MAPPING)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Widget> getWidget(
            @ApiParam(name = WIDGET_UUID, value = WIDGET_UUID_DESCRIPTION)
            @PathVariable(WIDGET_UUID) String widgetUUID) {
        return new ResponseEntity<>(widgetService.getWidgetByUUID(widgetUUID), HttpStatus.OK);
    }

    @ApiOperation(value = GET_ALL_WIDGET_DESCRIPTION, notes = GET_ALL_WIDGET_NOTES)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = WIDGET_LIST_PROVIDED),
            @ApiResponse(code = 404, message = EMPTY_WIDGET_STORAGE_EXCEPTION_MESSAGE)})
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Widget>> getWidgetsList() {
        return new ResponseEntity<>(widgetService.getAllWidgets(), HttpStatus.OK);
    }
}
