package com.keycodehelp.controller;

import com.keycodehelp.entities.KeycodeRequest;
import com.keycodehelp.services.KeycodeRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;  // Ensure you're importing this for validation

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/keycode-requests")
@CrossOrigin(origins = "http://localhost:3000")  // Adjust frontend URL if needed
public class KeycodeRequestController {

    private final KeycodeRequestService keycodeRequestService;

    public KeycodeRequestController(KeycodeRequestService keycodeRequestService) {
        this.keycodeRequestService = keycodeRequestService;
    }

    // Retrieve all Keycode Requests
    @GetMapping("/keycode-request")
    public List<KeycodeRequest> getAllKeycodeRequests() {
        return keycodeRequestService.getAllKeycodeRequests();
    }

    // Retrieve Keycode Request by ID
    @GetMapping("/keycode-request/{id}")
    public ResponseEntity<KeycodeRequest> getKeycodeRequestById(@PathVariable Long id) {
        KeycodeRequest keycodeRequest = keycodeRequestService.getKeycodeRequestById(id);
        return ResponseEntity.ok(keycodeRequest);
    }

    // Create a new Keycode Request
    @PostMapping("/keycode-requests")
    public ResponseEntity<KeycodeRequest> createKeycodeRequest(@Valid @RequestBody KeycodeRequest keycodeRequest) {
        KeycodeRequest newKeycodeRequest = keycodeRequestService.createKeycodeRequest(keycodeRequest);
        URI location = URI.create("/api/keycode-requests/" + newKeycodeRequest.getId());  // Use the ID of the created resource
        return ResponseEntity.created(location).body(newKeycodeRequest);  // Return 201 Created with the location
    }

    // Update an existing Keycode Request
    @PutMapping("/keycode-requests/{id}")
    public ResponseEntity<KeycodeRequest> updateKeycodeRequest(@PathVariable Long id, @Valid @RequestBody KeycodeRequest keycodeRequestDetails) {
        KeycodeRequest updatedKeycodeRequest = keycodeRequestService.updateKeycodeRequest(id, keycodeRequestDetails);
        return ResponseEntity.ok(updatedKeycodeRequest);
    }

    // Delete a Keycode Request by ID
    @DeleteMapping("/keycode-requests/{id}")
    public ResponseEntity<Void> deleteKeycodeRequest(@PathVariable Long id) {
        keycodeRequestService.deleteKeycodeRequest(id);
        return ResponseEntity.noContent().build();
    }
}
