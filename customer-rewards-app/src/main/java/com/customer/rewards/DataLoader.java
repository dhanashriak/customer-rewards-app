package com.customer.rewards;

import com.customer.rewards.model.Transaction;
import com.customer.rewards.repository.TransactionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

/**
 * Loads transaction data from a JSON file into MongoDB
 * when the application starts.
 */
@Component
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final TransactionRepository repository;

    public DataLoader(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("transactions.json")) {
            if (is == null) {
                log.warn("transactions.json not found in classpath. Skipping data load.");
                return;
            }

            List<Transaction> transactions = mapper.readValue(is, new TypeReference<List<Transaction>>() {});
            repository.saveAll(transactions);
            log.info("Loaded {} transactions into MongoDB.", transactions.size());
        } catch (Exception e) {
            log.error("Failed to load transactions data", e);
            throw e;
        }
    }
}
