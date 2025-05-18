package com.graduation_project.street2shelter.service;

import com.graduation_project.street2shelter.entity.NgoAssignments;
import com.graduation_project.street2shelter.repository.NgoAssignmentsRepo;
import com.graduation_project.street2shelter.repository.NgoRequestedExtraTimeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class NgoAssignmentsService {
    @Autowired
    private NgoAssignmentsRepo ngoAssignmentsRepo;
    @Autowired
    private NgoRequestedExtraTimeRepo ngoRequestedExtraTimeRepo;
    public List<NgoAssignments> findAllNgoAssignments() {
        return ngoAssignmentsRepo.findAll();
    }
  /*  public NgoAssignments createNgoAssignmentRequest(NgoAssignments ngoAssignments) {
        return ngoAssignmentsRepo.save(ngoAssignments);
    }*/

    public int getDays(){
        return ngoAssignmentsRepo.rule();
    }
    public int getResponseDaedline(){
        return ngoAssignmentsRepo.responseDaedlineDaysRule();
    }
    public NgoAssignments updateNgoAssignmentRequest(NgoAssignments ngoAssignments) {
        return ngoAssignmentsRepo.save(ngoAssignments);
    }
    public NgoAssignments findAssignmentRequest(int assignmentID) {
        return ngoAssignmentsRepo.findAssignment(assignmentID);
    }
    public NgoAssignments findAcceptedAssignmentRequest(int assignmentID) {
        return ngoAssignmentsRepo.findAcceptedAssignment(assignmentID);
    }
    public int findCountAssignment(int assignmentID) {
        return ngoAssignmentsRepo.getCountAssignment(assignmentID);
    }
    public List<NgoAssignments> findActiveAssignmentRequest(int requestId) {
        return ngoAssignmentsRepo.getActiveNgoAssignment(requestId);
    }
    public List<NgoAssignments> findAllAssignmentRequest(int requestId) {
        return ngoAssignmentsRepo.getAllNgoAssignment(requestId);
    }

    public List<NgoAssignments> findAllNgoAssignment() {
        return ngoAssignmentsRepo.getAllNgoAssignment();
    }

    public List<NgoAssignments> findAllNgoAcceptedAssignment() {
        return ngoAssignmentsRepo.getAllNgoAcceptedAssignment();
    }

    public List<NgoAssignments> findAllNgoMissionDoneAssignment() {
        return ngoAssignmentsRepo.getAllNgoMissionDoneAssignment();
    }

    public void updateAllAssignmentRequest(int requestId,int assignmentId) {
        ngoAssignmentsRepo.updateNgoAssignment(requestId,assignmentId);
    }
    public void updateRequestStatus(int requestId) {
        ngoAssignmentsRepo.updateRequestStatus(requestId);
    }
    public void requestUpdateActive(int assignmentID) {
        ngoAssignmentsRepo.requestUpdateActive(assignmentID);
    }
    public void insertRequestUpdate(int assignmentId,String status,String note) {
        ngoAssignmentsRepo.insertRequestUpdate(assignmentId,status,note);
    }
    public void udateAssignmentExtraTime (int assignmentId){
        //ngoRequestedExtraTimeRepo.getRequestDateExtraTimeByAssignment(assignmentId);
        ngoAssignmentsRepo.requestExtraTimeActive(assignmentId);
        ngoAssignmentsRepo.updateAssignmentExtraTime(assignmentId);
    }

}