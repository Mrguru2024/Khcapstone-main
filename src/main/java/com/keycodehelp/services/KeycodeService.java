package com.keycodehelp.services;

import com.keycodehelp.entities.Keycode;
import com.keycodehelp.entities.KeycodeRequest;
import com.keycodehelp.entities.User;
import com.keycodehelp.repositories.KeycodeRepository;
import com.keycodehelp.repositories.KeycodeRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class KeycodeService {

    @Autowired
    private KeycodeRepository keycodeRepository;

    @Autowired
    private KeycodeRequestRepository keycodeRequestRepository;

    // Convert VIN to Keycode and log the request
    public Keycode convertVinToKeycode(String vin, User user) {
        String generatedKeycode = "KEY-" + vin.toUpperCase();

        // Create a new Keycode entity and save it
        Keycode keycode = new Keycode(vin, generatedKeycode, new Date(), user);
        keycodeRepository.save(keycode);

        // Log the request in KeycodeRequest
        KeycodeRequest request = new KeycodeRequest(vin, new Date(), user);
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
