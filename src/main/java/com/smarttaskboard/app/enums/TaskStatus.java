package com.smarttaskboard.app.enums;

import lombok.Getter;

@Getter
public enum TaskStatus {
    TODO("To DO"),
    PROGRESS("In Progress"),
    DONE("Done");

    private final String displayName;

    TaskStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getStatusName() {
        return displayName;
    }
}
