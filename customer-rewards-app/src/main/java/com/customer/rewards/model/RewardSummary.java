package com.customer.rewards.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;
import java.util.Map;

/**
 * Represents a summary of reward points earned by a customer,
 * broken down by month and including a total.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RewardSummary {

    private String customerId;
    private Map<Month, Integer> monthlyPoints;
    private int totalPoints;
}
