package com.graduation_project.street2shelter.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ngo_requested_extra_time", schema = "street2shelter")
public class NgoRequestedExtraTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ngo_requested_extra_time_id")
    private Long ngoRequestedExtraTimeId;
    @Column(name = "assignment_id")
    private Long assignmentId;
    @Column(name = "extra_time_request_date")
    @NotNull(message = "Transaction Date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@NotBlank(message = "Transaction Date is required.")
    private LocalDateTime extraTimeRequestDate;
    @Column(name = "extra_needed_days")
    private Integer extraNeededDays;
    @Column(name = "extra_time_updated_deadline_date")
    @NotNull(message = "Updated Deadline Date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@NotBlank(message = "Updated Deadline Date is required.")
    private LocalDateTime extraTimeUpdatedDeadlineDays;
    @Column(name = "extra_time_action_expired_date")
    @NotNull(message = "Expired Action Date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@NotBlank(message = "Expired Action Date is required.")
    private LocalDateTime extraTimeActionExpiredDate;
    @Column(name = "is_active")
    private int isActive;


}

