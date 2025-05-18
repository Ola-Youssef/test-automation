package com.graduation_project.street2shelter.repository;
import com.graduation_project.street2shelter.entity.Ngos;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NgosRepo extends JpaRepository<Ngos, Long> {

    Ngos findByEmailAndPassword(String email, String password);

    @Query(value = "SELECT * FROM Ngos WHERE Ngos.email = :email and  Ngos.password = :password ", nativeQuery = true)
    Ngos getLoginByEmailAndPassword(@Param("email") String email,@Param("password") String password);

    Ngos findByEmailAndOtp(String email, int otp);

    Ngos findByEmail(String email);

    @Query(value = "SELECT * FROM Ngos WHERE Ngos.email = :email ", nativeQuery = true)
    Ngos getByEmail(@Param("email") String email);

    void deleteByEmail(String email);

    @Query(value = "SELECT substr(RAND()*100000,1,4) FROM Ngos WHERE Ngos.email = :email", nativeQuery = true)
    String searchOtp(@Param("email") String email);

    @Query(value = "SELECT * FROM Ngos WHERE approved_ngo = 0", nativeQuery = true)
    List<Ngos> findAllNgoNotApproved();

    @Modifying
    @Transactional
    @Query(value = "update Ngos set approved_ngo = 1 where email = :email", nativeQuery = true)
    void approveNgo(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "update Ngos set is_active = :isActive where email = :email", nativeQuery = true)
    void isActiveNgo(@Param("email") String email,@Param("isActive") int isActive);


}
