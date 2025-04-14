package com.graduation_project.street2shelter.controller;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.graduation_project.street2shelter.entity.NgoDogInfo;
import com.graduation_project.street2shelter.entity.Users;
import com.graduation_project.street2shelter.exception.ValidationErrorDetail;
import com.graduation_project.street2shelter.exception.ValidationErrorResponse;
import com.graduation_project.street2shelter.repository.UsersRepo;
import com.graduation_project.street2shelter.service.UsersService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import javax.xml.transform.Result;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.management.Query.or;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/users")
@Validated
public class UsersController {

    @Autowired
    private UsersService usersService;

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

    @GetMapping("/findallusers")
    public List<Users> findAll() {
        return usersService.findAllUers();
    }

    @GetMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Users user = usersService.loginUser(email, password);
        if (user == null) {
            String responseBody = "{\n" +
                    "    \"errorMessage\": \"The user not found\"" + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    //hatem
    @GetMapping("/otp")
    @ResponseBody
    public String otp(@RequestParam String email,@RequestParam int otp) {
        return usersService.findOtp(email,otp);
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
            String newOtpbody = usersService.findEmailOtp(toEmail);
            Users user = usersService.updateOTP(toEmail,newOtpbody);
            System.out.println(newOtpbody);
            newOtpbody= "Please add new OTP "+ newOtpbody + " to change password for street2shelter project ";
            usersService.sendEmail(toEmail, subject, newOtpbody);
            String msg = "{\n" + "\"Email_Status\":" + "The email has been sent successfully" + "\n}";
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending email: " + e.getMessage());
        }
    }

    @PostMapping("/createuser")
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody @Validated Users users, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(buildValidationErrorResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }
        try {
            Users createdUser = usersService.createUser(users);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle other exceptions (if any)
            String responseBody = "{\n" + "\"errorMessage\":" + e.getMessage() + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("")
    @ResponseBody
    public ResponseEntity<?> updateUser(@RequestBody @Validated Users users, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(buildValidationErrorResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }
        try {
            Users updateUser = usersService.updateUser(users.getEmail(), users);
            return new ResponseEntity<>(updateUser, HttpStatus.OK);

        } catch (Exception e) {
            // Handle other exceptions (if any)
            String responseBody = "{\n" + "\"errorMessage\":" + e.getMessage() + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/password")
    @ResponseBody
    public ResponseEntity<?> updatePasswordUser(@RequestParam String email, @RequestParam String password) {
      /*  if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(buildValidationErrorResponse(bindingResult), HttpStatus.BAD_REQUEST);
        }*/
        try {
            Users updateUser = usersService.updatePasswordUser(email, password);
            return new ResponseEntity<>(updateUser, HttpStatus.OK);

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
            Users deletedUser = usersService.deleteUser(email); // get the deleted user
            // usersService.deleteUser(email);
            String responseBody = "{\n" + "\"Deleted\":" + "The user has been deleted" + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
            // return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            // Handle other exceptions (if any)
            String responseBody = "{\n" + "\"errorMessage\":" + e.getMessage() + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findbyemail")
    @ResponseBody
    public ResponseEntity<?> findByEmail(@RequestParam String email) {
        Users users = usersService.findByEmail(email);
        if (users == null) {
            String responseBody = "{\n" +
                    "    \"errorMessage\": \"The email is not found\"" + "\n}";
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

}
