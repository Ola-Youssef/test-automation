package com.graduation_project.street2shelter.controller;

import com.graduation_project.street2shelter.entity.NgoAssignments;
import com.graduation_project.street2shelter.entity.Ngos;
import com.graduation_project.street2shelter.entity.Requests;
import com.graduation_project.street2shelter.entity.Users;
import com.graduation_project.street2shelter.exception.ValidationErrorDetail;
import com.graduation_project.street2shelter.exception.ValidationErrorResponse;
import com.graduation_project.street2shelter.service.NgoAssignmentsService;
import com.graduation_project.street2shelter.service.RequestsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.format.DateTimeFormatter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("/ngoassignments")
@Validated
public class NgoAssignmentsController {
    @Autowired
    private NgoAssignmentsService ngoAssignmentsService;
    @GetMapping("/findallngoassignments")
    @ResponseBody
    public List<NgoAssignments> findAll() {
        return ngoAssignmentsService.findAllNgoAssignments();
    }

    @GetMapping("/findallngoassigned")
    @ResponseBody
    public List<NgoAssignments> getAssignmentRequest() {
        return ngoAssignmentsService.findAllNgoAssignment();
    }

    @GetMapping("/findallngoaccepted")
    @ResponseBody
    public List<NgoAssignments> getAcceptedAssignmentRequest() {
        return ngoAssignmentsService.findAllNgoAcceptedAssignment();
    }

    @GetMapping("/findallngomissiondone")
    @ResponseBody
    public List<NgoAssignments> getAllNgoMissionDoneAssignment() {
        return ngoAssignmentsService.findAllNgoMissionDoneAssignment();
    }

    @PutMapping("/updateassignrequestngo")
   @ResponseBody
   public ResponseEntity<?> updateNgosassignment(@RequestBody @Validated NgoAssignments ngoAssignments, BindingResult bindingResult) {
       if (bindingResult.hasErrors()) {
           return new ResponseEntity<>(buildValidationErrorResponse(bindingResult), HttpStatus.BAD_REQUEST);
       }
       try {
           NgoAssignments ngoassign = new NgoAssignments();
           if (!(ngoAssignments.getStatus().equals("Accepted") || ngoAssignments.getStatus().equals("Rejected"))){
               String responseBody = "{\n" + "\"errorMessage\":" + "This status must be Accepted or Rejected" + "\n}";
               return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
           }
           int count = ngoAssignmentsService.findCountAssignment(Math.toIntExact(ngoAssignments.getAssignmentId()));
           if (count > 0) {
               NgoAssignments ngoAssignRequest = ngoAssignmentsService.findAssignmentRequest(Math.toIntExact(ngoAssignments.getAssignmentId()));
               // if (ngoAssignRequest.getIsActive() == 1) {
               //  String responseBody = "{\n" + "\"errorMessage\":" + "Can't update Inacative request" + "\n}";
               //return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
               //}
               //  }

               if (ngoAssignments.getStatus().equals("Accepted") && ngoAssignRequest.getStatus().equals("Assigned")) {
                   int days = ngoAssignmentsService.getDays();
                   int responseDeadlineDays = ngoAssignmentsService.getResponseDaedline();
                   //System.out.println("date "+now().plusDays(days));
                   ngoassign.setActionDuration(days);
                   ngoassign.setAssignmentId(ngoAssignments.getAssignmentId());
                   ngoassign.setNgoId(ngoAssignRequest.getNgoId());
                   ngoassign.setRequestId(ngoAssignRequest.getRequestId());
                   ngoassign.setStatus(ngoAssignments.getStatus());
                   ngoassign.setAssignedDate(ngoAssignRequest.getAssignedDate());
                   ngoassign.setResponseDate(now());
                   ngoassign.setResponseDuration(responseDeadlineDays);
                   ngoassign.setResponseExpiredDate(ngoAssignRequest.getResponseExpiredDate());
                   ngoassign.setActionExpiredDate(ngoAssignRequest.getActionExpiredDate());
                   ngoassign.setNumberExtraTimeUsed(ngoAssignRequest.getNumberExtraTimeUsed());
                   ngoassign.setNotes(ngoAssignments.getNotes());
                   ngoassign.setRuledId(ngoAssignRequest.getRuledId());
                   //ngoassign.setActionDate(now());
                   //System.out.println("Assignment "+ngoAssignRequest.getAssignmentId());
                   //System.out.println(ngoAssignRequest.getNumberNeededExtraTime());
                   ngoassign.setNumberNeededExtraTime(ngoAssignRequest.getNumberNeededExtraTime());
                   ngoAssignmentsService.requestUpdateActive(ngoAssignRequest.getAssignmentId().intValue());
                   ngoAssignmentsService.updateAllAssignmentRequest(ngoAssignRequest.getRequestId().intValue(), ngoAssignRequest.getAssignmentId().intValue());
                   ngoAssignmentsService.updateRequestStatus(ngoAssignRequest.getRequestId().intValue());
                   ngoAssignmentsService.insertRequestUpdate(ngoAssignRequest.getAssignmentId().intValue(), "Accepted", ngoAssignments.getNotes());
               }
               if (ngoAssignments.getStatus().equals("Rejected") && ngoAssignRequest.getStatus().equals("Assigned")) {
                   ngoassign.setIsActive(1);
                   ngoassign.setActionDuration(ngoAssignRequest.getActionDuration());
                   ngoassign.setAssignmentId(ngoAssignments.getAssignmentId());
                   ngoassign.setNgoId(ngoAssignRequest.getNgoId());
                   ngoassign.setRequestId(ngoAssignRequest.getRequestId());
                   ngoassign.setStatus(ngoAssignments.getStatus());
                   ngoassign.setAssignedDate(ngoAssignRequest.getAssignedDate());
                   ngoassign.setResponseDate(now());
                   //ngoassign.setActionDate(now());
                   ngoassign.setResponseExpiredDate(ngoAssignRequest.getResponseExpiredDate());
                   ngoassign.setResponseExpiredDate(ngoAssignRequest.getResponseExpiredDate());
                   ngoassign.setActionExpiredDate(ngoAssignRequest.getActionExpiredDate());
                   ngoassign.setNumberExtraTimeUsed(ngoAssignRequest.getNumberExtraTimeUsed());
                   ngoassign.setNotes(ngoAssignments.getNotes());
                   ngoassign.setRuledId(ngoAssignRequest.getRuledId());
                   ngoassign.setResponseDuration(ngoAssignRequest.getResponseDuration());
                   //System.out.println(ngoAssignRequest.getAssignmentId());
                   //System.out.println(ngoAssignRequest.getNumberNeededExtraTime());
                   ngoassign.setNumberNeededExtraTime(ngoAssignRequest.getNumberNeededExtraTime());
               }
               NgoAssignments createdNgosAssignmentRequests = ngoAssignmentsService.updateNgoAssignmentRequest(ngoassign);
               return new ResponseEntity<>(createdNgosAssignmentRequests, HttpStatus.OK);
           }
           else {
               String responseBody = "{\n" + "\"errorMessage\":" + "Can't update the data" + "\n}";
               return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
           }
       }   catch (Exception e) {
           // Handle other exceptions (if any)
           String responseBody = "{\n" + "\"errorMessage\":" + e.getMessage() + "\n}";
           return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
       }
   }

    @GetMapping("/activengorequestassignments")
    @ResponseBody
    public List<NgoAssignments> findActiveNgoAssignment(@RequestParam int requestId) {
        return ngoAssignmentsService.findActiveAssignmentRequest(requestId);
    }

    @GetMapping("/ngorequestassignments")
    @ResponseBody
    public List<NgoAssignments> findNgoRequestAssignment(@RequestParam int requestId) {
        return ngoAssignmentsService.findAllAssignmentRequest(requestId);
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
