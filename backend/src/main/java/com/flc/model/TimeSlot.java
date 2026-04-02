package com.flc.model;

/**
 * Enum representing the time slots for group exercise lessons
 */
public enum TimeSlot {
    MORNING("09:00 - 10:00", 0),
    AFTERNOON("14:00 - 15:00", 1),
    EVENING("18:00 - 19:00", 2);

    private final String timeRange;
    private final int slotNumber;

    TimeSlot(String timeRange, int slotNumber) {
        this.timeRange = timeRange;
        this.slotNumber = slotNumber;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public int getSlotNumber() {
        return slotNumber;
    }
}
