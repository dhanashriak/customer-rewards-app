package com.customer.rewards.util;

/**
 * Utility class holding constants used for reward point calculations.
 */
public final class Constants {

    /** Private constructor to prevent instantiation. */
    private Constants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /** Lower threshold for earning reward points. */
    public static final double LOWER_THRESHOLD = 50.0;

    /** Upper threshold for earning double reward points. */
    public static final double UPPER_THRESHOLD = 100.0;

    /** Number of points awarded per dollar between LOWER and UPPER thresholds. */
    public static final int ONE_POINT = 1;

    /** Number of points awarded per dollar above the UPPER threshold. */
    public static final int TWO_POINTS = 2;
}
