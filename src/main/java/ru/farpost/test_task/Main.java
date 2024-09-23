package ru.farpost.test_task;


import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;
import ru.farpost.test_task.entity.LogRecordEntity;
import ru.farpost.test_task.entity.ReportEntity;
import ru.farpost.test_task.service.FailureAnalyseService;
import ru.farpost.test_task.service.OptionParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Main {

    private static final String OPTIONS_HINT = "Try: -u <response time: double> -t <availability level: double>";

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            Map<String, Double> options = OptionParser.getOptions(args);
            FailureAnalyseService failureAnalyseService = new FailureAnalyseService(
                    options.get("t"),
                    options.get("u")
            );
            String line;
            while ((line = reader.readLine()) != null) {
                ReportEntity report = failureAnalyseService.addLogToAnalysis(new LogRecordEntity(line));
                if (report != null)
                    System.out.println(report);
            }

        } catch (UnrecognizedOptionException e) {
            System.out.printf("Unknown option %s. %s.\n", e.getOption(), OPTIONS_HINT);
        } catch (ParseException e) {
            System.out.printf("Missing options. %s.\n", OPTIONS_HINT);
        } catch (NumberFormatException e) {
            System.out.printf("Incorrect value. %s.\n", OPTIONS_HINT);
        } catch (IOException ignored) {
        }
    }
}