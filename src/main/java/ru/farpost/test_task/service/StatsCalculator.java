package ru.farpost.test_task.service;

/**
 * Сервис для расчётов уровня доступности web-сервиса.
 */
public class StatsCalculator {
    private int failCount = 0;
    private int successCount = 0;
    private int successCountCache = 0;


    /**
     * Считает уровень доступности до последнего сбоя.
     * @return уровень доступности с учётом последних успешных запросов из кеша.
     */
    public double getAvailabilityLevelAfterLastFail() {
        return (double) successCount / (double) (successCount + failCount) * 100.;
    }

    /**
     * Считает текущий уровень доступности.
     * @return уровень доступности с учётом последних успешных запросов из кеша.
     */
    public double getAvailabilityLevel() {
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
