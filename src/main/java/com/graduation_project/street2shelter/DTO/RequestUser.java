package com.graduation_project.street2shelter.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class RequestUser {

    private Long requestId;
    private Long userId;
    private String status;
    private BigDecimal longitude;
    private  BigDecimal latitude;
    private Timestamp submissionTime;
    private String description;
    private String dogImage;
    private int dogsCount;
    private String streetAddress;
    private String userName;
    private int phoneNumber;
    private String acceptedNgo;
    private Timestamp acceptedDate;
    private String missionDoneNgo;
    private Timestamp missionDoneDate;

    public RequestUser(Long requestId, Long userId, String status, BigDecimal longitude, BigDecimal latitude, Timestamp submissionTime, String description, String dogImage, int dogsCount, String streetAddress, String userName, int phoneNumber, String acceptedNgo, Timestamp acceptedDate, String missionDoneNgo, Timestamp missionDoneDate) {
        this.requestId = requestId;
        this.userId = userId;
        this.status = status;
        this.longitude = longitude;
        this.latitude = latitude;
        this.submissionTime = submissionTime;
        this.description = description;
        this.dogImage = dogImage;
        this.dogsCount = dogsCount;
        this.streetAddress = streetAddress;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.acceptedNgo = acceptedNgo;
        this.acceptedDate = acceptedDate;
        this.missionDoneNgo = missionDoneNgo;
        this.missionDoneDate = missionDoneDate;
    }
// Added
    public RequestUser() {

    }


    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Timestamp getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(Timestamp submissionTime) {
        this.submissionTime = submissionTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDogImage() {
        return dogImage;
    }

    public void setDogImage(String dogImage) {
        this.dogImage = dogImage;
    }

    public int getDogsCount() {
        return dogsCount;
    }

    public void setDogsCount(int dogsCount) {
        this.dogsCount = dogsCount;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAcceptedNgo() {
        return acceptedNgo;
    }

    public void setAcceptedNgo(String acceptedNgo) {
        this.acceptedNgo = acceptedNgo;
    }

    public Timestamp getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(Timestamp acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public String getMissionDoneNgo() {
        return missionDoneNgo;
    }

    public void setMissionDoneNgo(String missionDoneNgo) {
        this.missionDoneNgo = missionDoneNgo;
    }

    public Timestamp getMissionDoneDate() {
        return missionDoneDate;
    }

    public void setMissionDoneDate(Timestamp missionDoneDate) {
        this.missionDoneDate = missionDoneDate;
    }
}
