package com.graduation_project.street2shelter.repository;

import com.graduation_project.street2shelter.DTO.LongLatRequest;
import com.graduation_project.street2shelter.DTO.NgoRequestUpdateDto;
import com.graduation_project.street2shelter.entity.NgoRequestUpdates;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NgoRequestUpdatesRepo extends JpaRepository <NgoRequestUpdates, Long> {

//    @Query("SELECT (" +
//            "MAX(r.ngoRequestUpdatesId), r.assignmentId) " +
//            "FROM NgoRequestUpdates r " +
//            "WHERE r.status = 'Completed' " +
//            "GROUP BY r.assignmentId")
//    List<NgoRequestUpdateDto> findCompletedRequestUpdates();

    /*@Query("SELECT new com.graduation_project.street2shelter.DTO.NgoRequestUpdateDto( " +
            "MAX(r.ngoRequestUpdatesId), r.assignmentId)" +
            "FROM NgoRequestUpdates r " +
            "GROUP BY r.assignmentId")
    List<NgoRequestUpdateDto> findLastRequestUpdates();

     */

    @Query(value = "select  * from ngo_request_updates where Is_Active = 0 ", nativeQuery = true)
    List<NgoRequestUpdates> findLastRequestUpdateStatus();

    @Query(value = "select  * from ngo_request_updates where Is_Active = 0 and assignment_id = :assignment_id ", nativeQuery = true)
    NgoRequestUpdates findCompletedRequest(@Param("assignment_id") int assignment_id);

    @Query(value = "select  count(*) from ngo_request_updates where Is_Active = 0 and assignment_id = :assignment_id ", nativeQuery = true)
    int findCountRequest(@Param("assignment_id") int assignment_id);

    @Query(value = "select longitude,latitude from ngo_assignments,requests where ngo_assignments.request_id = requests.request_id and assignment_id = :assignment_id ", nativeQuery = true)
    LongLatRequest findLongLatRequest(@Param("assignment_id") int assignment_id);

    @Query(value = """
    SELECT 
        CASE 
            WHEN ST_Distance_Sphere(point(:reqLng, :reqLat), point(:ngoLng, :ngoLat)) < 200 THEN 1
            ELSE 0 
        END AS isFarEnough
    """, nativeQuery = true)
    int isDistanceMoreThanMeters(@Param("reqLng") BigDecimal reqLat,
                                        @Param("reqLat") BigDecimal reqLng,
                                        @Param("ngoLng") BigDecimal ngoLat,
                                        @Param("ngoLat") BigDecimal ngoLng);

    @Transactional
    @Modifying
    @Query(value = "insert into ngo_request_updates (assignment_id,status,transaction_date,note,completed_request_longitude,completed_request_latitude,ngo_dog_image,ngo_dogs_count) values(:assignment_id,:status,:trans_date,:note,:completed_request_longitude,:completed_request_latitude,:ngo_dog_image,:ngo_dogs_count) ", nativeQuery = true)
    void insertRequestUpdate(@Param("assignment_id") int assignment_id, @Param("status") String status, @Param("trans_date") LocalDateTime trans_date, @Param("note") String note,@Param("completed_request_longitude") BigDecimal completed_request_longitude,@Param("completed_request_latitude") BigDecimal completed_request_latitude,@Param("ngo_dog_image") String ngo_dog_image,@Param("ngo_dogs_count") int ngo_dogs_count);

    @Modifying
    @Transactional
    @Query(value = "update requests set status = 'Mission Done' where request_id = (select distinct request_id from ngo_assignments where assignment_id = :assignment_id)", nativeQuery = true)
    void updateRequestStatus(@Param("assignment_id") int assignment_id);

    @Modifying
    @Transactional
    @Query(value = "update ngo_assignments set status = 'Mission Done' , action_date = now() where assignment_id = :assignment_id", nativeQuery = true)
    void updateRequestAssignmentStatus(@Param("assignment_id") int assignment_id);

    @Modifying
    @Transactional
    @Query(value = "update ngo_request_updates set Is_Active = 1 where assignment_id = :assignment_id", nativeQuery = true)
    void requestUpdateStatus(@Param("assignment_id") int assignment_id);

}
