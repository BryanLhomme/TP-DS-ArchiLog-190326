package com.archilog.memberservice.dto;

import com.archilog.memberservice.model.SubscriptionType;

public record CreateMemberDTO(
        String fullName,
        String email,
        SubscriptionType subscriptionType
) {}
