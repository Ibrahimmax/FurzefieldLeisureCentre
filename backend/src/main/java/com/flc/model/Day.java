package com.flc.model;

/**
 * Enum representing the days when lessons are offered
 */
public enum Day {
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    private final String displayName;

    Day(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
