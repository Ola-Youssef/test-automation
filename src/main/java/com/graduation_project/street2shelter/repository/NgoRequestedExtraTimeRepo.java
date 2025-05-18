package com.graduation_project.street2shelter.repository;

import com.graduation_project.street2shelter.entity.NgoAssignments;
import com.graduation_project.street2shelter.entity.NgoRequestedExtraTime;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface NgoRequestedExtraTimeRepo extends JpaRepository<NgoRequestedExtraTime, Long>{

    NgoRequestedExtraTime findByAssignmentId(int assignmentId);


    @Query(value = "select * from ngo_requested_extra_time where assignment_id = :assignment_id and is_active = 0 ", nativeQuery = true)
    NgoRequestedExtraTime getRequestExtraTimeByAssignment(@Param("assignment_id") int assignment_id);


    @Query(value = "select count(*) from ngo_requested_extra_time where assignment_id = :assignment_id and is_active = 0 ", nativeQuery = true)
    int getCountRequestExtraTimeByAssignment(@Param("assignment_id") int assignment_id);


    @Query(value = "select extra_time_updated_deadline_date from street2shelter.ngo_requested_extra_time where is_active = 0 and assignment_id = :assignment_id", nativeQuery = true)
    Timestamp getRequestDateExtraTimeByAssignment(@Param("assignment_id") int assignment_id);

    @Modifying
    @Transactional
    //@Query(value = "insert into ngo_requested_extra_time (assignment_id,extra_time_request_date,extra_needed_days,extra_time_updated_deadline_date) values(:assignment_id,NOW(),(SELECT extra_time_days FROM rules),(SELECT NOW() + INTERVAL extra_time_days DAY FROM rules) ) ", nativeQuery = true)
    @Query(value = "insert into ngo_requested_extra_time (assignment_id,extra_time_request_date,extra_needed_days,extra_time_updated_deadline_date) values(:assignment_id,NOW(),:extraNeededDays,DATE_ADD(:extraTimeUpdatedDeadlineDate, INTERVAL :extraNeededDays DAY) ) ", nativeQuery = true)
    int insertRequestExtraTime(@Param("assignment_id") int assignment_id,@Param("extraNeededDays") int extraNeededDays,@Param("extraTimeUpdatedDeadlineDate") Timestamp extraTimeUpdatedDeadlineDate);


//INTERVAL extra_time_days DAY FROM rules

}
