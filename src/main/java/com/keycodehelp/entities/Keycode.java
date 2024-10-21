package com.keycodehelp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "keycodes")
public class Keycode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String vin; // This matches the unique `vin` column in the table

    @Column(nullable = false)
    private String keycode; // This matches the `keycode` column

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // This matches the `created_at` column, stores datetime

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "keycodes_ibfk_1")) // Ensure foreign key name matches
    private User user; // This matches the `user_id` column, it’s a foreign key

    // Default constructor
    public Keycode() {}

    // Constructor for creating a Keycode with a generated keycode
    public Keycode(String vin, String keycode, User user) {
        this.vin = vin;
        this.keycode = keycode;
        this.user = user;
        this.createdAt = LocalDateTime.now(); // Automatically set createdAt to current time
    }

    // Constructor for creating a Keycode with a specified createdAt time
    public Keycode(String vin, String keycode, LocalDateTime createdAt, User user) {
        this.vin = vin;
        this.keycode = keycode;
        this.createdAt = createdAt; // Use provided createdAt time
        this.user = user;
    }

    // Automatically set createdAt before persisting if not already set
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now(); // Set createdAt to the current time
        }
    }
}