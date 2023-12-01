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

    private int totalWatched = 0;
    private int totalLiked = 0;

    Film(String title, String director, String genre,
                 int releaseYear) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }

    // Update watched/liked counters, respectively
    public void incrementWatched(int toAdd) {
        totalWatched = totalWatched + toAdd;
    }
    public void incrementLiked(int toAdd) {
        totalLiked = totalLiked + toAdd;
    }
}
