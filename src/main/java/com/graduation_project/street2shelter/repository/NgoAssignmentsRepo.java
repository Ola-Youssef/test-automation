package com.graduation_project.street2shelter.repository;

import com.graduation_project.street2shelter.entity.NgoAssignments;
import com.graduation_project.street2shelter.entity.Requests;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.digester.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface NgoAssignmentsRepo extends JpaRepository <NgoAssignments, Long> {

    @Query(value = "select action_deadline_days from rules where rules_id = 3", nativeQuery = true)
    int rule();

    @Query(value = "select response_daedline_days from rules where rules_id = 3", nativeQuery = true)
    int responseDaedlineDaysRule();

    @Query(value = "select * from ngo_assignments where assignment_id = :assignment_id and is_actve = 0 and Status = 'Assigned' ", nativeQuery = true)
    NgoAssignments findAssignment(@Param("assignment_id") int assignmentId);

    @Query(value = "select * from ngo_assignments where assignment_id = :assignment_id and is_actve = 0 and Status = 'Accepted' ", nativeQuery = true)
    NgoAssignments findAcceptedAssignment(@Param("assignment_id") int assignmentId);

    @Query(value = "select * from ngo_assignments where request_id = :request_id and is_actve = 0", nativeQuery = true)
    List<NgoAssignments> getActiveNgoAssignment(@Param("request_id") int request_id);

    @Query(value = "select * from ngo_assignments where request_id = :request_id", nativeQuery = true)
    List<NgoAssignments> getAllNgoAssignment(@Param("request_id") int request_id);

    @Query(value = "select count(*) from ngo_assignments where assignment_id = :assignment_id ", nativeQuery = true)
    int getCountAssignment(@Param("assignment_id") int assignmentId);

    @Query(value = "select * from ngo_assignments where is_actve = 0 and Status = 'Assigned' ", nativeQuery = true)
    List<NgoAssignments> getAllNgoAssignment();

    @Query(value = "select * from ngo_assignments where is_actve = 0 and Status = 'Accepted' ", nativeQuery = true)
    List<NgoAssignments> getAllNgoAcceptedAssignment();

    @Query(value = "select * from ngo_assignments where is_actve = 0 and Status = 'Mission Done' ", nativeQuery = true)
    List<NgoAssignments> getAllNgoMissionDoneAssignment();

    @Modifying
    @Transactional
    @Query(value = "update ngo_assignments set status = 'Withdraw' , response_date = NOW() , is_actve = 1 where request_id = :request_id and assignment_id <>:assignment_id and is_actve = 0", nativeQuery = true)
    void updateNgoAssignment(@Param("request_id") int request_id,@Param("assignment_id") int assignment_id);

    @Modifying
    @Transactional
    @Query(value = "update requests set status = 'Accepted' where request_id = :request_id", nativeQuery = true)
    void updateRequestStatus(@Param("request_id") int request_id);

    @Modifying
    @Transactional
    @Query(value = "insert into ngo_request_updates (assignment_id,status,transaction_date,note) values(:assignment_id,:status,NOW(),:note) ", nativeQuery = true)
    void insertRequestUpdate(@Param("assignment_id") int assignment_id,@Param("status") String status,@Param("note") String note);

    @Modifying
    @Transactional
    @Query(value = "update ngo_request_updates set Is_Active = 1 where assignment_id = :assignment_id", nativeQuery = true)
    void requestUpdateActive(@Param("assignment_id") int assignment_id);

    @Modifying
    @Transactional
    @Query(value = "update ngo_requested_extra_time set is_active = 1 where assignment_id = :assignment_id", nativeQuery = true)
    void requestExtraTimeActive(@Param("assignment_id") int assignment_id);

    @Modifying
    @Transactional
   // @Query(value = "update ngo_assignments set number_extra_time_used = number_extra_time_used + 1 , action_deadline_date = (SELECT action_deadline_date + INTERVAL extra_time_days DAY FROM rules) where assignment_id = :assignment_id and response_expired_date is null and action_expired_date is null and is_actve = 0 ", nativeQuery = true)
    @Query(value = "update ngo_assignments set number_extra_time_used = number_extra_time_used + 1  where assignment_id = :assignment_id and response_expired_date is null and action_expired_date is null and is_actve = 0 and status = 'Accepted' ", nativeQuery = true)
    void updateAssignmentExtraTime(@Param("assignment_id") int assignment_id);

    @Modifying
    @Transactional
    @Query(value = "delete from ngo_assignments where request_id = :request_id", nativeQuery = true)
    void deletNgoAssignment(@Param("request_id") int request_id);
}
