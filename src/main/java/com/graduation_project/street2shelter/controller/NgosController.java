package com.graduation_project.street2shelter.controller;

import com.graduation_project.street2shelter.entity.Ngos;
import com.graduation_project.street2shelter.entity.Users;
import com.graduation_project.street2shelter.exception.ValidationErrorDetail;
import com.graduation_project.street2shelter.exception.ValidationErrorResponse;
import com.graduation_project.street2shelter.service.NgosService;
import com.graduation_project.street2shelter.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ngos")
@Validated
public class NgosController {
    @Autowired
    private NgosService ngosService;

    @GetMapping("/findallngos")
    @ResponseBody
    public List<Ngos> findAll() {
        return ngosService.findAllNgos();
    }

    @GetMapping("/findallngosnotapproved")
    @ResponseBody
    public List<Ngos> getAllNgoNotApproved() {
        return ngosService.getAllNgoNotApproved();
    }


    @GetMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Ngos ngos = ngosService.loginNgos(email, password);
        if (ngos == null) {
            String responseBody = "{\n" +
                    "    \"errorMessage\": \"The ngos not found \"" + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        } else {
            if (ngos.getApprovedNgo() == 0){//nada
                String responseBody = "{\n" +
                        "    \"errorMessage\": \"The ngo registration not approved\"" + "\n}";
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }
            else {
                if (ngos.getIsActive()==0)
                return new ResponseEntity<>(ngos, HttpStatus.OK);
                else {
                    String responseBody = "{\n" +
                            "    \"errorMessage\": \"The ngo is not active\"" + "\n}";
                    return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
                }
            }

        }
    }

    //nada
    @GetMapping("/otp")
    @ResponseBody
    public ResponseEntity<String> otp(@RequestParam String email,@RequestParam int otp) {
        String findNgoOtp = ngosService.findOtp(email,otp);
        if (findNgoOtp.equals("1")) {
            return ResponseEntity.ok("The otP has been send successful "); // 200 OK
        } else {
            return ResponseEntity.status(500).body("The email or otp wrong ");
        }
    }

    /*@GetMapping("/otpx")
    @ResponseBody
    public String getOtp(@RequestParam String email) {
        String newOtp = usersService.findOtp(email);
        return newOtp;
    }*/

    @RequestMapping(value = "/send-email", method = RequestMethod.POST)
    public ResponseEntity<String> sendEmail(@RequestParam String toEmail,
                                            String subject,
                                            String body
    ) {
        try {
            subject = "Change password for street2shelter project";
            System.out.println(toEmail);
            //UsersController user = new UsersController();
            //System.out.println(user);
            String newOtpbody = ngosService.findEmailOtp(toEmail);
            Ngos ngos = ngosService.updateOTP(toEmail,newOtpbody);
            System.out.println(newOtpbody);
            newOtpbody= "Please add new OTP "+ newOtpbody + " to change password for street2shelter project ";
            ngosService.sendEmail(toEmail, subject, newOtpbody);
            String msg = "{\n" + "\"Email_Status\":" + "The email has been sent successfully" + "\n}";
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending email: " + e.getMessage());
        }
    }

    @PostMapping("/createngos")
    @ResponseBody
    public ResponseEntity<?> createNgos(@RequestBody @Validated Ngos ngos, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(buildValidationErrorResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }
        try {
            ngos.setApprovedNgo(1); // Set approvedNgo to 1 (Added)
            Ngos createdNgos = ngosService.createNgo(ngos);
            return new ResponseEntity<>(createdNgos, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle other exceptions (if any)
            String responseBody = "{\n" + "\"errorMessage\":" + e.getMessage() + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("")
    @ResponseBody
    public ResponseEntity<?> updateNgos(@RequestBody @Validated Ngos ngos, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(buildValidationErrorResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }
        try {
            Ngos updateNgos = ngosService.updateNgo(ngos.getEmail(), ngos);
            return new ResponseEntity<>(updateNgos, HttpStatus.OK);

        } catch (Exception e) {
            // Handle other exceptions (if any)
            String responseBody = "{\n" + "\"errorMessage\":" + e.getMessage() + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/aproveNgo")
    @ResponseBody
    public ResponseEntity<?> approveNgos(@RequestBody Ngos ngos, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(buildValidationErrorResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }
        try {
            //return new ResponseEntity<>(ngos, HttpStatus.OK);
            if (ngos.getApprovedNgo()==1){
                ngosService.approveNgo(ngos.getEmail(), ngos);
                String responseBody = "{\n" + "\"NgoApproved\":" + "The NGO has been Approved" + "\n}";
                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            } else if (ngos.getApprovedNgo()==0) {
                ngosService.findByEmail(ngos.getEmail());
                ngosService.deleteNgos(ngos.getEmail());
                String responseBody = "{\n" + "\"NgoApproved\":" + "The NGO has been rejected and deleted" + "\n}";
                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            }
            else {
                String responseBody = "{\n" + "\"NgoApproved\":" + "the vale must be accepted or rejected" + "\n}";
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            // Handle other exceptions (if any)
            String responseBody = "{\n" + "\"errorMessage\":" + e.getMessage() + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/activeNgo")
    @ResponseBody
    public ResponseEntity<?> findIsActiveNgo(@RequestBody Ngos ngos, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(buildValidationErrorResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }
        try {
            //return new ResponseEntity<>(ngos, HttpStatus.OK);
            if (ngos.getIsActive()==1){
                ngosService.getIsActiveNgo(ngos.getEmail(),ngos.getIsActive(), ngos);
                String responseBody = "{\n" + "\"NgoActiveStatus\":" + "The NGO is inactive" + "\n}";
                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            }
            else if (ngos.getIsActive()==0){
                ngosService.getIsActiveNgo(ngos.getEmail(),ngos.getIsActive(), ngos);
                String responseBody = "{\n" + "\"NgoActiveStatus\":" + "The NGO is active" + "\n}";
                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            }
            else {
                String responseBody = "{\n" + "\"Message\":" + "the value must be inactive or active" + "\n}";
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            // Handle other exceptions (if any)
            String responseBody = "{\n" + "\"errorMessage\":" + e.getMessage() + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }



    @PutMapping("/password")
    @ResponseBody
    public ResponseEntity<?> updatePasswordNgos(@RequestParam String email, @RequestParam String password) {
      /*  if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(buildValidationErrorResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }*/
        try {
            Ngos updateNgos = ngosService.updatePasswordNgos(email, password);
            return new ResponseEntity<>(updateNgos, HttpStatus.OK);

        } catch (Exception e) {
            // Handle other exceptions (if any)
            String responseBody = "{\n" + "\"errorMessage\":" + e.getMessage() + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    //@DeleteMapping("")
    @RequestMapping(value = "/{email}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("email") String email) {
        try {
            Ngos deletedNgos = ngosService.deleteNgos(email); // get the deleted user
            // ngosService.deleteNgos(email);
            String responseBody = "{\n" + "\"Deleted\":" + "The Ngo has been deleted" + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
            // return new ResponseEntity<>(HttpStatus.NO_CONTENT);

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






