package com.keycodehelp.entities;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subscriptionType;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(nullable = false)
    private LocalDate startDate;

    // Each subscription belongs to one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Constructors
    public Subscription() {}

    public Subscription(String subscriptionType, LocalDate startDate, User user) {
        this.subscriptionType = subscriptionType;
        this.startDate = startDate;
        this.user = user;
    }

}
