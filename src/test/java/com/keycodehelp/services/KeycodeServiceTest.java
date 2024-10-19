package com.keycodehelp.services;

import com.keycodehelp.entities.Keycode;
import com.keycodehelp.entities.KeycodeRequest;
import com.keycodehelp.entities.User;
import com.keycodehelp.repositories.KeycodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class KeycodeServiceTest {

    @Mock
    private KeycodeRepository keycodeRepository;

    @InjectMocks
    private KeycodeService keycodeService;

    private Keycode keycode;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        keycode = new Keycode();
        keycode.setId(1L);
        keycode.setKeycode("KEY-123");
        keycode.setCreatedAt(LocalDateTime.now());  // Use LocalDateTime
    }

    @Test
    void shouldSaveKeycode() {
        when(keycodeRepository.save(keycode)).thenReturn(keycode);

        Keycode savedKeycode = keycodeService.saveKeycode(keycode);
        assertEquals(keycode.getKeycode(), savedKeycode.getKeycode());
    }

    @Test
    void shouldFindKeycodeById() {
        when(keycodeRepository.findById(1L)).thenReturn(Optional.of(keycode));

        Optional<Keycode> foundKeycode = keycodeService.getKeycodeById(1L);

        // Ensure the keycode was found
        assertTrue(foundKeycode.isPresent());
        assertEquals(keycode.getKeycode(), foundKeycode.get().getKeycode());
    }

    @Test
    void shouldCreateKeycodeRequest() {
        // Set up a user
        User user = new User();
        user.setUsername("testUser");

        // Create a KeycodeRequest with LocalDateTime
        KeycodeRequest keycodeRequest = new KeycodeRequest("VIN123", LocalDateTime.now(), user);

        // Ensure the request object is created correctly
        assertEquals("VIN123", keycodeRequest.getVin());
        assertEquals(user, keycodeRequest.getUser());
    }
}
