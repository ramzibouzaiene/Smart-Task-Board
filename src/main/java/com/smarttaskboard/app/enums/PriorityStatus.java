package com.smarttaskboard.app.enums;

import lombok.Getter;

@Getter
public enum PriorityStatus {
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low");

    private final String displayName;

    PriorityStatus(String displayName){
        this.displayName = displayName;
    }

    public String getPriorityName() {
        return displayName;
    }
}
