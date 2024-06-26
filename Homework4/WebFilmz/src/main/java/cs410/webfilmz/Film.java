package cs410.webfilmz;

import lombok.Getter;

/**
 * Represents a film, including both its immutable
 * intrinsic information and its mutable viewing history.
 * <p>
 * Used by {@link User} and {@link Catalog}, updated by {@link User}; created by {@link Catalog}.
 */
@Getter
public class Film {
    private final String title;
    private final String director;
    private final String genre;
    private final int releaseYear;
    private final Rating rating;

    private int totalWatched = 0;
    private int totalLiked = 0;

    Film(String title, String director, String genre,
                 int releaseYear, Rating rating) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    /**
     * Update watched/liked counters, respectively
     */
    public void incrementWatched(int toAdd) {
        totalWatched += toAdd;
    }
    public void incrementLiked(int toAdd) {
        totalLiked += toAdd;
    }

    /**
     * Indicates whether the film's rating is appropriate for an audience that can watch up to the given limit
     */
    public boolean isAppropriateFor(Rating limit) {
        return rating.isAppropriateFor(limit);
    }

    /**
     * @return the string representation of this film
     */
    @Override
    public String toString() {
        return title + " (" + releaseYear + ", " + genre + ", " + rating.toString() + ") directed by " + director;
    }
}
