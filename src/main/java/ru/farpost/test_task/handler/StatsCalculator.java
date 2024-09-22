package ru.farpost.test_task.handler;

public class StatsCalculator {
    private int failCount = 0;
    private int successCount = 0;
    private int successCountCache = 0;


    public double getAvailabilityLevel() {
        return (double) successCount / (double) (successCount + failCount) * 100.;
    }

    public double getAvailabilityLevelWithCache() {
        return (double) (successCount + successCountCache) / (double) (successCount + failCount + successCountCache) * 100.;
    }

    public void addFail() {
        failCount++;
        successCount += successCountCache;
        successCountCache = 0;
    }

    public void addSuccess() {
        successCountCache++;
    }

    public void reset() {
        failCount = 0;
        successCount = 0;
        successCountCache = 0;
    }

}
