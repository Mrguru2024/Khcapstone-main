package com.keycodehelp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "keycode_requests") // Ensure this matches your actual table name in the database
public class KeycodeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String vin;

    @Column(nullable = false)
    private LocalDateTime requestTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Constructor with LocalDateTime
    public KeycodeRequest(String vin, LocalDateTime requestTime, User user) {
        this.vin = vin;
        this.requestTime = requestTime;
        this.user = user;
    }

    // New constructor with User and auto-set current time
    public KeycodeRequest(String vin, User user) {
        this.vin = vin;
        this.requestTime = LocalDateTime.now(); // Initialize to the current time
        this.user = user;
    }

<<<<<<< HEAD
    // Default constructor
    public KeycodeRequest() {}
=======
    public KeycodeRequest(String vin123, java.time.LocalDateTime now, User user) {
    }

    @PrePersist
    protected void onRequest() {
        this.requestTime = LocalDateTime.now();
    }
>>>>>>> 6077084 (Added tailwind proper config and classes for basic front end use, Added Exception, Dev-prop, Prop-prop, input.css, style.css, added missing controllers, fragments folder, customized header, footer and navbar (ready to use as a component. Fixed looping issues in the path settings.))
}
