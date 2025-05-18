package com.graduation_project.street2shelter.controller;

import com.graduation_project.street2shelter.entity.NgoAssignments;
import com.graduation_project.street2shelter.entity.NgoDogInfo;
import com.graduation_project.street2shelter.entity.NgoRequestedExtraTime;
import com.graduation_project.street2shelter.exception.ValidationErrorDetail;
import com.graduation_project.street2shelter.exception.ValidationErrorResponse;
import com.graduation_project.street2shelter.service.NgoAssignmentsService;
import com.graduation_project.street2shelter.service.NgoDogInfoService;
import com.graduation_project.street2shelter.service.NgoRequestedExtraTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("/extratime")
@Validated
public class NgoRequestedExtraTimeController {
    @Autowired
    private NgoRequestedExtraTimeService ngoRequestedExtraTimeService;
    @Autowired
    private NgoAssignmentsService ngoAssignmentsService;

    @GetMapping("/findallneededextratime")
    @ResponseBody
    public List<NgoRequestedExtraTime> findAll() {
        return ngoRequestedExtraTimeService.findAllNgoRequestedExtraTime();
    }

    @GetMapping("/findbyassignmentid")
    @ResponseBody
    public ResponseEntity<?> findByAssignmentId(@RequestParam int assignmentId) {
        NgoRequestedExtraTime ngoRequestedExtraTime = ngoRequestedExtraTimeService.findByAssignmentId(assignmentId);
        try {
            if (ngoRequestedExtraTime == null) {
                String responseBody = "{\n" +
                        "    \"errorMessage\": \"The assignment id not found\"" + "\n}";
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(ngoRequestedExtraTime, HttpStatus.OK);
            }
        } catch (Exception e) {
            // Handle other exceptions (if any)
            String responseBody = "{\n" + "\"errorMessage\":" + e.getMessage() + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/createextratime")
    @ResponseBody
    public ResponseEntity<?> createextratime(@RequestBody @Validated NgoRequestedExtraTime ngoRequestedExtraTime, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(buildValidationErrorResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }
        try {
             NgoAssignments ngoAssignments = ngoAssignmentsService.findAcceptedAssignmentRequest(ngoRequestedExtraTime.getAssignmentId().intValue());
           // ngoAssignments.get
            if (ngoAssignments.getNumberNeededExtraTime()-ngoAssignments.getNumberExtraTimeUsed() >0) {
                //ngoAssignments.setNumberExtraTimeUsed(ngoAssignments.getNumberNeededExtraTime()+1);
                int ngocountRequestedExtraTime1 = ngoRequestedExtraTimeService.findCountExtraTime(ngoRequestedExtraTime.getAssignmentId().intValue());
                System.out.println("helooooo "+ ngocountRequestedExtraTime1 );
                if (ngocountRequestedExtraTime1 < 1){
                    //NgoRequestedExtraTime ngoRequestedExtraTime1 = ngoRequestedExtraTimeService.findByAssignmentId(ngoRequestedExtraTime.getAssignmentId().intValue());
                    ngoAssignmentsService.udateAssignmentExtraTime(ngoAssignments.getAssignmentId().intValue());
                    int createextratime = ngoRequestedExtraTimeService.createextratime(ngoRequestedExtraTime.getAssignmentId().intValue(),ngoRequestedExtraTime.getExtraNeededDays(), Timestamp.valueOf(now()));
                    //System.out.println(ngoRequestedExtraTime1.getAssignmentId());

                }
                else {
                    //NgoRequestedExtraTime ngoRequestedExtraTime1 = ngoRequestedExtraTimeService.findByAssignmentId(ngoRequestedExtraTime.getAssignmentId().intValue());
                    Timestamp ngoRequestedDate = ngoRequestedExtraTimeService.findRequestDateExtraTimeByAssignment(ngoRequestedExtraTime.getAssignmentId().intValue());
                    ngoAssignmentsService.udateAssignmentExtraTime(ngoAssignments.getAssignmentId().intValue());
                    int createextratime = ngoRequestedExtraTimeService.createextratime(ngoRequestedExtraTime.getAssignmentId().intValue(),ngoRequestedExtraTime.getExtraNeededDays(), ngoRequestedDate);
                    //System.out.println(ngoRequestedExtraTime1.getAssignmentId());
                }


                String responseBody = "{\n" + "\"Message\":" + "The time has extended" + "\n}";
                return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
            }

        else {
                String responseBody = "{\n" + "\"errorMessage\":" + "No extra time available" + "\n}";
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }
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
