package com.graduation_project.street2shelter.controller;

import com.graduation_project.street2shelter.entity.NgoAssignments;
import com.graduation_project.street2shelter.entity.NgoRequestedExtraTime;
import com.graduation_project.street2shelter.service.NgoAssignmentsService;
import com.graduation_project.street2shelter.service.NgoRequestedExtraTimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class NgoRequestedExtraTimeControllerTest {

    @InjectMocks
    private NgoRequestedExtraTimeController ngoRequestedExtraTimeController;

    @Mock
    private NgoRequestedExtraTimeService ngoRequestedExtraTimeService;

    @Mock
    private NgoAssignmentsService ngoAssignmentsService;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    //
    @Test
    public void testCreateExtraTime_ValidRequest() {
        // Arrange
        NgoRequestedExtraTime extraTimeRequest = new NgoRequestedExtraTime();
        extraTimeRequest.setAssignmentId(1L);
        extraTimeRequest.setExtraNeededDays(3);
        NgoAssignments ngoAssignments = new NgoAssignments();
        ngoAssignments.setNumberNeededExtraTime(5); // Enough extra time available
        ngoAssignments.setNumberExtraTimeUsed(2); // Already used 2 days
        when(bindingResult.hasErrors()).thenReturn(false);
        when(ngoAssignmentsService.findAcceptedAssignmentRequest(1)).thenReturn(ngoAssignments);
        when(ngoRequestedExtraTimeService.findCountExtraTime(1)).thenReturn(0); // No previous requests
        when(ngoRequestedExtraTimeService.createextratime(anyInt(), anyInt(), any(Timestamp.class))).thenReturn(1);
        // Act
        ResponseEntity<?> response = ngoRequestedExtraTimeController.createextratime(extraTimeRequest, bindingResult);
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("The time has extended"));
    }



        @Test
    public void testCreateExtraTime_NoExtraTimeAvailable() {
        // Arrange
        NgoRequestedExtraTime extraTimeRequest = new NgoRequestedExtraTime();
        extraTimeRequest.setAssignmentId(1L);
        extraTimeRequest.setExtraNeededDays(3);

        NgoAssignments ngoAssignments = new NgoAssignments();
        ngoAssignments.setNumberNeededExtraTime(2);
        ngoAssignments.setNumberExtraTimeUsed(2);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(ngoAssignmentsService.findAcceptedAssignmentRequest(1)).thenReturn(ngoAssignments);

        // Act
        ResponseEntity<?> response = ngoRequestedExtraTimeController.createextratime(extraTimeRequest, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("No extra time available"));
    }

    @Test
    public void testCreateExtraTime_ValidationError() {
        // Arrange
        NgoRequestedExtraTime extraTimeRequest = new NgoRequestedExtraTime();
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        ResponseEntity<?> response = ngoRequestedExtraTimeController.createextratime(extraTimeRequest, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        // You can add more assertions to check the validation error response if needed
    }
}
