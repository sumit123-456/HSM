package com.example.hms_backend.bloodManagement.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DonorIdGenerator {

    private static final String PREFIX = "DON";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String generateSimpleDonorId() {
        String datePart = getCurrentDatePart();
        Long sequence = getNextSequence();
        return String.format("%s%s%04d", PREFIX, datePart, sequence);
    }

    private String getCurrentDatePart() {
        return LocalDateTime.now().format(DATE_FORMATTER);
    }

    private Long getNextSequence() {
        createSequenceTableIfNotExists();
        String sql = "SELECT next_value FROM donor_sequence WHERE sequence_name = 'donor_id' FOR UPDATE";
        Long currentValue = jdbcTemplate.queryForObject(sql, Long.class);

        String updateSql = "UPDATE donor_sequence SET next_value = next_value + 1 WHERE sequence_name = 'donor_id'";
        jdbcTemplate.update(updateSql);

        return currentValue;
    }

    private void createSequenceTableIfNotExists() {
        try {
            String createTableSql = "CREATE TABLE IF NOT EXISTS donor_sequence (" +
                    "sequence_name VARCHAR(50) PRIMARY KEY, " +
                    "next_value BIGINT NOT NULL, " +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                    ")";
            jdbcTemplate.execute(createTableSql);

            String insertSql = "INSERT IGNORE INTO donor_sequence (sequence_name, next_value) VALUES ('donor_id', 1)";
            jdbcTemplate.update(insertSql);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create sequence table", e);
        }
    }
    public void resetSequence() {
        String sql = "UPDATE donor_sequence SET next_value = 1 WHERE sequence_name = 'donor_id'";
        jdbcTemplate.update(sql);
    }

    public Long getCurrentSequenceValue() {
        String sql = "SELECT next_value FROM donor_sequence WHERE sequence_name = 'donor_id'";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}
