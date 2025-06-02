package com.customer.rewards.repository;

import com.customer.rewards.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing {@link Transaction} data from MongoDB.
 */
@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    /**
     * Finds all transactions associated with the specified customer ID.
     *
     * @param customerId the ID of the customer
     * @return a list of transactions for the customer
     */
    List<Transaction> findByCustomerId(String customerId);
}
