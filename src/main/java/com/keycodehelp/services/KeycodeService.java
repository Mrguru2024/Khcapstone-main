package com.keycodehelp.services;

import com.keycodehelp.entities.Keycode;
import com.keycodehelp.entities.KeycodeRequest;
import com.keycodehelp.entities.User;
import com.keycodehelp.repositories.KeycodeRepository;
import com.keycodehelp.repositories.KeycodeRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KeycodeService {

    private final KeycodeRepository keycodeRepository;
    private final KeycodeRequestRepository keycodeRequestRepository;

    // Constructor-based injection
    public KeycodeService(KeycodeRepository keycodeRepository, KeycodeRequestRepository keycodeRequestRepository) {
        this.keycodeRepository = keycodeRepository;
        this.keycodeRequestRepository = keycodeRequestRepository;
    }

    // Convert VIN to Keycode and log the request
    public Keycode convertVinToKeycode(String vin, User user) {
        // Generate the keycode
        String generatedKeycode = "KEY-" + vin.toUpperCase();

        // Create a new Keycode entity
        Keycode keycode = new Keycode(vin, generatedKeycode, user); // Use the constructor with three parameters
        keycodeRepository.save(keycode);

        // Log the request in KeycodeRequest
        KeycodeRequest request = new KeycodeRequest(vin, user);  // LocalDateTime is set automatically
        keycodeRequestRepository.save(request);

        return keycode;
    }

    // Get keycode request history for the user
    public List<KeycodeRequest> getKeycodeHistory(User user) {
        return keycodeRequestRepository.findByUserId(user.getId());
    }

    // Save a Keycode entity
    public Keycode saveKeycode(Keycode keycode) {
        return keycodeRepository.save(keycode);
    }

    // Retrieve all Keycode entities
    public List<Keycode> getAllKeycodes() {
        return keycodeRepository.findAll();
    }

    // Retrieve a Keycode entity by its ID
    public Optional<Keycode> getKeycodeById(Long id) {
        return keycodeRepository.findById(id);
    }
}
