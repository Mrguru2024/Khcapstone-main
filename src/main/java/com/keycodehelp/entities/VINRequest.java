package com.keycodehelp.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vin_requests")
@Getter
@Setter
public class VINRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String vin;

    @Column(nullable = false)
    private String keycodeResponse;

    @Column(nullable = false)
    private LocalDateTime requestTime;

    // Optional: Track the user who made the request
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Optional: Status of the request
    @Column(nullable = false)
    private String status;

    // Default Constructor
    public VINRequest() {}

    // Parameterized Constructor (if needed)
    public VINRequest(String vin, String keycodeResponse, LocalDateTime requestTime, User user, String status) {
        this.vin = vin;
        this.keycodeResponse = keycodeResponse;
        this.requestTime = requestTime;
        this.user = user;
        this.status = status;
    }
}
