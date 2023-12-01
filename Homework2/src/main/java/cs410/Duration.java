package cs410;

/**
 * Represents a duration of time, with a precision of seconds (whole seconds only).
 */
public class Duration {

    /**
     * The number of hours in this {@link Duration}
     */
    private final int hours;
    /**
     * The number of minutes in this {@link Duration}.
     * <p>
     * Field invariant: Should be in range [0..60)
     */
    private final int minutes;
    /**
     * The number of seconds in this {@link Duration}.
     * <p>
     * Field invariant: Should be in range [0..60)
     */
    private final int seconds;

    /**
     * Constructs a new {@link Duration} object
     * <p>
     * All values will be adjusted so that the field invariants will hold true.
     * <p>
     * For example, a duration of {@code 6:75:75} would be adjusted to {@code 7:16:15}
     */
    private Duration(int hours, int minutes, int seconds) {
        // calculate adjustments
        int additionalMinutes = seconds / 60;
        int additionalHours = (minutes + additionalMinutes) / 60;

        this.hours = hours + additionalHours;
        this.minutes = (minutes + additionalMinutes) - (additionalHours * 60);
        this.seconds = seconds - (additionalMinutes * 60);
    }

    /**
     * Static factory method
     * @param totalSeconds the total number of seconds in the duration
     */
    public static Duration of(int totalSeconds) {
        if (totalSeconds < 0) {
            throw new IllegalArgumentException("Given time is invalid");
        }
        int timeHours = totalSeconds / 3600;
        totalSeconds -= timeHours * 3600;
        int timeMinutes = totalSeconds / 60;
        totalSeconds -= timeMinutes * 60;
        return new Duration(timeHours, timeMinutes, totalSeconds);
    }

    /**
     * Static factory method
     * @param hours the number of hours in the duration
     * @param minutes the number of minutes in the duration
     * @param seconds the number of seconds in the duration
     */
    public static Duration of(int hours, int minutes, int seconds) {
        if (hours < 0 || minutes < 0 || seconds < 0) {
            throw new IllegalArgumentException("There is an invalid value for hours, minutes, and/or seconds.");
        }
        return new Duration(hours, minutes, seconds);
    }

    /**
     * Adds two {@link Duration Durations} together
     * @return the sum of the {@link Duration} objects in the form of a new {@link Duration} object
     */
    public Duration add(Duration other) {
        return new Duration(this.hours + other.hours,
                            this.minutes + other.minutes,
                            this.seconds + other.seconds);
    }

    /**
     * @return this {@link Duration}'s total number of seconds
     */
    public int seconds() {
        return (hours * 3600) + (minutes * 60) + seconds;
    }

    /**
     * Tests the equality of two {@link Duration} objects by comparing their hours, minutes, and seconds values. This method is needed in order to use {@code assertEquals} in tests.
     * @param other the other object being compared
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Duration otherDuration) {
            return (this.hours == otherDuration.hours &&
                    this.minutes == otherDuration.minutes &&
                    this.seconds == otherDuration.seconds);
        }
        return false;
    }

    /**
     * @return the string representation of this {@link Duration} in the  {@code H:MM:SS} format
     */
    @Override
    public String toString() {
        return String.format("%d:%02d:%02d", hours, minutes, seconds);
    }
}
