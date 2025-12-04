package com.example.hms_backend.bloodManagement.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DonationIdGenerator {

    private static final String PREFIX = "DNT";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String generateDonationId() {
        createSequenceTableIfNotExists();

        String sql = "SELECT next_value FROM donation_sequence WHERE sequence_name = 'donation_id' FOR UPDATE";
        Long currentValue = jdbcTemplate.queryForObject(sql, Long.class);

        String updateSql = "UPDATE donation_sequence SET next_value = next_value + 1 WHERE sequence_name = 'donation_id'";
        jdbcTemplate.update(updateSql);

        return String.format("%s%07d", PREFIX, currentValue); // DNT0000001
    }

    private void createSequenceTableIfNotExists() {
        try {
            String createTableSql = "CREATE TABLE IF NOT EXISTS donation_sequence (" +
                    "sequence_name VARCHAR(50) PRIMARY KEY, " +
                    "next_value BIGINT NOT NULL, " +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                    ")";
            jdbcTemplate.execute(createTableSql);

            String insertSql = "INSERT IGNORE INTO donation_sequence (sequence_name, next_value) VALUES ('donation_id', 1)";
            jdbcTemplate.update(insertSql);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create sequence table", e);
        }
    }
}
