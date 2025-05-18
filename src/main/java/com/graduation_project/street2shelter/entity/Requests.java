package com.graduation_project.street2shelter.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "requests", schema = "street2shelter")
public class Requests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "status")
    //@NotBlank(message = "Status is required.")
    private String status;
    @Column(name = "longitude")
    @NotNull(message = "Longitude cannot be null")
    private BigDecimal longitude;
    @Column(name = "latitude")
    @NotNull(message = "Latitude cannot be null")
    private  BigDecimal latitude;
    @Column(name = "submission_time")
   // @NotNull(message = "Submission Time cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime submissionTime;
    @Column(name = "description")
    private String description;
    @Column(columnDefinition = "TEXT",name = "dog_image")
    private String dogImage;
    @Column(name = "dogs_count")
   // @NotNull(message = "Estimate Dogs Count cannot be null")
    private int dogsCount;
    @Column(name = "street_address")
     @NotNull(message = "Street Address cannot be null")
    private String streetAddress;

}
