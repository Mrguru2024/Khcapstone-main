package com.keycodehelp.services;

import com.keycodehelp.entities.KeycodeRequest;
import com.keycodehelp.exception.ResourceNotFoundException;
import com.keycodehelp.repositories.KeycodeRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeycodeRequestService {

    private final KeycodeRequestRepository keycodeRequestRepository;

    // Constructor-based dependency injection
    public KeycodeRequestService(KeycodeRequestRepository keycodeRequestRepository) {
        this.keycodeRequestRepository = keycodeRequestRepository;
    }

    // Get all Keycode requests
    public List<KeycodeRequest> getAllKeycodeRequests() {
        return keycodeRequestRepository.findAll();
    }

    // Get Keycode request by ID
    public KeycodeRequest getKeycodeRequestById(Long id) {
        return keycodeRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("KeycodeRequest not found with id: " + id));
    }

    // Create a new Keycode request
    public KeycodeRequest createKeycodeRequest(KeycodeRequest keycodeRequest) {
        return keycodeRequestRepository.save(keycodeRequest);
    }

    // Update an existing Keycode request
    public KeycodeRequest updateKeycodeRequest(Long id, KeycodeRequest keycodeRequestDetails) {
        KeycodeRequest keycodeRequest = keycodeRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("KeycodeRequest not found with id: " + id));

        keycodeRequest.setVin(keycodeRequestDetails.getVin());
        keycodeRequest.setRequestTime(keycodeRequestDetails.getRequestTime());
        keycodeRequest.setUser(keycodeRequestDetails.getUser());

        return keycodeRequestRepository.save(keycodeRequest);
    }

    // Delete Keycode request by ID
    public void deleteKeycodeRequest(Long id) {
        KeycodeRequest keycodeRequest = keycodeRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("KeycodeRequest not found with id: " + id));
        keycodeRequestRepository.delete(keycodeRequest);
    }
}
