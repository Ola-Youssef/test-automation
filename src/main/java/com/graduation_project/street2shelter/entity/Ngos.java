package com.graduation_project.street2shelter.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ngos", schema = "street2shelter")
public class Ngos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ngo_id")
    private Long ngoId;
    @Column(name = "name")
    @NotBlank(message = "Name is required.")
    private String name ;
    @Column(name = "email", unique = true)
    @Email
    @NotBlank(message = "Email is required.")
    private String email ;
    @Column(name = "password")
    @NotBlank(message = "Password is required.")
    private String password ;
    @Column(name = "phone_number")
    @NotNull(message = "Phone Number cannot be null")
    private Integer phoneNumber ;
    @Column(name = "latitude")
    @NotNull(message = "Latitude cannot be null")
    private BigDecimal latitude;
    @Column(name = "longitude")
    @NotNull(message = "Longitude cannot be null")
    private BigDecimal longitude;
    @Column(name = "address")
    private String address;
    @Column(name = "date_joined")
    @NotNull(message = "Date Joined cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateJoined;
    @Column(name = "otp")
    private Integer otp;








}
