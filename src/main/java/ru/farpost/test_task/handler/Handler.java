package ru.farpost.test_task.handler;

import ru.farpost.test_task.entity.FailureState;
import ru.farpost.test_task.entity.LogRecordEntity;

public abstract class Handler {
    protected Handler nextHandler;

    public Handler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    abstract public void handle(LogRecordEntity logRecord, FailureState state);

    protected void next(LogRecordEntity logRecord, FailureState state) {
        if (nextHandler != null) {
            nextHandler.handle(logRecord, state);
        }
    }
}
