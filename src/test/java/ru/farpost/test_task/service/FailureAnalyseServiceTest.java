package ru.farpost.test_task.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.farpost.test_task.entity.LogRecordEntity;
import ru.farpost.test_task.entity.ReportEntity;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Тест класса {@link FailureAnalyseService}
 */
public class FailureAnalyseServiceTest {

    private final Random random = new Random();
    private LocalDateTime dt = LocalDateTime.of(2024, 1, 1, 0, 0);

    /**
     * Проверяет {@link FailureAnalyseService#addLogToAnalysis(LogRecordEntity)}
     * Условия: автоматически генерируются тестовые данные.
     * Результат: уровень доступности в отчётах не превышает максимального.
     */
    @Test
    void analyse_autotest() {
        double availabilityLevel = 50;
        FailureAnalyseService failureAnalyseService = new FailureAnalyseService(50, availabilityLevel);
        for (int i = 0; i < 1000000; i++) {
            ReportEntity report = failureAnalyseService.addLogToAnalysis(new LogRecordEntity(generateLog()));
            Assertions.assertTrue(report == null || availabilityLevel > report.getAvailabilityLevel());
        }
    }

    private String generateLog() {
        dt = dt.plusNanos(300000000);
        int code = random.nextInt(100) == 5 ? 5 : 2;
        double time = 10 + random.nextDouble() * (80);
        StringBuilder sb = new StringBuilder();
        sb.append("192.168.32.181 - - [14/06/2017:")
                .append(dt.getHour())
                .append(':')
                .append(dt.getMinute())
                .append(':')
                .append(dt.getSecond())
                .append(" +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=7ae28555 HTTP/1.1\" ")
                .append(code)
                .append("00 2 ")
                .append(time)
                .append(" \"-\" \"@list-item-updater\" prio:0");
        return sb.toString();
    }
}
