package com.graduation_project.street2shelter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.graduation_project.street2shelter.entity.Users;
import com.graduation_project.street2shelter.repository.UsersRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule()); // Ensure LocalDateTime serialization
        usersRepo.deleteAll(); // Clean up repository before each test
    }

    @Test
    public void testSignup() throws Exception {
        Users newUser = new Users();
        newUser.setFirstName("Ola");
        newUser.setLastName("Youssef");
        newUser.setEmail("olayoussefmohammed@gmail.com");
        newUser.setPassword("passWord123$"); // If using password encryption, adjust accordingly
        newUser.setPhoneNumber(1234567890);
        newUser.setDateJoined(LocalDateTime.now());

        mockMvc.perform(post("/users/createuser") // Ensure this matches actual endpoint in controller
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("olayoussefmohammed@gmail.com"));
    }

    @Test
    public void testLoginSuccess() throws Exception {
        // First, create a user
        Users newUser = new Users();
        newUser.setFirstName("Ola");
        newUser.setLastName("Youssef");
        newUser.setEmail("olayoussefmohammed@gmail.com");
        newUser.setPassword("passWord123$"); // If encryption is used, encode accordingly
        newUser.setPhoneNumber(1234567890);
        newUser.setDateJoined(LocalDateTime.now());
        usersRepo.save(newUser);

        // Now, attempt to log in
        mockMvc.perform(get("/users/login")
                        .param("email", "olayoussefmohammed@gmail.com")
                        .param("password", "passWord123$"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("olayoussefmohammed@gmail.com"));
    }

    @Test
    public void testLoginFailure() throws Exception {
        mockMvc.perform(get("/users/login")
                        .param("email", "nonexistent@example.com")
                        .param("password", "wrongpassword"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("The user not found"));
    }
}