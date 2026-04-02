package com.flc.model;

/**
 * Enum representing different types of group exercises
 */
public enum ExerciseType {
    YOGA(25.00, "Yoga"),
    ZUMBA(30.00, "Zumba"),
    AQUACISE(28.00, "Aquacise"),
    BOX_FIT(35.00, "Box Fit"),
    BODY_BLITZ(32.00, "Body Blitz"),
    PILATES(26.00, "Pilates"),
    SPIN(31.00, "Spin"),
    HIIT(33.00, "HIIT");

    private final double price;
    private final String displayName;

    ExerciseType(double price, String displayName) {
        this.price = price;
        this.displayName = displayName;
    }

    public double getPrice() {
        return price;
    }

    public String getDisplayName() {
        return displayName;
    }
}
