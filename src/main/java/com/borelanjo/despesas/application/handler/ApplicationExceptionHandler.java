package com.borelanjo.despesas.application.handler;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> validateError(ConstraintViolationException ex){
        return ResponseEntity.badRequest().body(ex.getConstraintViolations().stream().map(cv -> cv.getMessage()).collect(Collectors.toList()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> otherErrors(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
