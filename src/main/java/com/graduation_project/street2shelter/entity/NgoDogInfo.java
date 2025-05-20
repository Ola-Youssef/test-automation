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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ngo_dog_info", schema = "street2shelter")
public class NgoDogInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ngo_dog_info_id")
    private Long ngoDogInfoId;
    @Column(name = "ngo_request_updates_id")
    private Long ngoRequestUpdatesId;
    @Column(name = "breed")
    @NotBlank(message = "Breed is required.")
    private String breed;
    @Column(name = "color")
    @NotBlank(message = "Color is required.")
    private String color;
    @Column(name = "size")
    @NotBlank(message = "Size is required.")
    private String size;
    @Column(name = "health_status")
    @NotBlank(message = "Health Status is required.")
    private String healthStatus;
    @Column(name = "image")
    @NotBlank(message = "Image is required.")
    private String image;
    @Column(name = "created_at")
    @NotNull(message = "Created At cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @Column(name = "notes")
    private String notes;
}
