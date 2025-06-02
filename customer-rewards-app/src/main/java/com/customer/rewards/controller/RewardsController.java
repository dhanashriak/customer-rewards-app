package com.customer.rewards.controller;

import com.customer.rewards.model.RewardSummary;
import com.customer.rewards.service.RewardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.constraints.NotBlank;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

/**
 * REST controller for calculating customer reward points.
 */
@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Customer Rewards", description = "REST API to calculate reward points for a customer")
public class RewardsController {

    private final RewardService rewardService;

    /**
     * Returns the reward summary for the given customer ID.
     *
     * @param customerId the ID of the customer
     * @return the reward summary including monthly and total points
     */
    @GetMapping("/{customerId}")
    @Operation(summary = "Get rewards by customer ID", description = "Retrieve monthly and total reward points for a customer.")
    public RewardSummary getRewards(@PathVariable @NotBlank String customerId) {
        log.info("Fetching rewards for customerId: {}", customerId);
        return rewardService.getRewardsByCustomer(customerId);
    }
}
