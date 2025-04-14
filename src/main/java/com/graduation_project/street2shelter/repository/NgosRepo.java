package com.graduation_project.street2shelter.repository;
import com.graduation_project.street2shelter.entity.Ngos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NgosRepo extends JpaRepository<Ngos, Long> {

    Ngos findByEmailAndPassword(String email, String password);

    Ngos findByEmailAndOtp(String email, int otp);

    Ngos findByEmail(String email);

    void deleteByEmail(String email);

    @Query(value = "SELECT substr(RAND()*100000,1,4) FROM Ngos WHERE Ngos.email = :email", nativeQuery = true)
    String searchOtp(@Param("email") String email);



}
