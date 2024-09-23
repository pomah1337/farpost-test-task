package ru.farpost.test_task.service;

import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * Тест класса {@link OptionParser}
 */
public class OptionParserTest {

    /**
     * Проверяет {@link OptionParser#getOptions(String[])}
     * Условия: переданы корректные аргументы.
     * Результат: вернулись корректные параметры.
     */
    @Test
    void getOptions_correctOptions_success() throws ParseException {
        String[] args = new String[]{"-u", "99.9", "-t", "12.34"};
        Map<String, Double> options = OptionParser.getOptions(args);
        Assertions.assertEquals(99.9, options.get("u"));
        Assertions.assertEquals(12.34, options.get("t"));
    }

    /**
     * Проверяет {@link OptionParser#getOptions(String[])}
     * Условия: не хватает параметра.
     * Результат: выбросилось исключение {@link ParseException}.
     */
    @Test
    void getOptions_notEnoughOptions_throwsParseException()  {
        Assertions.assertThrows(ParseException.class,()->OptionParser.getOptions(new String[]{"-u", "99.9"}));
        Assertions.assertThrows(ParseException.class,()->OptionParser.getOptions(new String[]{"-t", "12.34"}));
        Assertions.assertThrows(ParseException.class,()->OptionParser.getOptions(new String[]{}));
        Assertions.assertThrows(ParseException.class,()->OptionParser.getOptions(new String[]{"-u", "99.9", "-t"}));
    }

    /**
     * Проверяет {@link OptionParser#getOptions(String[])}
     * Условия: передан не правильный параметр.
     * Результат: выбросилось исключение {@link UnrecognizedOptionException}.
     */
    @Test
    void getOptions_wrongOption_throwsUnrecognizedOptionException()  {
        Assertions.assertThrows(UnrecognizedOptionException.class,()->OptionParser.getOptions(new String[]{"-u", "99.9","-o", "-t", "12.34"}));
        Assertions.assertThrows(UnrecognizedOptionException.class,()->OptionParser.getOptions(new String[]{"-i", "12.34"}));
    }

    /**
     * Проверяет {@link OptionParser#getOptions(String[])}
     * Условия: передан не правильный параметр.
     * Результат: выбросилось исключение {@link NumberFormatException}.
     */
    @Test
    void getOptions_wrongOptionValue_throwsNumberFormatException()  {
        Assertions.assertThrows(NumberFormatException.class,()->OptionParser.getOptions(new String[]{"-u", "ninety nine", "-t", "12.34"}));
    }

}
