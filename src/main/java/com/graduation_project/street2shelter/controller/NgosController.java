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

    @GetMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Ngos ngos = ngosService.loginNgos(email, password);
        if (ngos == null) {
            String responseBody = "{\n" +
                    "    \"errorMessage\": \"The ngos not found\"" + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(ngos, HttpStatus.OK);
        }
    }

    //nada
    @GetMapping("/otp")
    @ResponseBody
    public String otp(@RequestParam String email,@RequestParam int otp) {
        return ngosService.findOtp(email,otp);
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






