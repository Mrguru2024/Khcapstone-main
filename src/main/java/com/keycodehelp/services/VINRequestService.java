package com.keycodehelp.services;

import com.keycodehelp.entities.VINRequest;
import com.keycodehelp.exception.ResourceNotFoundException;
import com.keycodehelp.repositories.VINRequestRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/vinrequest")
@Service
public class VINRequestService {

    private final VINRequestRepository vinRequestRepository;

    @Autowired
    public VINRequestService(VINRequestRepository vinRequestRepository) {
        this.vinRequestRepository = vinRequestRepository;
    }

    public List<VINRequest> getAllVINRequests() {
        return vinRequestRepository.findAll();
    }

    public VINRequest getVINRequestById(Long id) {
        return vinRequestRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("VINRequest not found with id: " + id));
    }

    public VINRequest createVINRequest(VINRequest vinRequest) {
        return vinRequestRepository.save(vinRequest);
    }

    public VINRequest updateVINRequest(Long id, VINRequest vinRequestDetails) {
        VINRequest vinRequest = vinRequestRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("VINRequest not found with id: " + id));
        
        // Update fields based on vinRequestDetails
        vinRequest.setVin(vinRequestDetails.getVin());
        vinRequest.setKeycodeResponse(vinRequestDetails.getKeycodeResponse());
        vinRequest.setRequestTime(vinRequestDetails.getRequestTime());

        return vinRequestRepository.save(vinRequest);
    }

    public void deleteVINRequest(Long id) {
        VINRequest vinRequest = vinRequestRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("VINRequest not found with id: " + id));
        vinRequestRepository.delete(vinRequest);
    }
}
