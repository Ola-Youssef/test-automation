package com.graduation_project.street2shelter.service;

import com.graduation_project.street2shelter.entity.Ngos;
import com.graduation_project.street2shelter.entity.Users;
import com.graduation_project.street2shelter.repository.NgosRepo;
import com.graduation_project.street2shelter.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NgosService {
    @Autowired
    private NgosRepo ngosRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    @Transactional
    public List<Ngos> findAllNgos() {
        //return ngosRepo.Login(email,password) ;
        return ngosRepo.findAll();
    }

    public Ngos loginNgos(String email, String password) {
        //   return ngosRepo.login(email,password);
        return ngosRepo.findByEmailAndPassword(email, password);
    }

    public String findOtp(String email, int otp) {

        Optional<Ngos> existingUser = Optional.ofNullable(ngosRepo.findByEmail(email));
        try {
            if (existingUser != null) {
                Ngos ngos = ngosRepo.findByEmailAndOtp(email, otp);
                if (ngos != null) {
                    String responseBody = "{\n" + "\"Current OTP is \":" + ngos.getOtp() + "\n}";
                    return responseBody;
                } else {
                    String responseBody = "{\n" + "\"Ngos\":" + "User not found with email" + "\n}";
                    return responseBody;
                }
            } else {
                throw new RuntimeException("Ngos not found with email " + email);
            }
        } catch (MailException e) {
            System.out.println("Error sending email: " + e.getMessage());
            return e.getMessage();
        }

    }


    public String findEmailOtp(String email) {

        Optional<Ngos> existingUser = Optional.ofNullable(ngosRepo.findByEmail(email));
        try {
            if (existingUser != null) {
                String otpNew = ngosRepo.searchOtp(email);
                if (otpNew != null) {
                    String responseBody = otpNew;
                    return responseBody;
                } else {
                    String responseBody = "{\n" + "\"Ngos\":" + "Ngos not found with email" + "\n}";
                    return responseBody;
                }
            } else {
                throw new RuntimeException("Ngos not found with email " + email);
            }
        } catch (MailException e) {
            System.out.println("Error sending email: " + e.getMessage());
            return e.getMessage();
        }

    }

    public Ngos createNgo(Ngos ngos) {
        return ngosRepo.save(ngos);
    }

    public Ngos updateNgo(String email, Ngos ngos) {
        Optional<Ngos> existingNgosOpt = Optional.ofNullable(ngosRepo.findByEmail(email));
        //Users found = usersRepo.findByEmail(email);
        if (existingNgosOpt.isPresent()) {
            Ngos existingUser = existingNgosOpt.get();
            existingUser.setName(ngos.getName());
            existingUser.setPhoneNumber(ngos.getPhoneNumber());
            existingUser.setPassword(ngos.getPassword());
            existingUser.setLatitude(ngos.getLatitude());
            existingUser.setLongitude(ngos.getLongitude());
            existingUser.setAddress(ngos.getAddress());
            return ngosRepo.save(existingUser);

        } else {
            throw new RuntimeException("Ngos not found with email " + email);
        }
    }

    public Ngos updatePasswordNgos(String email, String password) {
        Optional<Ngos> existingNgosOpt = Optional.ofNullable(ngosRepo.findByEmail(email));
        //Ngos found = ngosRepo.findByEmail(email);
        if (existingNgosOpt.isPresent()) {
            Ngos existingNgos = existingNgosOpt.get();
            // existingUser.setFirstName(user.getFirstName());
            //existingUser.setLastName(user.getLastName());
            //existingUser.setPhoneNumber(user.getPhoneNumber());
            existingNgos.setPassword(password);
            return ngosRepo.save(existingNgos);
        } else {
            throw new RuntimeException("Ngos not found with email " + email);
        }
    }

    public Ngos updateOTP(String email, String otp) {
        Optional<Ngos> existingNgosOpt= Optional.ofNullable(ngosRepo.findByEmail(email));
        //Ngos found = ngosrepo.findByEmail(email);
        if (existingNgosOpt.isPresent()) {
            Ngos existingUser = existingNgosOpt.get();
            existingUser.setOtp(Integer.valueOf(otp));
            return ngosRepo.save(existingUser);
        } else {
            throw new RuntimeException("Ngos not found with email " + email);
        }
    }

    @Transactional
    public Ngos deleteNgos(String email) {
        Ngos existingUser = ngosRepo.findByEmail(email);
        //try {
        if (existingUser != null) {
            ngosRepo.deleteByEmail(email);
            return existingUser;
        } else {
            throw new RuntimeException("Ngos not found with email " + email);
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
}

