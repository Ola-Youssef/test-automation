package com.graduation_project.street2shelter.repository;

import com.graduation_project.street2shelter.entity.NgoDogInfo;
import com.graduation_project.street2shelter.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
    // @Query(value = "SELECT user_id FROM users where email like :email", nativeQuery = true)
    //  List<Users> Login(@Param("email") String email);
    //@Query(value = "SELECT 1 from dual" , nativeQuery = true)
    //Users Login();
    //Users login(String email,String password);
    //@Query(value="SELECT 1 FROM  Users WHERE Users.email = :email AND Users.password = :password", nativeQuery = true)
    //Users login(@Param("email") String email, @Param("password") String password);
    Users findByEmailAndPassword(String email, String password);

    Users findByEmailAndOtp(String email, int otp);

    Users findByEmail(String email);

    void deleteByEmail(String email);


    @Query(value = "SELECT substr(RAND()*100000,1,4) FROM Users WHERE Users.email = :email", nativeQuery = true)
    String searchOtp(@Param("email") String email);
}
