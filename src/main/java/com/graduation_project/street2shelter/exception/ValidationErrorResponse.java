package com.graduation_project.street2shelter.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {

    private List<ValidationErrorDetail> errors = new ArrayList<>();

    public void addError(ValidationErrorDetail errorDetail) {
        errors.add(errorDetail);
    }

    public List<ValidationErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationErrorDetail> errors) {
        this.errors = errors;
    }
}