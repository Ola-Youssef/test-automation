package com.graduation_project.street2shelter.service;

import com.graduation_project.street2shelter.entity.NgoDogInfo;
import com.graduation_project.street2shelter.entity.Requests;
import com.graduation_project.street2shelter.entity.Users;
import com.graduation_project.street2shelter.repository.NgoDogInfoRepo;
import com.graduation_project.street2shelter.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NgoDogInfoService {
    @Autowired
    private NgoDogInfoRepo ngoDogInfoRepo ;
    public List<NgoDogInfo> findAllNgoDogInfo() {
        return ngoDogInfoRepo.findAll();
    }
    public NgoDogInfo findByRquestUpdatesId(int requestUpdatesId) {
        return ngoDogInfoRepo.findByNgoRequestUpdatesId(requestUpdatesId);
    }

    public NgoDogInfo createNgoDogInfo(NgoDogInfo ngoDogInfo) {
        return ngoDogInfoRepo.save(ngoDogInfo);
    }
}
