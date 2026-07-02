package com.saumya.projects.lovable_clone.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class Plan {
    Long id;
    String name;
    String stripePriceId; // so stripe has purchased plan info
    Integer maxProjects;
    Integer maxTokensPerDay;
    Integer maxPreviews; // max number of previews allowed
    Boolean unlimitedAi; // unlimited AI access, ignore maxTokensPerDay
    Boolean active;
}
