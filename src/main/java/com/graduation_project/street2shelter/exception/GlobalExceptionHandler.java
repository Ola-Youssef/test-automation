package com.graduation_project.street2shelter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();

        bindingResult.getFieldErrors().forEach(error -> {
            ValidationErrorDetail errorDetail = new ValidationErrorDetail(
                    error.getField(),
                    error.getDefaultMessage(),
                    error.getRejectedValue()
            );
            errorResponse.addError(errorDetail);
        });

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}