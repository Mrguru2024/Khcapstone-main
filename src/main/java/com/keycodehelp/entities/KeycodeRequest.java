package com.keycodehelp.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "keycode_requests")
public class KeycodeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String vin;

    @Column(nullable = false)
    private Date requestDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Constructors, Getters, and Setters

    public KeycodeRequest() {}

    public KeycodeRequest(String vin, Date requestDate, User user) {
        this.vin = vin;
        this.requestDate = requestDate;
        this.user = user;
    }

    // Getters and setters here...
}
