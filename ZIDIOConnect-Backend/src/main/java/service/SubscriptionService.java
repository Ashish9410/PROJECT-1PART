package com.zidioconnect.service;

import com.zidioconnect.model.Subscription;
import com.zidioconnect.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription createSubscription(String userEmail, int durationInDays) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(durationInDays);

        Subscription subscription = new Subscription();
        subscription.setUserEmail(userEmail);
        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);

        return subscriptionRepository.save(subscription);
    }

    public boolean isSubscriptionActive(String userEmail) {
        Optional<Subscription> subscriptionOpt = subscriptionRepository.findByUserEmail(userEmail);
        if (subscriptionOpt.isPresent()) {
            Subscription sub = subscriptionOpt.get();
            LocalDate today = LocalDate.now();
            return (today.isEqual(sub.getStartDate()) || today.isAfter(sub.getStartDate()))
                    && today.isBefore(sub.getEndDate());
        }
        return false;
    }
}
