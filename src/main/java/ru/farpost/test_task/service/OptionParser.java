package ru.farpost.test_task.service;

import org.apache.commons.cli.*;

import java.util.Map;

public class OptionParser {


    public static Map<String, Double> getOptions(String[] args) throws ParseException {

        Options options = new Options();
        Option acceptableResponseTime = new Option("t", true, "time");
        Option acceptableAvailabilityLevel = new Option("u", true, "level");
        acceptableResponseTime.setRequired(true);
        acceptableAvailabilityLevel.setRequired(true);
        options.addOption(acceptableResponseTime);
        options.addOption(acceptableAvailabilityLevel);

        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine;

        commandLine = parser.parse(options, args);

        return Map.of("u", Double.parseDouble(commandLine.getOptionValue("u")),
                "t", Double.parseDouble(commandLine.getOptionValue("t")));
    }
}
