package ru.farpost.test_task.entity;

import lombok.Getter;

/**
 * Сущность для чтения записи из лога.
 */
@Getter
public class LogRecordEntity {
    private final String requestDateTime;
    private final String errorCode;
    private final double responseTime;

    public LogRecordEntity(String str) {
        String[] columns = str.split(" ");
        requestDateTime = columns[3].split(":", 2)[1];
        errorCode = columns[8];
        responseTime = Double.parseDouble(columns[10]);
    }
}
