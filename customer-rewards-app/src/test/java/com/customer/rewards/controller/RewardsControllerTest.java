package com.customer.rewards.controller;

import com.customer.rewards.model.RewardSummary;
import com.customer.rewards.service.RewardService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Integration-style test for {@link RewardsController} using Spring context.
 */
@SpringBootTest
class RewardsControllerTest {

    @Autowired
    private RewardsController rewardsController;

    @MockBean
    private RewardService rewardService;

    /**
     * Should return reward summary when a valid customer ID is provided.
     */
    @Test
    void shouldReturnRewardSummary_WhenCustomerIdIsValid() {
        // Arrange
        String customerId = "CUST123";
        Map<Month, Integer> monthlyPoints = new HashMap<>();
        monthlyPoints.put(Month.JANUARY, 120);
        monthlyPoints.put(Month.FEBRUARY, 50);

        RewardSummary expectedSummary = new RewardSummary(customerId, monthlyPoints, 170);
        when(rewardService.getRewardsByCustomer(customerId)).thenReturn(expectedSummary);

        // Act
        RewardSummary actualSummary = rewardsController.getRewards(customerId);

        // Assert
        assertNotNull(actualSummary, "Reward summary should not be null");
        assertEquals(customerId, actualSummary.getCustomerId(), "Customer ID should match");
        assertEquals(170, actualSummary.getTotalPoints(), "Total points should match");
        assertEquals(2, actualSummary.getMonthlyPoints().size(), "Monthly points should contain 2 entries");
        assertEquals(120, actualSummary.getMonthlyPoints().get(Month.JANUARY));
        assertEquals(50, actualSummary.getMonthlyPoints().get(Month.FEBRUARY));

        verify(rewardService, times(1)).getRewardsByCustomer(customerId);
    }
}
