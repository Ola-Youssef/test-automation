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
@Table(name = "ngo_request_updates", schema = "street2shelter")
public class NgoRequestUpdates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ngo_request_updates_id")
    private Long ngoRequestUpdatesId;
    @Column(name = "assignment_id")
    private int assignmentId;
    @Column(name = "status")
    @NotBlank(message = "Status is required.")
    private String status;
    @Column(name = "transaction_date")
    @NotNull(message = "Transaction Date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotBlank(message = "Transaction Date is required.")
    private LocalDateTime transactionDate;
    @Column(name = "note")
    private String note;
    @Column(name = "completed_request_longitude")
    //@NotNull(message = "Longitude cannot be null")
    private BigDecimal completedRequestLongitude;
    @Column(name = "completed_request_latitude")
    //@NotNull(message = "Latitude cannot be null")
    private  BigDecimal completedRequestLatitude;
    @Column(columnDefinition = "TEXT",name = "ngo_dog_image")
    private String ngoDogImage;
    @Column(name = "ngo_dogs_count")
    // @NotNull(message = "Estimate Dogs Count cannot be null")
    private Integer ngoDogsCount;
}
