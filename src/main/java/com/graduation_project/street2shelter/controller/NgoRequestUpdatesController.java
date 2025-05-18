package com.graduation_project.street2shelter.controller;

import com.graduation_project.street2shelter.DTO.LongLatRequest;
import com.graduation_project.street2shelter.DTO.NgoRequestUpdateDto;
import com.graduation_project.street2shelter.entity.NgoRequestUpdates;
import com.graduation_project.street2shelter.exception.ValidationErrorDetail;
import com.graduation_project.street2shelter.exception.ValidationErrorResponse;
import com.graduation_project.street2shelter.service.NgoRequestUpdatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("/requestupdate")
@Validated
public class NgoRequestUpdatesController {
    @Autowired
    private NgoRequestUpdatesService ngoRequestUpdatesService;

    @GetMapping("/findallrequestupdate")
    @ResponseBody
    public List<NgoRequestUpdates> findAll() {
        return ngoRequestUpdatesService.findAllRequestUpdate();
    }

    @GetMapping("/findLastRequests")
    @ResponseBody
    public List<NgoRequestUpdates> findLastRequests() {
       //List<NgoRequestUpdateDto> lastRequests = ngoRequestUpdatesService.findLastRequest();

        return ngoRequestUpdatesService.findLastRequestUpdateStatus();
    }


    @PostMapping("/createRequestUpdate")
    @ResponseBody
    public ResponseEntity<?> createRequestupdate(@RequestBody NgoRequestUpdates ngoRequestUpdates, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(buildValidationErrorResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }
        try {
            //System.out.println(LocalDateTime.now());
            int count = ngoRequestUpdatesService.getCountRequest(ngoRequestUpdates.getAssignmentId());
            if (count == 0) {
                if (ngoRequestUpdates.getStatus().equals("Mission Done")) {
                    String responseBody = "{\n" + "\"errorMessage\":" + "The request must be accepted first" + "\n}";
                    return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
                }
                if (ngoRequestUpdates.getStatus().equals("Accepted")) {
                    ngoRequestUpdatesService.updateRequest(ngoRequestUpdates.getAssignmentId());
                }
            }
            if (count > 0) {
                NgoRequestUpdates ngoRequestUpdates1 = ngoRequestUpdatesService.getCompletedRequest(ngoRequestUpdates.getAssignmentId());
                if (ngoRequestUpdates1.getStatus().equals("Mission Done")) {
                    String responseBody = "{\n" + "\"errorMessage\":" + "The request already Mission Done" + "\n}";
                    return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
                }
                if (ngoRequestUpdates1.getStatus().equals("Accepted")) {
                    if (ngoRequestUpdates.getStatus().equals("Accepted")) {
                        String responseBody = "{\n" + "\"errorMessage\":" + "The request already Accepted" + "\n}";
                        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
                    }
                    if (ngoRequestUpdates.getStatus().equals("Mission Done")) {
                        LongLatRequest longLatRequest = ngoRequestUpdatesService.getLongLatRequest(ngoRequestUpdates.getAssignmentId());
                        System.out.println("hhhhhhhhhh "+longLatRequest.getLongitude());
                        System.out.println("hhhhhhhhhh "+longLatRequest.getLatitude());
                        System.out.println("hhhhhhhhhh "+ngoRequestUpdates.getCompletedRequestLongitude());
                        System.out.println("hhhhhhhhhh "+ngoRequestUpdates.getCompletedRequestLatitude());
                        int pickupLocation = ngoRequestUpdatesService.isNgoFarEnough(longLatRequest.getLongitude(),longLatRequest.getLatitude(),ngoRequestUpdates.getCompletedRequestLongitude(),ngoRequestUpdates.getCompletedRequestLatitude());
                        System.out.println("hhhhhhhhhh"+pickupLocation);
                        //return pickupLocation;
                        //String responseBody = "{\n" + "\"Message\":" + "The request has been done successfully " + "\n}";

                        if (pickupLocation == 1){
                            ngoRequestUpdatesService.updateAssignmentRequest(ngoRequestUpdates.getAssignmentId());
                            ngoRequestUpdatesService.requestUpdateStatus(ngoRequestUpdates.getAssignmentId());
                            ngoRequestUpdatesService.updateRequest(ngoRequestUpdates.getAssignmentId());
                            ngoRequestUpdatesService.insertRequestUpdate(ngoRequestUpdates.getAssignmentId(), "Mission Done", now(), ngoRequestUpdates.getNote(),ngoRequestUpdates.getCompletedRequestLongitude(),ngoRequestUpdates.getCompletedRequestLatitude(),ngoRequestUpdates.getNgoDogImage(),ngoRequestUpdates.getNgoDogsCount());

                            String responseBody = "{\n" + "\"Message\":" + "The request has been done successfully " + "\n}";
                            return new ResponseEntity<>(responseBody, HttpStatus.CREATED);

                        }
                        else {
                            String responseBody = "{\n" + "\"Message\":" + "The pickup location is completely away from the original request." + "\n}";
                            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
                        }

                    }
                }
                else {
                    String responseBody = "{\n" + "\"errorMessage\":" + "The request must be accepted first" + "\n}";
                    return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
                }

            }
            //}
        } catch (Exception e) {
            // Handle other exceptions (if any)
            String responseBody = "{\n" + "\"errorMessage\":" + e.getMessage() + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("{\"errorMessage\":\"Unhandled condition.\"}", HttpStatus.BAD_REQUEST);
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
