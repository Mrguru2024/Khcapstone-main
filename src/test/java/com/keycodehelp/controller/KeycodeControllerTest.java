package com.keycodehelp.controller;

import com.keycodehelp.config.TestSecurityConfig;
import com.keycodehelp.entities.Keycode;
import com.keycodehelp.security.JwtRequestFilter;
import com.keycodehelp.security.JwtUtil;
import com.keycodehelp.services.KeycodeService;
import com.keycodehelp.services.UserService;
import io.jsonwebtoken.Jwt;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")  // To load application-test.properties
@ContextConfiguration(classes = {TestSecurityConfig.class, Jwt.class}) // Use TestSecurityConfig to bypass security

public class KeycodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KeycodeService keycodeService;

    @Setter
    @Getter
    @MockBean
    private UserService userService;  // Add the missing UserService Mock

    @Setter
    @Getter
    @MockBean
    private JwtUtil jwtUtil;  // Mock JwtUtil to avoid actual token validation during tests
    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @Test
    public void shouldSaveKeycode() throws Exception {
        Keycode keycode = new Keycode("VIN123", "KEY-123", new Date(), null);
        Mockito.when(keycodeService.saveKeycode(Mockito.any(Keycode.class))).thenReturn(keycode);

        mockMvc.perform(post("/api/keycode/convert")
                        .param("vin", "VIN123"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetAllKeycodes() throws Exception {
        Mockito.when(keycodeService.getAllKeycodes()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/keycode/history"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetKeycodeById() throws Exception {
        Keycode keycode = new Keycode("VIN123", "KEY-123", new Date(), null);
        Mockito.when(keycodeService.getKeycodeById(1L)).thenReturn(Optional.of(keycode));

        mockMvc.perform(get("/api/keycode/1"))
                .andExpect(status().isOk());
    }

}
