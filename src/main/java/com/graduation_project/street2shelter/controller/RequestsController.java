package com.graduation_project.street2shelter.controller;

import com.graduation_project.street2shelter.DTO.RequestUser;
import com.graduation_project.street2shelter.entity.NgoAssignments;
import com.graduation_project.street2shelter.entity.Requests;
import com.graduation_project.street2shelter.exception.ValidationErrorDetail;
import com.graduation_project.street2shelter.exception.ValidationErrorResponse;
import com.graduation_project.street2shelter.service.NgoAssignmentsService;
import com.graduation_project.street2shelter.service.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/requests")
@Validated
public class RequestsController {
    @Autowired
    private RequestsService requestsService;
    @Autowired
    private NgoAssignmentsService ngoAssignmentsService;

    @GetMapping("/findallrequests")
    @ResponseBody
    public List<Requests> findAll() {
        return requestsService.findAllRequests();
    }

    @GetMapping("/getallinprogressrequests")
    @ResponseBody
    public List<RequestUser> getInprogressRequests() {
        return requestsService.getInprogressRequests();
    }

    @GetMapping("/getacceptedrequests")
    @ResponseBody
    public List<RequestUser> getAcceptedRequests() {
        return requestsService.getAcceptedRequests();
    }

    @GetMapping("/getmissiondonerequests")
    @ResponseBody
    public List<RequestUser> getMissionDoneRequests() {
        return requestsService.getMissionDoneRequests();
    }//0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000

    @GetMapping("/getrequstinfo")
    @ResponseBody
    public ResponseEntity<Object> getRequestInfo(@RequestParam int requestId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Response-Info", "Request data fetched successfully");
        headers.add("X-Custom-Header", "Street2ShelterApp");
        RequestUser requestUser = requestsService.findRequestUser(requestId);
        return ResponseEntity.ok()
                .headers(headers)
                .body(requestUser);
    }

    @PostMapping("/createrequests")
    @ResponseBody
    public ResponseEntity<?> createRequests(@RequestBody @Validated Requests requests, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(buildValidationErrorResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }
        try {
            requests.setSubmissionTime(LocalDateTime.now());
            requests.setStatus("Inprogress");
            Requests createRequests = requestsService.createRequest(requests);

            //System.out.println("helllllllllllo");
            List<Object[]> nearngoid = requestsService.findNearestNgoForRequest(requests.getRequestId());
//            for (Object[] row : nearngoid) {
//                System.out.println("helllllllllllo"+Arrays.toString(row));
//            }
            // Iterate over each row (Object[])
            for (int rowIndex = 0; rowIndex < nearngoid.size(); rowIndex++) {
                Object[] row = nearngoid.get(rowIndex);
                NgoAssignments ngoAssignments = new NgoAssignments();
                System.out.println("Row " + rowIndex + ":");
                int days = (int) row[1];
                int extraTimeNumber = (int) row[2];
                System.out.println("Row1 " + rowIndex + ":");
                // Iterate over each column in the current row
                for (int colIndex = 0; colIndex < row.length; colIndex++) {
                    Object columnValue = row[colIndex];
                    System.out.println("  Column " + colIndex + ": " + columnValue);
                    Long ngoId = ((Number) row[0]).longValue();
                    ngoAssignments.setNgoId(ngoId);
                    ngoAssignments.setRequestId(requests.getRequestId());
                    ngoAssignments.setStatus("Assigned");
                    ngoAssignments.setAssignedDate(requests.getSubmissionTime());
                    //System.out.println("  days " + days);
                    //System.out.println("  extraTimeNumber " + extraTimeNumber);
                    ngoAssignments.setResponseDuration(days);
                    ngoAssignments.setNumberNeededExtraTime(extraTimeNumber);
                    ngoAssignments.setRuledId(3);

                    //ngoAssignments.setNumberExtraTimeUsed(row.length);
                    //ngoAssignments.setNumberNeededExtraTime(Math.toIntExact(nearngoid.size()));

                }
                NgoAssignments ngoAssimentrequest = ngoAssignmentsService.updateNgoAssignmentRequest(ngoAssignments);
                //System.out.println("-----------------------");
            }

//            for (Object[] row : nearngoid) {
//                Long ngoId = ((Number) row[0]).longValue();
//                //Long responseDeadlineDays = ((Number) row[1]).longValue();
//                //System.out.println("ResponseDeadlineDate"+responseDeadlineDays);
//                NgoAssignments ngoAssignments = new NgoAssignments();
//                ngoAssignments.setNgoId(ngoId);
//                ngoAssignments.setRequestId(requests.getRequestId());
//                ngoAssignments.setStatus("Inprogress");
//                ngoAssignments.setAssignedDate(requests.getSubmissionTime());
//                //ngoAssignments.setResponseDeadlineDate(requests.getSubmissionTime().plusDays(responseDeadlineDays));
//                //ngoAssignments.setNumberNeededExtraTime(Math.toIntExact(responseDeadlineDays));
//
//                NgoAssignments ngoAssimentrequest = ngoAssignmentsService.createNgoAssignmentRequest(ngoAssignments);
//            }
             /**/
            return new ResponseEntity<>(createRequests, HttpStatus.CREATED);
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

    @PutMapping("")
    @ResponseBody
    public ResponseEntity<?> updateReq(@RequestBody @Validated Requests requests, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(buildValidationErrorResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }
        try {
            Requests updateRequest = requestsService.updateRequests(requests.getRequestId(), requests);
            return new ResponseEntity<>(updateRequest, HttpStatus.OK);

        } catch (Exception e) {
            // Handle other exceptions (if any)
            String responseBody = "{\n" + "\"errorMessage\":" + e.getMessage() + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    //@DeleteMapping("")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            Optional<Requests> deleteRequest = requestsService.deleteRequest(id); // get the deleted user
            // ngosService.deleteNgos(email);
            String responseBody = "{\n" + "\"Deleted\":" + "The request has been deleted" + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
            // return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            // Handle other exceptions (if any)
            String responseBody = "{\n" + "\"errorMessage\":" + e.getMessage() + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }


}
