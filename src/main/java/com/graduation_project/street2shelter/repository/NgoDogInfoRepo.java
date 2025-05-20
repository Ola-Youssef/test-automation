package com.graduation_project.street2shelter.repository;

import com.graduation_project.street2shelter.entity.NgoDogInfo;
import com.graduation_project.street2shelter.entity.Ngos;
import com.graduation_project.street2shelter.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NgoDogInfoRepo extends JpaRepository<NgoDogInfo, Long> {

    NgoDogInfo findByNgoRequestUpdatesId(int requestUpdatesId);







}
