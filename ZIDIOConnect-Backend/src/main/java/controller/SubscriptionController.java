package com.zidioconnect.controller;

import com.zidioconnect.model.Subscription;
import com.zidioconnect.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
@CrossOrigin(origins = "*")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    // 👉 Create a new subscription
    @PostMapping("/create")
    public ResponseEntity<Subscription> createSubscription(@RequestParam String email,
                                                           @RequestParam int days) {
        Subscription sub = subscriptionService.createSubscription(email, days);
        return ResponseEntity.ok(sub);
    }

    // 👉 Check subscription status
    @GetMapping("/status")
    public ResponseEntity<Boolean> checkSubscription(@RequestParam String email) {
        boolean active = subscriptionService.isSubscriptionActive(email);
        return ResponseEntity.ok(active);
    }
}
