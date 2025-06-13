package com.graduation_project.street2shelter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graduation_project.street2shelter.entity.Ngos;
import com.graduation_project.street2shelter.repository.NgosRepo;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional; // Import Transactional
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NgosControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NgosRepo ngosRepository;

    @Autowired
    private EntityManager entityManager; // Inject EntityManager

    @BeforeEach
    public void setup() {
        // Clear the repository to ensure a clean state.
        ngosRepository.deleteAll();
    }

    @Test
    @Transactional // Add Transactional annotation
    public void testSignupAndLoginFlow() throws Exception {
        // Create a new NGO
        Ngos ngo = new Ngos();
        ngo.setName("TNR Maadi");
        ngo.setEmail("TNRMaadi@gamil.com");
        ngo.setPassword("Pa$sword12");
        ngo.setPhoneNumber(1020335018);
        // Set dummy values for other required fields.
        ngo.setLatitude(BigDecimal.valueOf(30.0444));
        ngo.setLongitude(BigDecimal.valueOf(31.2357));
        ngo.setAddress("Cairo,Maadi ,Nasr st");

        ngo.setDateJoined(LocalDateTime.now());
        ngo.setOtp(null); // or set an appropriate dummy OTP if your flow requires it

        // Perform the signup POST request.
        mockMvc.perform(post("/ngos/createngos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ngo)))
                .andExpect(status().isCreated());

        entityManager.flush(); // Force the changes to be written to the database
        entityManager.clear(); // Clear the persistence context

        // Now perform a login GET request with the same email and password.
        mockMvc.perform(get("/ngos/login")
                        .param("email", "TNRMaadi@gamil.com")
                        .param("password", "Pa$sword12"))
                .andExpect(status().isOk())
                // Validate that the response JSON contains the expected email and phone number.
                .andExpect(jsonPath("$.email").value("TNRMaadi@gamil.com"))
                .andExpect(jsonPath("$.phoneNumber").value(1020335018));
    }
}