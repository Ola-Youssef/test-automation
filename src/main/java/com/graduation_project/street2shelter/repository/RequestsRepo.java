package com.graduation_project.street2shelter.repository;

import com.graduation_project.street2shelter.DTO.RequestUser;
import com.graduation_project.street2shelter.entity.Requests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestsRepo extends JpaRepository<Requests, Long> {


    @Query(value = "SELECT request_id,requests.user_id,status,longitude,latitude,submission_time,description,dog_image,dogs_count,street_address,CONCAT(first_name,' ',last_name) user_name,phone_number,\n" +
            "(select name from ngo_assignments,ngos where ngo_assignments.request_id = requests.request_id and status = \"Accepted\" and is_actve =0 and ngos.ngo_id = ngo_assignments.ngo_id and ngo_assignments.request_id = :requestId ) accepted_ngo,\n" +
            "(select transaction_date from ngo_assignments,ngo_request_updates where ngo_assignments.request_id = requests.request_id and ngo_assignments.status = \"Accepted\" and ngo_assignments.is_actve =0 and ngo_request_updates.assignment_id = ngo_assignments.assignment_id and ngo_assignments.request_id = :requestId) accepted_date,\n" +
            "(select name from ngo_assignments,ngos where ngo_assignments.request_id = requests.request_id and ngo_assignments.status = \"Mission Done\" and ngo_assignments.is_actve =0 and ngos.ngo_id = ngo_assignments.ngo_id and ngo_assignments.request_id = :requestId ) mission_done_ngo\n" +
            ",(select ngo_request_updates.transaction_date from ngo_assignments,ngo_request_updates where ngo_assignments.request_id = requests.request_id and ngo_assignments.status = \"Mission Done\" and ngo_assignments.is_actve =0 and ngo_request_updates.assignment_id = ngo_assignments.assignment_id and ngo_request_updates.status = \"Mission Done\" and ngo_assignments.request_id = :requestId) mission_done_date\n" +
            "FROM street2shelter.requests, users\n" +
            "where users.user_id = requests.user_id  and request_id = :requestId ", nativeQuery = true)
    RequestUser getRequestUser(@Param("requestId") int requestId);

//    @Query(value = "SELECT * FROM street2shelter.requests where status = 'Inprogress' ", nativeQuery = true)
@Query(value = "SELECT request_id,requests.user_id,status,longitude,latitude,submission_time,description,dog_image,dogs_count,street_address,CONCAT(first_name,' ',last_name) user_name,phone_number," +
        "null as accepted_ngo,null as accepted_date,null as mission_done_ngo,null as mission_done_date \n" +
        "FROM street2shelter.requests, users\n" +
        "where users.user_id = requests.user_id  and status = 'Inprogress' ", nativeQuery = true)
   List<RequestUser> getInprogressRequests();

    //@Query(value = "SELECT * FROM street2shelter.requests where status = 'Accepted' ", nativeQuery = true)
    @Query(value = "SELECT request_id,requests.user_id,status,longitude,latitude,submission_time,description,dog_image,dogs_count,street_address,CONCAT(first_name,' ',last_name) user_name,phone_number,\n" +
            "(select name from ngo_assignments,ngos where ngo_assignments.request_id = requests.request_id and status = \"Accepted\" and is_actve =0 and ngos.ngo_id = ngo_assignments.ngo_id ) accepted_ngo,\n" +
            "(select transaction_date from ngo_assignments,ngo_request_updates where ngo_assignments.request_id = requests.request_id and ngo_assignments.status = \"Accepted\" and ngo_assignments.is_actve =0 and ngo_request_updates.assignment_id = ngo_assignments.assignment_id ) accepted_date,\n" +
            "(select name from ngo_assignments,ngos where ngo_assignments.request_id = requests.request_id and ngo_assignments.status = \"Mission Done\" and ngo_assignments.is_actve =0 and ngos.ngo_id = ngo_assignments.ngo_id ) mission_done_ngo\n" +
            ",(select ngo_request_updates.transaction_date from ngo_assignments,ngo_request_updates where ngo_assignments.request_id = requests.request_id and ngo_assignments.status = \"Mission Done\" and ngo_assignments.is_actve =0 and ngo_request_updates.assignment_id = ngo_assignments.assignment_id and ngo_request_updates.status = \"Mission Done\" ) mission_done_date\n" +
            "FROM street2shelter.requests, users\n" +
            "where users.user_id = requests.user_id  and status = 'Accepted' ", nativeQuery = true)
    List<RequestUser> getAcceptedRequests();

    //@Query(value = "SELECT * FROM street2shelter.requests where status = 'Mission Done' ", nativeQuery = true)
    @Query(value = "SELECT request_id,requests.user_id,status,longitude,latitude,submission_time,description,dog_image,dogs_count,street_address,CONCAT(first_name,' ',last_name) user_name,phone_number,\n" +
            "(select name from ngo_assignments,ngos where ngo_assignments.request_id = requests.request_id and status = \"Accepted\" and is_actve =0 and ngos.ngo_id = ngo_assignments.ngo_id ) accepted_ngo,\n" +
            "(select transaction_date from ngo_assignments,ngo_request_updates where ngo_assignments.request_id = requests.request_id and ngo_assignments.status = \"Accepted\" and ngo_assignments.is_actve =0 and ngo_request_updates.assignment_id = ngo_assignments.assignment_id ) accepted_date,\n" +
            "(select name from ngo_assignments,ngos where ngo_assignments.request_id = requests.request_id and ngo_assignments.status = \"Mission Done\" and ngo_assignments.is_actve =0 and ngos.ngo_id = ngo_assignments.ngo_id ) mission_done_ngo\n" +
            ",(select ngo_request_updates.transaction_date from ngo_assignments,ngo_request_updates where ngo_assignments.request_id = requests.request_id and ngo_assignments.status = \"Mission Done\" and ngo_assignments.is_actve =0 and ngo_request_updates.assignment_id = ngo_assignments.assignment_id and ngo_request_updates.status = \"Mission Done\" ) mission_done_date\n" +
            "FROM street2shelter.requests, users\n" +
            "where users.user_id = requests.user_id  and status = 'Mission Done' ", nativeQuery = true)
    List<RequestUser> getMissionDoneRequests();

    /*  @Query(value = "SELECT ngo_id,haversine_distance(:newlatitude, :newlongitude, :latitude, :longitude) " +
                "AS distance FROM Ngos order by distance asc LIMIT 1",nativeQuery = true)*/
    @Query(value = "SELECT distinct n.ngo_id AS ngoId, (select response_daedline_days from rules where rules_id = 3) AS daedlineDays ,(select extra_time_request_count from rules where rules_id = 3) AS extraTimeNumber ," +
            " (6371 * acos( cos( radians(r.latitude) ) * cos( radians(n.latitude) ) " +
            " * cos( radians(n.longitude) - radians(r.longitude) ) + sin( radians(r.latitude) ) " +
            " * sin( radians(n.latitude) ) ) ) AS distance " +
            "FROM ngos n " +
            "CROSS JOIN requests r " +
            "WHERE r.request_id = :requestId " +
            "ORDER BY distance ASC " +
            "LIMIT 3", nativeQuery = true)
    List<Object[]> findNearestNgoForRequest(@Param("requestId") Long requestId);

    //(select response_daedline_days from rules where rules_id = 3) AS daedlineDays ,

}
