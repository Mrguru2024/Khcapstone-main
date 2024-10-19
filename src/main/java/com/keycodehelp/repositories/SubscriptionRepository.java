package com.keycodehelp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.keycodehelp.entities.Subscription;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    // Find subscriptions by user ID
    List<Subscription> findByUserId(Long userId);
}
