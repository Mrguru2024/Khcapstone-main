package com.keycodehelp.controller;

import com.keycodehelp.entities.Keycode;
import com.keycodehelp.entities.KeycodeRequest;
import com.keycodehelp.entities.User;
import com.keycodehelp.services.KeycodeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class KeycodeControllerTest {

    @Autowired
    private KeycodeService keycodeService;

    @Mock
    private User user;  // Mock user for tests

    @InjectMocks
    private Keycode keycode;  // Inject mocks into keycode instance

    @Test
    public void testKeycodeCreation() {
        MockitoAnnotations.openMocks(this);
        // Sample data
        String vin = "1HGCM82633A123456";
        String generatedKeycode = "KEY-1HGCM82633A123456";

        // Create Keycode instance
        keycode = new Keycode(vin, generatedKeycode, LocalDateTime.now(), user); // Ensure the correct constructor is called

        // Perform assertions or save the keycode as needed
        assertNotNull(keycode);
        assertEquals(vin, keycode.getVin());
        assertEquals(generatedKeycode, keycode.getKeycode());
        assertEquals(user, keycode.getUser());
    }
}
