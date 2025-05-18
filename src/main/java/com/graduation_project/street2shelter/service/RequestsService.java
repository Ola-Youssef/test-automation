package com.graduation_project.street2shelter.service;

import com.graduation_project.street2shelter.DTO.RequestUser;
import com.graduation_project.street2shelter.entity.Requests;
import com.graduation_project.street2shelter.repository.NgoAssignmentsRepo;
import com.graduation_project.street2shelter.repository.RequestsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RequestsService {
    @Autowired
    private RequestsRepo requestsRepo;
    @Autowired
    private NgoAssignmentsRepo ngoAssignmentsRepo;
    public List<Requests> findAllRequests() {
        return requestsRepo.findAll();
    }

    public RequestUser findRequestUser(int requestId) {
        return requestsRepo.getRequestUser(requestId);
    }


    public List<RequestUser> getInprogressRequests() {
        return requestsRepo.getInprogressRequests();
    }

    public List<RequestUser> getAcceptedRequests() {
        return requestsRepo.getAcceptedRequests();
    }

    public List<RequestUser> getMissionDoneRequests() {
        return requestsRepo.getMissionDoneRequests();
    }

    public Requests createRequest(Requests requests) {
        //String base64Image = Base64.getEncoder().encodeToString(requests.getDogImage().getBytes());
        //requests.setDogImage(base64Image);
        return requestsRepo.save(requests);
    }

    public Requests updateRequests(long id,Requests requests) {
        Optional<Requests> existingRequestOptional = requestsRepo.findById(id);
        if (existingRequestOptional.isPresent()) {
            Requests existingRequests = existingRequestOptional.get();
            existingRequests.setLatitude(requests.getLatitude());
            existingRequests.setLongitude(requests.getLongitude());
            //existingRequests.setStatus(requests.getStatus());
            existingRequests.setDescription(requests.getDescription());
            existingRequests.setDogImage(requests.getDogImage());
            //existingRequests.setSubmissionTime(requests.getSubmissionTime());

            return requestsRepo.save(existingRequests);

        } else {
            throw new RuntimeException("Request isn't found " );
        }
    }

    public List<Object[]> findNearestNgoForRequest(long requestId ){
        List<Object[]> nearNgo = requestsRepo.findNearestNgoForRequest(requestId);
        return nearNgo;
    }

    @Transactional
    public Optional<Requests> deleteRequest(Long id) {
        Optional<Requests> existingRequest = requestsRepo.findById(id);
        //try {
        if (existingRequest != null) {
            ngoAssignmentsRepo.deletNgoAssignment(Math.toIntExact(id));
            requestsRepo.deleteById(id);
            return existingRequest;
        } else {
            throw new RuntimeException("Request not found with id " + id);
        }
    }


}



