package com.graduation_project.street2shelter.service;

import com.graduation_project.street2shelter.entity.NgoRequestedExtraTime;
import com.graduation_project.street2shelter.repository.NgoAssignmentsRepo;
import com.graduation_project.street2shelter.repository.NgoRequestedExtraTimeRepo;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NgoRequestedExtraTimeService {
    @Autowired
    private NgoRequestedExtraTimeRepo ngoRequestedExtraTimeRepo ;
    @Autowired
    private NgoAssignmentsRepo ngoAssignmentsRepo;

    public List<NgoRequestedExtraTime> findAllNgoRequestedExtraTime() {
        return ngoRequestedExtraTimeRepo.findAll();
    }
    public NgoRequestedExtraTime findByAssignmentId(int assignmentId) {
        return ngoRequestedExtraTimeRepo.getRequestExtraTimeByAssignment(assignmentId);
    }

     public int findCountExtraTime(int assignmentId) {
        return ngoRequestedExtraTimeRepo.getCountRequestExtraTimeByAssignment(assignmentId);
    }

    public Timestamp findRequestDateExtraTimeByAssignment(int assignmentId) {
        return ngoRequestedExtraTimeRepo.getRequestDateExtraTimeByAssignment(assignmentId);
    }

    public int createextratime(int assignmentId, int extraNeededDays, Timestamp extraNeededDate) {
        //return ngoRequestedExtraTimeRepo.save(ngoRequestedExtraTime);
        return ngoRequestedExtraTimeRepo.insertRequestExtraTime(assignmentId,extraNeededDays,extraNeededDate);
    }

}
