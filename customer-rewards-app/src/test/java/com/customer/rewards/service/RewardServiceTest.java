package com.customer.rewards.service;

import com.customer.rewards.exception.ResourceNotFoundException;
import com.customer.rewards.model.RewardSummary;
import com.customer.rewards.model.Transaction;
import com.customer.rewards.repository.TransactionRepository;
import com.customer.rewards.util.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link RewardService} using Spring Boot context and @MockBean.
 */
@SpringBootTest
class RewardServiceTest {

    @Autowired
    private RewardService rewardService;

    @MockBean
    private TransactionRepository transactionRepository;

    /**
     * Should return correct reward summary for valid transactions.
     */
    @Test
    void shouldReturnRewardSummaryForValidTransactions() {
        String customerId = "cust123";
        List<Transaction> transactions = List.of(
                new Transaction("1", customerId, 120.0, LocalDateTime.of(2024, 1, 10, 10, 0)),
                new Transaction("2", customerId, 80.0, LocalDateTime.of(2024, 2, 15, 10, 0)),
                new Transaction("3", customerId, 45.0, LocalDateTime.of(2024, 3, 20, 10, 0)) // Below threshold
        );

        when(transactionRepository.findByCustomerId(customerId)).thenReturn(transactions);

        RewardSummary summary = rewardService.getRewardsByCustomer(customerId);

        assertEquals(customerId, summary.getCustomerId());
        //assertEquals(2, summary.getMonthlyPoints().size());
        assertEquals(90, summary.getMonthlyPoints().get(Month.JANUARY));
        assertEquals(30, summary.getMonthlyPoints().get(Month.FEBRUARY));
        assertEquals(120, summary.getTotalPoints());

        verify(transactionRepository).findByCustomerId(customerId);
    }

    /**
     * Should throw ResourceNotFoundException when no transactions exist.
     */
    @Test
    void shouldThrowExceptionForNoTransactions() {
        String customerId = "emptyUser";

        when(transactionRepository.findByCustomerId(customerId)).thenReturn(Collections.emptyList());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> rewardService.getRewardsByCustomer(customerId)
        );

        assertEquals("No transactions found for customer: " + customerId, exception.getMessage());
        verify(transactionRepository).findByCustomerId(customerId);
    }

    /**
     * Should return 0 reward points when transaction amount is exactly at lower threshold.
     */
    @Test
    void shouldReturnZeroPointsForLowerThresholdTransaction() {
        String customerId = "custLow";
        List<Transaction> transactions = List.of(
                new Transaction("1", customerId, Constants.LOWER_THRESHOLD, LocalDateTime.now())
        );

        when(transactionRepository.findByCustomerId(customerId)).thenReturn(transactions);

        RewardSummary summary = rewardService.getRewardsByCustomer(customerId);

        assertEquals(0, summary.getTotalPoints());
    }

    /**
     * Should calculate points correctly for transaction between lower and upper threshold.
     */
    @Test
    void shouldCalculatePointsForMiddleRangeTransaction() {
        String customerId = "custMid";
        List<Transaction> transactions = List.of(
                new Transaction("1", customerId, 75.0, LocalDateTime.of(2024, 4, 1, 12, 0))
        );

        when(transactionRepository.findByCustomerId(customerId)).thenReturn(transactions);

        RewardSummary summary = rewardService.getRewardsByCustomer(customerId);

        assertEquals(25, summary.getTotalPoints());
        assertEquals(25, summary.getMonthlyPoints().get(Month.APRIL));
    }

    /**
     * Should calculate correct points for transaction above upper threshold.
     */
    @Test
    void shouldCalculatePointsForHighValueTransaction() {
        String customerId = "custHigh";
        List<Transaction> transactions = List.of(
                new Transaction("1", customerId, 200.0, LocalDateTime.of(2024, 5, 1, 12, 0))
        );

        when(transactionRepository.findByCustomerId(customerId)).thenReturn(transactions);

        int expectedPoints = (int) ((200 - Constants.UPPER_THRESHOLD) * Constants.TWO_POINTS)
                + (int) ((Constants.UPPER_THRESHOLD - Constants.LOWER_THRESHOLD) * Constants.ONE_POINT);

        RewardSummary summary = rewardService.getRewardsByCustomer(customerId);

        assertEquals(expectedPoints, summary.getTotalPoints());
        assertEquals(expectedPoints, summary.getMonthlyPoints().get(Month.MAY));
    }
}
