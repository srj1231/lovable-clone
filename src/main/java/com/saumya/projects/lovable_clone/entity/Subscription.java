package com.saumya.projects.lovable_clone.entity;

import com.saumya.projects.lovable_clone.enums.SubscriptionStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class Subscription {
    Long id; // PK

    User user;  // FK user_id
    Plan plan;  // FK plan_id

    String stripeSubscriptionId;    // UK stripe_subscription_id

    SubscriptionStatus status;

    Instant currentPeriodStart;
    Instant currentPeriodEnd;
    Boolean canceledAtPeriodEnd = false;

    Instant updatedAt;
    Instant createdAt;
}

// Hibernate takes care of mapping between the database and the entity from the ER diagram and the annotations like ManyToOne, OneToMany, etc.
