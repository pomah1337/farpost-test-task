package ru.farpost.test_task.entity;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportEntity {
    private String start;
    private String end;
    private double availabilityLevel;
    private boolean isReady = false;

    @Override
    public String toString() {
        return String.format("%s %s %.1f", start, end, availabilityLevel);
    }
}
