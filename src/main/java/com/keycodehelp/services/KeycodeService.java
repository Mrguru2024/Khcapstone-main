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

    public KeycodeService(KeycodeRepository keycodeRepository, KeycodeRequestRepository keycodeRequestRepository) {
        this.keycodeRepository = keycodeRepository;
        this.keycodeRequestRepository = keycodeRequestRepository;
    }

    // Convert VIN to Keycode and log the request
    public Keycode convertVinToKeycode(String vin, User user) {
        Optional<Keycode> existingKeycode = keycodeRepository.findByVin(vin);

        if (existingKeycode.isPresent()) {
            // Return existing keycode if already generated
            return existingKeycode.get();
        }

        // Otherwise, generate a new keycode
        String generatedKeycode = "KEY-" + vin.toUpperCase();
        Keycode keycode = new Keycode(vin, generatedKeycode, user);
        keycodeRepository.save(keycode);

        // Log the keycode request
        KeycodeRequest request = new KeycodeRequest(vin, user);
        keycodeRequestRepository.save(request);

        return keycode;
    }

    // Fetch request history for a specific user
    public List<KeycodeRequest> getKeycodeHistory(User user) {
        return keycodeRequestRepository.findByUserId(user.getId());
    }

    public List<Keycode> getAllKeycodes() {
        return keycodeRepository.findAll();
    }

    public Optional<Keycode> getKeycodeById(Long id) {
        return keycodeRepository.findById(id);
    }

    public Keycode saveKeycode(Keycode keycode) {
        return keycode;
    }
}
