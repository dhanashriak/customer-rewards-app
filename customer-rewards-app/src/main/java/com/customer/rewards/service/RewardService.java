package com.customer.rewards.service;

import com.customer.rewards.exception.ResourceNotFoundException;
import com.customer.rewards.model.RewardSummary;
import com.customer.rewards.model.Transaction;
import com.customer.rewards.repository.TransactionRepository;
import com.customer.rewards.util.Constants;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class responsible for calculating reward points for customers based on their transactions.
 */
@Service
public class RewardService {

    private final TransactionRepository transactionRepository;

    public RewardService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Retrieves the reward summary for a specific customer.
     *
     * @param customerId the ID of the customer
     * @return the reward summary containing monthly and total reward points
     * @throws ResourceNotFoundException if no transactions are found for the customer
     */
    public RewardSummary getRewardsByCustomer(String customerId) {
        List<Transaction> transactions = transactionRepository.findByCustomerId(customerId);

        if (transactions == null || transactions.isEmpty()) {
            throw new ResourceNotFoundException("No transactions found for customer: " + customerId);
        }

        Map<Month, Integer> monthlyPoints = new HashMap<>();
        int totalPoints = 0;

        for (Transaction transaction : transactions) {
            int points = calculateRewardPoints(transaction.getAmount());
            Month month = transaction.getDate().getMonth();
            monthlyPoints.merge(month, points, Integer::sum);
            totalPoints += points;
        }

        return RewardSummary.builder()
                .customerId(customerId)
                .monthlyPoints(monthlyPoints)
                .totalPoints(totalPoints)
                .build();
    }

    /**
     * Calculates reward points based on the transaction amount using predefined thresholds.
     *
     * @param amount the transaction amount
     * @return the calculated reward points
     */
    private int calculateRewardPoints(double amount) {
        int points = 0;

        if (amount > Constants.UPPER_THRESHOLD) {
            points += (int) ((amount - Constants.UPPER_THRESHOLD) * Constants.TWO_POINTS);
            points += (int) ((Constants.UPPER_THRESHOLD - Constants.LOWER_THRESHOLD) * Constants.ONE_POINT);
        } else if (amount > Constants.LOWER_THRESHOLD) {
            points += (int) ((amount - Constants.LOWER_THRESHOLD) * Constants.ONE_POINT);
        }

        return points;
    }
}
