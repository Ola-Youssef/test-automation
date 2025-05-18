package com.graduation_project.street2shelter.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ngo_assignments", schema = "street2shelter")
public class NgoAssignments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long assignmentId;
    @Column(name = "request_id")
    private Long requestId;
    @Column(name = "ngo_id")
    private Long ngoId;
    @Column(name = "rules_id")
    private int ruledId;
    @Column(name = "status")
    @NotBlank(message = "Status is required.")
    private String status;
    @Column(name = "assigned_date")
    @NotNull(message = "Assigned Date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime assignedDate;
    @Column(name = "response_duration")
    private Integer responseDuration;
   // @NotNull(message = "Response Duration cannot be null")
    @Column(name = "response_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime responseDate;
   //@Column(name = "response_deadline_date")
   // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   // private LocalDateTime responseDeadlineDate;
    @Column(name = "response_expired_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime responseExpiredDate;
    //@Column(name = "action_deadline_date")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     //private LocalDateTime actionDeadlineDate;
    @Column(name = "action_duration")
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Integer actionDuration;
    @Column(name = "action_date")
    private LocalDateTime actionDate;
    @Column(name = "action_expired_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actionExpiredDate;
    @Column(name = "number_extra_time_used")
    private int numberExtraTimeUsed;
    @Column(name = "notes")
    private String notes;
    @Column(name = "number_needed_extra_time")
    private int numberNeededExtraTime;
    @Column(name = "is_actve")
    private int isActive;




}
