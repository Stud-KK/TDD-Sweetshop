package com.incubyte.sweetshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    static class RegisterRequest {
        public String name;
        public String email;
        public String password;

        RegisterRequest(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }
    }

    @Test
    void registerUser_shouldReturn201() throws Exception {
        RegisterRequest request = new RegisterRequest(
                "Komal",
                "komal@example.com",
                "Password123"
        );

        mvc.perform(
                        post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated());
    }

    //Missing request body
    @Test
    void registerUser_withoutRequestBody_shouldReturn400() throws Exception {
        mvc.perform(
                        post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }
    //Invalid email
    @Test
    void registerUser_withInvalidEmail_shouldReturn400() throws Exception {
        RegisterRequest request = new RegisterRequest(
                "Komal",
                "invalid-email",
                "Password123"
        );

        mvc.perform(
                        post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());
    }
    //empty password
    @Test
    void registerUser_withEmptyPassword_shouldReturn400() throws Exception {
        RegisterRequest request = new RegisterRequest(
                "Komal",
                "komal@example.com",
                ""
        );

        mvc.perform(
                        post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());
    }


}
