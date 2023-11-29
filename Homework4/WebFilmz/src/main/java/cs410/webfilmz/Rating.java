package cs410.webfilmz;

import lombok.Getter;

/**
 * Represents MPAA film ratings (P, PG, PG-13, R)
 * <p>
 * Supports comparison with max acceptable Rating.
 * <p>
 * Used by Film, used by User to limit recommendations.
 */
@Getter
public final class Rating {
    public static Rating G = new Rating("G", 1);
    public static Rating PG = new Rating("PG", 2);
    public static Rating PG13 = new Rating("PG-13", 3);
    public static Rating R = new Rating("R", 4);
    public static Rating X = new Rating("X", 5);

    /**
     * Human-readable display
     */
    private final String display;
    /**
     * For comparison, greater = more restricted
     */
    private final int level;

    private Rating(String display, int level) {
        this.display = display;
        this.level = level;
    }

    /**
     * Is this Rating appropriate for an audience that accepts up to limit?
     */
    public boolean isAppropriateFor(Rating limit) {
        return this.level <= limit.level;
    }

    @Override
    public String toString() {
        return display;
    }
}
