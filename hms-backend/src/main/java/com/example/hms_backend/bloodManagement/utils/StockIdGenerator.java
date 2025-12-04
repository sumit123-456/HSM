package com.example.hms_backend.bloodManagement.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StockIdGenerator {
    private static final String PREFIX = "STK-";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String generateStockId() {
        Long sequence = getNextSequence();
        return String.format("%s%d", PREFIX, sequence);
    }
    private Long getNextSequence() {
        createSequenceTableIfNotExists();

        String sql = "SELECT next_value FROM stock_sequence WHERE sequence_name = 'stock_id' FOR UPDATE";
        Long currentValue = jdbcTemplate.queryForObject(sql, Long.class);

        String updateSql = "UPDATE stock_sequence SET next_value = next_value + 1 WHERE sequence_name = 'stock_id'";
        jdbcTemplate.update(updateSql);

        return currentValue;
    }

    private void createSequenceTableIfNotExists() {
        try {
            String createTableSql = "CREATE TABLE IF NOT EXISTS stock_sequence (" +
                    "sequence_name VARCHAR(50) PRIMARY KEY, " +
                    "next_value BIGINT NOT NULL, " +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                    ")";
            jdbcTemplate.execute(createTableSql);

            String insertSql = "INSERT IGNORE INTO stock_sequence (sequence_name, next_value) VALUES ('stock_id', 1001)";
            jdbcTemplate.update(insertSql);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create sequence table", e);
        }
    }
    public void resetSequence() {
        String sql = "UPDATE stock_sequence SET next_value = 1001 WHERE sequence_name = 'stock_id'";
        jdbcTemplate.update(sql);
    }
    public Long getCurrentSequenceValue() {
        String sql = "SELECT next_value FROM stock_sequence WHERE sequence_name = 'stock_id'";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}
