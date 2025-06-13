package com.graduation_project.street2shelter.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS") //, schema = "street2shelter" (Modified)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "first_name")
    //@NotNull(message = "FirstName cannot be null")
    @NotBlank(message = "First name is required.")
    private String firstName;
    @Column(name = "last_name")
    //@NotNull(message = "LastName cannot be null")
    @NotBlank(message = "LastName is required.")
    private String lastName;
    @Column(name = "email", unique = true)
    //@NotNull(message = "Email cannot be null")
    @NotBlank(message = "LastName is required.")
    @Email
    @NotBlank(message = "Email is required.")
    private String email;
    @Column(name = "password")
    //@NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password is required.")
    private String password;
    @Column(name = "phone_number")
    @NotNull(message = "Phone Number cannot be null")
    private int phoneNumber;
    @Column(name = "date_joined")
    @NotNull(message = "Date Joined cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateJoined;
    @Column(name = "otp")
    private Integer otp;
    @Column(name = "user_admin")
    private int userAdmin;


}