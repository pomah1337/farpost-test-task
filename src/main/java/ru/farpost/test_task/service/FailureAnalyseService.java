package ru.farpost.test_task.service;

import ru.farpost.test_task.entity.LogRecordEntity;
import ru.farpost.test_task.entity.ReportEntity;

/**
 * Сервис для анализа access-лога.
 */
public class FailureAnalyseService {
    private final static String BAD_RESPONSE_CODE = "5";

    private final double acceptableResponseTime;
    private final double acceptableAvailabilityLevel;

    private final StatsCalculator statsCalculator;
    private boolean isFallPeriod = false;
    private String lastFailTime;
    private ReportEntity report;

    public FailureAnalyseService(double acceptableResponseTime, double acceptableAvailabilityLevel) {
        this.acceptableResponseTime = acceptableResponseTime;
        this.acceptableAvailabilityLevel = acceptableAvailabilityLevel;
        statsCalculator = new StatsCalculator();
        report = new ReportEntity();
    }

    /**
     * Анализирует запись лога, создаёт отчёт об отказах.
     *
     * @param logRecord новая запись access-лога.
     * @return возвращает отчёт об отказе если доля отказов достигла указанного уровня.
     */
    public ReportEntity addLogToAnalysis(LogRecordEntity logRecord) {
        if (isLogFailed(logRecord)) {
            processFailLog(logRecord);
        } else if (isFallPeriod) {
            processSuccessLog();
        }
        if (report.isReady()) {
            ReportEntity resultReport = report;
            report = new ReportEntity();
            return resultReport;
        } else {
            return null;
        }
    }

    private void processFailLog(LogRecordEntity logRecord) {
        statsCalculator.addFail();
        lastFailTime = logRecord.getRequestDateTime();
        if (!isFallPeriod) {
            report.setStart(logRecord.getRequestDateTime());
            isFallPeriod = true;
        }
    }

    private void processSuccessLog() {
        statsCalculator.addSuccess();
        double availabilityLevel = statsCalculator.getAvailabilityLevel();
        if (availabilityLevel >= acceptableAvailabilityLevel) {
            report.setEnd(lastFailTime);
            report.setAvailabilityLevel(statsCalculator.getAvailabilityLevelAfterLastFail());
            report.setReady(true);
            statsCalculator.reset();
            isFallPeriod = false;
        }
    }

    private boolean isLogFailed(LogRecordEntity logRecord) {
        return logRecord.getErrorCode().startsWith(BAD_RESPONSE_CODE) ||
                logRecord.getResponseTime() > acceptableResponseTime;
    }
}
