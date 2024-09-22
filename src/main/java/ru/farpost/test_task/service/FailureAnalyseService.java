package ru.farpost.test_task.service;

import ru.farpost.test_task.entity.LogRecordEntity;

public class FailureAnalyseService {

    int lineCounter = 0;
    int startLine = 0;
    int endLine = 0;

    private final static String BAD_RESPONSE_CODE = "5";

    private final double acceptableResponseTime;
    private final double acceptableAvailabilityLevel;
    private boolean isFallPeriod = false;
    private String lastFailTime;

    private final StatsCalculator statsCalculator;

    public FailureAnalyseService(double acceptableResponseTime, double acceptableAvailabilityLevel) {
        this.acceptableResponseTime = acceptableResponseTime;
        this.acceptableAvailabilityLevel = acceptableAvailabilityLevel;
        statsCalculator = new StatsCalculator();
    }

    public void analyse(LogRecordEntity logRecord) {
        lineCounter++;
        if (isLogFailed(logRecord)) {
            statsCalculator.addFail();
            lastFailTime = logRecord.getRequestDateTime();
            endLine = lineCounter;
            if (!isFallPeriod) {
                System.out.print(logRecord.getRequestDateTime() + " ");
                isFallPeriod = true;
                startLine = lineCounter;
            }
        } else if (isFallPeriod) {
            statsCalculator.addSuccess();
            double availabilityLevel = statsCalculator.getAvailabilityLevelWithCache();
            if (availabilityLevel >= acceptableAvailabilityLevel) {
                System.out.printf("%s %f\n",
                        lastFailTime,
                        statsCalculator.getAvailabilityLevel());

                System.out.printf("%d %d\n", startLine, endLine);

                statsCalculator.reset();
                isFallPeriod = false;
            }
        }
    }

    private boolean isLogFailed(LogRecordEntity logRecord) {
        return logRecord.getErrorCode().startsWith(BAD_RESPONSE_CODE) ||
                logRecord.getResponseTime() > acceptableResponseTime;
    }
}
