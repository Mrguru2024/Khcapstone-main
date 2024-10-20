package com.keycodehelp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "keycode_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeycodeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String vin;

    @Column(nullable = false)
    private LocalDateTime requestTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public KeycodeRequest(String vin, User user) {
    }

    public KeycodeRequest(String vin123, LocalDateTime now, User user) {
    }

    @PrePersist
    protected void onRequest() {
        this.requestTime = LocalDateTime.now();
    }
}
