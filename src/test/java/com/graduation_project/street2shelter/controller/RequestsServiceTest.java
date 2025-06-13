package com.graduation_project.street2shelter.controller;

import com.graduation_project.street2shelter.DTO.RequestUser ;
import com.graduation_project.street2shelter.entity.Requests;
import com.graduation_project.street2shelter.repository.NgoAssignmentsRepo;
import com.graduation_project.street2shelter.repository.RequestsRepo;
import com.graduation_project.street2shelter.service.RequestsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RequestsServiceTest {

    @InjectMocks
    private RequestsService requestsService;

    @Mock
    private RequestsRepo requestsRepo;

    @Mock
    private NgoAssignmentsRepo ngoAssignmentsRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testCreateRequest() {
        // Arrange
        Requests request = new Requests();
        request.setRequestId(1L);
        request.setLatitude(new BigDecimal("34.052235"));
        request.setLongitude(new BigDecimal("-118.243683"));
        request.setDescription("There are three dogs with superficial wounds" +
                " and two puppies that need urgent care.");
        request.setDogImage("base64EncodedImageString");
        request.setSubmissionTime(LocalDateTime.now());
        request.setStatus("Inprogress");
        request.setDogsCount(11);
        request.setStreetAddress("Cairo, Maadi , Nasr str");
        when(requestsRepo.save(any(Requests.class))).thenReturn(request);
        // Act
        Requests createdRequest = requestsService.createRequest(request);
        // Assert
        assertEquals(request, createdRequest);
        verify(requestsRepo, times(1)).save(request);
    }


    @Test
    public void testDeleteRequest() {
        // Arrange
        Requests existingRequest = new Requests();
        existingRequest.setRequestId(1L);
        when(requestsRepo.findById(1L)).thenReturn(Optional.of(existingRequest));

        // Act
        Optional<Requests> deletedRequest = requestsService.deleteRequest(1L);

        // Assert
        assertTrue(deletedRequest.isPresent());
        assertEquals(existingRequest, deletedRequest.get());
        verify(requestsRepo, times(1)).deleteById(1L);
    }


}
