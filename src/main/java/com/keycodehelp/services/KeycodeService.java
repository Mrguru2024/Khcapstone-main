package com.keycodehelp.services;

import com.keycodehelp.entities.Keycode;
import com.keycodehelp.entities.KeycodeRequest;
import com.keycodehelp.entities.User;
import com.keycodehelp.repositories.KeycodeRepository;
import com.keycodehelp.repositories.KeycodeRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class KeycodeService {

    private final KeycodeRepository keycodeRepository;
    private final KeycodeRequestRepository keycodeRequestRepository;

    @Autowired
    public KeycodeService(KeycodeRepository keycodeRepository, KeycodeRequestRepository keycodeRequestRepository) {
        this.keycodeRepository = keycodeRepository;
        this.keycodeRequestRepository = keycodeRequestRepository;

    }
    // Fetch the keycode by VIN
    public Keycode getKeycodeByVin(String vin) {
        return keycodeRepository.findByVin(vin);
    }
    // Create a keycode request
    public KeycodeRequest createKeycodeRequest(String vin, User user) {
        KeycodeRequest keycodeRequest = new KeycodeRequest(vin, LocalDateTime.now(), user);
        return keycodeRequestRepository.save(keycodeRequest);
    }

    // Generate keycode for a VIN
    public Keycode convertVinToKeycode(String vin, User user) {
        Keycode keycode = new Keycode(vin, "GeneratedKeycode_" + vin, user); // Simplified keycode generation
        return keycodeRepository.save(keycode);
    }

    // Retrieve keycode request history for a user
    public List<KeycodeRequest> getKeycodeHistory(User user) {
        return keycodeRequestRepository.findAllByUser(user); // Custom query in KeycodeRequestRepository
    }

    // Retrieve all keycode requests (admin feature)
    public List<KeycodeRequest> getAllKeycodeRequests() {
        return keycodeRequestRepository.findAll();
    }

    // Delete a keycode request
    public void deleteKeycodeRequest(Long id) {
        keycodeRequestRepository.deleteById(id);
    }
}
