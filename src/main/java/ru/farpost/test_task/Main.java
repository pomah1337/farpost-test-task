package ru.farpost.test_task;


import ru.farpost.test_task.entity.LogRecordEntity;
import ru.farpost.test_task.handler.FailureAnalyseService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        FailureAnalyseService failureAnalyseService = new FailureAnalyseService(54, 90);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines().forEach(l -> failureAnalyseService.analyse(new LogRecordEntity(l)));
    }
}