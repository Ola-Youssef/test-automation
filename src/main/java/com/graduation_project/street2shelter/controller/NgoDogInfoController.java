package com.graduation_project.street2shelter.controller;

import com.graduation_project.street2shelter.entity.NgoDogInfo;
import com.graduation_project.street2shelter.entity.Ngos;
import com.graduation_project.street2shelter.entity.Requests;
import com.graduation_project.street2shelter.entity.Users;
import com.graduation_project.street2shelter.exception.ValidationErrorDetail;
import com.graduation_project.street2shelter.exception.ValidationErrorResponse;
import com.graduation_project.street2shelter.service.NgoAssignmentsService;
import com.graduation_project.street2shelter.service.NgoDogInfoService;
import com.graduation_project.street2shelter.service.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doginfo")
@Validated
public class NgoDogInfoController {
    @Autowired
    private NgoDogInfoService ngoDogInfoService;

    @GetMapping("/findalldogsinfo")
    @ResponseBody
    public List<NgoDogInfo> findAll() {
        return ngoDogInfoService.findAllNgoDogInfo();
    }

    @GetMapping("/findbyid")
    @ResponseBody
    public ResponseEntity<?> findByRequestUpdateId(@RequestParam int requestUpdatesId) {
        NgoDogInfo ngoDogInfo = ngoDogInfoService.findByRquestUpdatesId(requestUpdatesId);
        if (ngoDogInfo == null) {
            String responseBody = "{\n" +
                    "    \"errorMessage\": \"The id not found\"" + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(ngoDogInfo, HttpStatus.OK);
        }
    }


    @PostMapping("/createngodoginfo")
    @ResponseBody
    public ResponseEntity<?> createNgoDogInfo(@RequestBody @Validated NgoDogInfo ngoDogInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(buildValidationErrorResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }
        try {
            NgoDogInfo createNgoDogInfo = ngoDogInfoService.createNgoDogInfo(ngoDogInfo);
            return new ResponseEntity<>(createNgoDogInfo, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle other exceptions (if any)
            String responseBody = "{\n" + "\"errorMessage\":" + e.getMessage() + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    private ValidationErrorResponse buildValidationErrorResponse(BindingResult bindingResult) {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();

        bindingResult.getFieldErrors().forEach(fieldError -> {
            ValidationErrorDetail errorDetail = new ValidationErrorDetail(
                    fieldError.getField(),
                    fieldError.getDefaultMessage(),
                    fieldError.getRejectedValue()
            );
            errorResponse.addError(errorDetail);
        });

        return errorResponse;
    }

}
