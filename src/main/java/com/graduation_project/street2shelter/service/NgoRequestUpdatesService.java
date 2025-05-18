package com.graduation_project.street2shelter.service;

import com.graduation_project.street2shelter.DTO.LongLatRequest;
import com.graduation_project.street2shelter.DTO.NgoRequestUpdateDto;
import com.graduation_project.street2shelter.entity.NgoRequestUpdates;
import com.graduation_project.street2shelter.repository.NgoRequestUpdatesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NgoRequestUpdatesService {
    @Autowired
    private NgoRequestUpdatesRepo ngoRequestUpdatesRepo;

    public List<NgoRequestUpdates> findAllRequestUpdate() {
        return ngoRequestUpdatesRepo.findAll();
    }
    public NgoRequestUpdates getCompletedRequest(int assignment_id) {
        return ngoRequestUpdatesRepo.findCompletedRequest(assignment_id);
    }
    public int getCountRequest(int assignment_id) {
        return ngoRequestUpdatesRepo.findCountRequest(assignment_id);
    }

    public LongLatRequest getLongLatRequest(int assignment_id) {
        return ngoRequestUpdatesRepo.findLongLatRequest(assignment_id);
    }


    public void insertRequestUpdate(int assignmentId, String status, LocalDateTime transDate, String note, BigDecimal completedRequestLongitude, BigDecimal completedRequestLatitude, String ngoDogImage, int ngoDogsCount) {
        ngoRequestUpdatesRepo.insertRequestUpdate(assignmentId,status,transDate,note,completedRequestLongitude,completedRequestLatitude,ngoDogImage,ngoDogsCount);
    }

    public int isNgoFarEnough(BigDecimal userLng , BigDecimal userLat,
                                  BigDecimal ngoLng, BigDecimal ngoLat) {
        return ngoRequestUpdatesRepo.isDistanceMoreThanMeters(userLng,userLat,ngoLng,ngoLat);
    }

    public void updateRequest(int assignmentId) {
        ngoRequestUpdatesRepo.updateRequestStatus(assignmentId);
    }
    public void updateAssignmentRequest(int assignmentId) {
        ngoRequestUpdatesRepo.updateRequestAssignmentStatus(assignmentId);
    }
//    public List<NgoRequestUpdateDto> findLastRequest(){
//        return ngoRequestUpdatesRepo.findLastRequestUpdates();
//    }
   public List<NgoRequestUpdates> findLastRequestUpdateStatus(){
        return ngoRequestUpdatesRepo.findLastRequestUpdateStatus();
    }
   public void requestUpdateStatus(int assignmentId) {
       ngoRequestUpdatesRepo.requestUpdateStatus(assignmentId);
   }


}