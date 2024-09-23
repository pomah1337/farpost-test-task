package ru.farpost.test_task.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тест класса {@link StatsCalculator}
 */
public class StatsCalculatorTest {

    StatsCalculator calculator;

    @BeforeEach
    void init() {
        calculator = new StatsCalculator();
    }

    /**
     * Проверяет {@link StatsCalculator#getAvailabilityLevel()}
     * Условия: добавлены успешная и неуспешная записи.
     * Результат: 50% доступности.
     */
    @Test
    void getAvailabilityLevel_halfFail_halfAvailability() {
        calculator.addSuccess();
        calculator.addFail();
        double availabilityLevel = calculator.getAvailabilityLevel();
        Assertions.assertEquals(50., availabilityLevel);
    }

    /**
     * Проверяет {@link StatsCalculator#getAvailabilityLevel()}
     * Условия: добавлены только неуспешные записи.
     * Результат: 0% доступности.
     */
    @Test
    void getAvailabilityLevel_onlyFail_zeroAvailability() {
        calculator.addFail();
        calculator.addFail();
        calculator.addFail();
        double availabilityLevel = calculator.getAvailabilityLevel();
        Assertions.assertEquals(0., availabilityLevel);
    }

    /**
     * Проверяет {@link StatsCalculator#getAvailabilityLevel()}
     * Условия: добавлены только успешные записи.
     * Результат: 100% доступности.
     */
    @Test
    void getAvailabilityLevel_onlySuccess_100PercentAvailability() {
        calculator.addSuccess();
        calculator.addSuccess();
        calculator.addSuccess();
        double availabilityLevel = calculator.getAvailabilityLevel();
        Assertions.assertEquals(100., availabilityLevel);
    }


    /**
     * Проверяет {@link StatsCalculator#getAvailabilityLevelAfterLastFail()}
     * Условия: добавлены успешная и неуспешная записи, добавлена успешная запись в кеш.
     * Результат: Уровень доступности различается пока в кеше есть успешные записи.
     */
    @Test
    void getAvailabilityLevelAfterLastFail_halfFail_halfAvailability() {
        calculator.addSuccess();
        calculator.addFail();
        calculator.addSuccess();
        double availabilityLevel = calculator.getAvailabilityLevel();
        double realAvailabilityLevel = calculator.getAvailabilityLevelAfterLastFail();
        Assertions.assertEquals(50., realAvailabilityLevel);
        Assertions.assertTrue(availabilityLevel > realAvailabilityLevel);
        calculator.addFail();
        Assertions.assertEquals(calculator.getAvailabilityLevel(), calculator.getAvailabilityLevelAfterLastFail());
    }
}
