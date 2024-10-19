package com.keycodehelp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "keycodes")
public class Keycode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String vin;

    @Column(nullable = false)
    private String keycode;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Default constructor
    public Keycode() {
    }

    // Constructor for creating a Keycode with a generated keycode
    public Keycode(String vin, String keycode, User user) {
        this.vin = vin;
        this.keycode = keycode;
        this.user = user;
    }

    // Constructor for creating a Keycode with a specified createdAt time
    public Keycode(String vin, String keycode, LocalDateTime createdAt, User user) {
        this.vin = vin;
        this.keycode = keycode;
        this.createdAt = createdAt;  // Use provided createdAt time
        this.user = user;
    }

    // Automatically set createdAt before persisting
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // Set createdAt to the current time
    }
}
