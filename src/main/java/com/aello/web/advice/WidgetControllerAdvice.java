package com.aello.web.advice;

import com.aello.model.exception.EmptyWidgetStorageException;
import com.aello.model.exception.WidgetIdValidationException;
import com.aello.model.exception.WidgetNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class WidgetControllerAdvice {
    @ExceptionHandler(WidgetIdValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object widgetIdValidationExceptionHandler(WidgetIdValidationException e) {
        return new HttpEntity<>(e.getMessage());
    }

    @ExceptionHandler(WidgetNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object widgetNotFoundExceptionExceptionHandler(WidgetNotFoundException e) {
        return new HttpEntity<>(e.getMessage());
    }

    @ExceptionHandler(EmptyWidgetStorageException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object emptyWidgetStorageExceptionHandler(EmptyWidgetStorageException e) {
        return new HttpEntity<>(e.getMessage());
    }
}
