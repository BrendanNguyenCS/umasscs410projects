package cs410.webfilmz;

import java.util.*;

/**
 * Represents the catalog, the list of all available films.
 * <p>
 * Caches a mapping of director to the films they directed.
 * <p>
 * Responsible for adding new films; generating recommendations, both generic
 * and personal.
 * <p>
 * Invariant: every film shows up both in {@link #allFilms} set, in the set
 *   for the director of the film in {@link #byDirector}, and in the set for the genre of the film in {@link #byGenre}
 * <p>
 * Refers to {@link Film}, factory for Films
 * <p>
 * Relies on {@link ILikeFilm} for film preferences for generating recommendations.
 */
public class Catalog {
    /**
     * all available films
     */
    private final Set<Film> allFilms;

    /**
     * cached mapping from director/genre to the films they directed/in that genre
     */
    private final Map<String, Set<Film>> byDirector;
    private final Map<String, Set<Film>> byGenre;

    public Catalog() {
        allFilms = new HashSet<>();
        byDirector = new HashMap<>();
        byGenre = new HashMap<>();
    }

    /**
     * Factory for films, ensures that new films are recorded in the catalog.
     * @param title the film's title
     * @param director the film's director
     * @param genre the film's genre
     * @param releaseYear the film's year of release
     * @param rating the film's rating
     * @return the newly added film
     */
    public Film add(String title, String director, String genre,
                    int releaseYear, Rating rating) {
        Film newFilm = new Film(title, director, genre, releaseYear, rating);
        allFilms.add(newFilm);
        Set<Film> otherFilms = byDirector.get(director);
        Set<Film> otherFilmsByGenre = byGenre.get(genre);
        if (otherFilms == null) {
            otherFilms = new HashSet<>();
            byDirector.put(director, otherFilms);
        }
        if (otherFilmsByGenre == null) {
            otherFilmsByGenre = new HashSet<>();
            byGenre.put(genre, otherFilmsByGenre);
        }
        otherFilms.add(newFilm);
        otherFilmsByGenre.add(newFilm);
        return newFilm;
    }

    /**
     * @param title the requested film's title
     * @return the film with the given title
     * @throws RuntimeException if no such film is in the catalog
     */
    public Film findByTitle(String title) throws RuntimeException {
        for (Film film : allFilms) {
            if (film.title().equals(title)) {
                return film;
            }
        }
        throw new RuntimeException("film not found");
    }

    /**
     * Get up to count recommendations, the most recent/watched/liked films in the catalog
     */
    public Set<Film> getRecommendationsByYear(int count) {
        Comparator<Film> comparator = Comparator.comparingInt(Film::releaseYear).reversed();
        return getRecommendationBySorting(count, comparator);
    }
    public Set<Film> getRecommendationsMostWatched(int count) {
        Comparator<Film> comparator = Comparator.comparingInt(Film::totalWatched).reversed();
        return getRecommendationBySorting(count, comparator);
    }
    public Set<Film> getRecommendationsMostLiked(int count) {
        Comparator<Film> comparator = Comparator.comparingInt(Film::totalLiked).reversed();
        return getRecommendationBySorting(count, comparator);
    }

    /**
     * Generalization of non-personalized recommendations by Film attributes.
     * <p>
     * The comparator should put the best recommendations at the <b>start</b> of the list.
     */
    private Set<Film> getRecommendationBySorting(int count, Comparator<Film> comparator) {
        List<Film> films = new ArrayList<>(allFilms);
        films.sort(comparator);
        count = Integer.min(count, films.size());
        films = films.subList(0, count);
        return new HashSet<>(films);
    }

    /**
     * Get all films by liked director/genre
     */
    public Set<Film> getRecommendationsByDirector(ILikeFilm user) {
        Set<Film> recommendations = new HashSet<>();
        for (Map.Entry<String, Set<Film>> entry : byDirector.entrySet()) {
            if (user.isLikedDirector(entry.getKey())) {
                recommendations.addAll(entry.getValue());
            }
        }
        return recommendations;
    }
    public Set<Film> getRecommendationsByGenre(ILikeFilm user) {
        Set<Film> recommendations = new HashSet<>();
        for (Map.Entry<String, Set<Film>> entry : byGenre.entrySet()) {
            if (user.isLikedGenre(entry.getKey())) {
                recommendations.addAll(entry.getValue());
            }
        }
        return recommendations;
    }

    /**
     * @return the string representation of the catalog
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("The films in the catalog include:\n");
        for (Film f : allFilms) {
            sb.append(f.toString()).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Catalog catalog = new Catalog();
        catalog.add("The Terminator", "James Cameron", "SciFi", 1984, Rating.R);
        catalog.add("The Princess Bride", "Rob Reiner", "Romance", 1987, Rating.PG);
        catalog.add("The City of Lost Children", "Jean-Pierre Jeunet", "SciFi", 1995, Rating.R);
        catalog.add("Toy Story", "John Lasseter", "Comedy", 1995, Rating.G);
        catalog.add("Titanic", "James Cameron", "Romance", 1997, Rating.PG13);
        catalog.add("Memento", "Christopher Nolan", "Thriller", 2000, Rating.R);
        catalog.add("Amelie", "Jean-Pierre Jeunet", "Romance", 2001, Rating.R);
        catalog.add("Inception", "Christopher Nolan", "SciFi", 2010, Rating.PG13);
        catalog.add("The Martian", "Ridley Scott", "SciFi", 2015, Rating.PG13);
        catalog.add("Oppenheimer", "Christopher Nolan", "Bio" ,2023, Rating.R);
        catalog.add("Avatar", "James Cameron", "SciFi", 2009, Rating.PG13);
        catalog.add("Aliens", "James Cameron", "SciFi", 1986, Rating.R);
        catalog.add("Tenet", "Christopher Nolan", "SciFi", 2020, Rating.PG13);
        catalog.add("The Dark Knight Rises", "Christopher Nolan", "SciFi", 2012, Rating.PG13);
        catalog.add("Fast Five", "Justin Lin", "Action", 2011, Rating.PG13);
        catalog.add("Cars", "John Lasseter", "Comedy", 2006, Rating.G);

        System.out.println(catalog);
    }
}
