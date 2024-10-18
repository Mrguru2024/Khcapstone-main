package com.keycodehelp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Role {

    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;  // e.g., ROLE_USER or ROLE_ADMIN

    // Constructors
    public Role() {}

    public Role(String name) {
        this.name = name;
    }

}
