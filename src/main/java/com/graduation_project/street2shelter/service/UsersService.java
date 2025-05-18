package com.graduation_project.street2shelter.service;

import com.graduation_project.street2shelter.DTO.Admin;
import com.graduation_project.street2shelter.entity.NgoDogInfo;
import com.graduation_project.street2shelter.entity.Users;
import com.graduation_project.street2shelter.repository.UsersRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    //public Users Login(String email,String password){
    public List<Users> findAllUers() {
        //return usersRepo.Login(email,password) ;
        return usersRepo.findAll();
    }

    public Users loginUser(String email, String password) {
        //   return usersRepo.login(email,password);
        return usersRepo.findByEmailAndPassword(email, password);
    }

    public Admin loginAdminUser(String email, String password) {
        //   return usersRepo.login(email,password);
        return usersRepo.findAdminByEmailAndPassword(email, password);
    }

    public String findOtp(String email, int otp) {

        Optional<Users> existingUser = Optional.ofNullable(usersRepo.findByEmail(email));
        try {
            if (existingUser != null) {
                Users user = usersRepo.findByEmailAndOtp(email, otp);
                if (user != null) {
//                    String responseBody = "{\n" + "\"Current OTP is \":" + user.getOtp() + "\n}";
//                    return responseBody;
                    return "1";
                } else {
//                    String responseBody = "{\n" + "\"User\":" + "User not found with email" + "\n}";
//                    return responseBody;
                    return "2";
                }
            } else {
                throw new RuntimeException("User not found with email " + email);
            }
        } catch (MailException e) {
            System.out.println("Error sending email: " + e.getMessage());
            return e.getMessage();
        }

    }


    public String findEmailOtp(String email) {

        Optional<Users> existingUser = Optional.ofNullable(usersRepo.findByEmail(email));
        try {
            if (existingUser != null) {
                String otpNew = usersRepo.searchOtp(email);
                if (otpNew != null) {
                    String responseBody = otpNew;
                    return responseBody;
                } else {
                    String responseBody = "{\n" + "\"User\":" + "User not found with email" + "\n}";
                    return responseBody;
                }
            } else {
                throw new RuntimeException("User not found with email " + email);
            }
        } catch (MailException e) {
            System.out.println("Error sending email: " + e.getMessage());
            return e.getMessage();
        }

    }

    public Users createUser(Users user) {
        return usersRepo.save(user);
    }

    public Users updateUser(String email, Users user) {
        Optional<Users> existingUserOpt = Optional.ofNullable(usersRepo.findByEmail(email));
        //Users found = usersRepo.findByEmail(email);
        if (existingUserOpt.isPresent()) {
            Users existingUser = existingUserOpt.get();
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setPassword(user.getPassword());

            return usersRepo.save(existingUser);

        } else {
            throw new RuntimeException("User not found with email " + email);
        }
    }

    public Users updatePasswordUser(String email, String password) {
        Optional<Users> existingUserOpt = Optional.ofNullable(usersRepo.findByEmail(email));
        //Users found = usersRepo.findByEmail(email);
        if (existingUserOpt.isPresent()) {
            Users existingUser = existingUserOpt.get();
           // existingUser.setFirstName(user.getFirstName());
            //existingUser.setLastName(user.getLastName());
            //existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setPassword(password);
            return usersRepo.save(existingUser);
        } else {
            throw new RuntimeException("User not found with email " + email);
        }
    }

    public Users updateOTP(String email, String otp) {
        Optional<Users> existingUserOpt = Optional.ofNullable(usersRepo.findByEmail(email));
        //Users found = usersRepo.findByEmail(email);
        if (existingUserOpt.isPresent()) {
            Users existingUser = existingUserOpt.get();
            existingUser.setOtp(Integer.valueOf(otp));
            return usersRepo.save(existingUser);
        } else {
            throw new RuntimeException("User not found with email " + email);
        }
    }

    @Transactional
    public Users deleteUser(String email) {
        Users existingUser = usersRepo.findByEmail(email);
        //try {
        if (existingUser != null) {
            usersRepo.deleteByEmail(email);
            return existingUser;
        } else {
            throw new RuntimeException("User not found with email " + email);
        }
    }

   /* public void sendEmail(String toEmail, String subject, String body) throws MailException, MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setTo(toEmail);
        messageHelper.setSubject(subject);
        messageHelper.setText(body, true); // true indicates the body is HTML

        // You can also add attachments if needed, for example:
        // messageHelper.addAttachment("fileName", new File("path/to/file"));

        javaMailSender.send(mimeMessage);
    }*/


    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nhatemsalahali@gmail.com");  // Your email address
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        try {
            javaMailSender.send(message);
            System.out.println("Email sent successfully!");
        } catch (MailException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }

    public Users findByEmail(String email) {
        return usersRepo.findByEmail(email);
    }
}
