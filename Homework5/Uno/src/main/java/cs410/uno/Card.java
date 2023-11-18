package cs410.uno;

import java.util.*;

/**
 * Represents a card in the Uno game.
 */
public abstract class Card {

    public static final String[] colors = {"Red", "Yellow", "Green", "Blue", "None"};
    public static final String[] types = {"Normal", "Special", "Wild"};

    /**
     * The card type of this Card. There are 3 types of Uno cards: "Normal", "Special", and "Wild".
     */
    private String type;

    /**
     * The value of this Card. The possible value of the card is determined by its type.
     * <ul>
     *     <li>Normal cards have values between "0" and "9"</li>
     *     <li>Special cards have values of either "Skip", "Reverse", or "Draw Two"</li>
     *     <li>Wild cards have a value of "Wild"</li>
     * </ul>
     */
    private String value;

    /**
     * The color of this card. Normal and special cards are colored red, yellow, green, or blue. Wild cards are colorless, but
     * the value of this variable will be the "effective" color once the card is played.
     */
    private String color;

    /**
     * Constructor
     * @param type the card's type
     */
    public Card(String type) {
        this(type, "", "");
    }

    public Card(String type, String color) {
        this(type, "", color);
    }

    public Card(String type, String value, String color) {
        if (Arrays.asList(types).contains(type) && Arrays.asList(colors).contains(color)) {
            this.type = type;
            this.color = color;
            this.value = value;
        } else {
            throw new IllegalArgumentException("Invalid values detected");
        }
    }

    // Getters and setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getColor() { return color; }
    public void setColor(String color) throws IllegalArgumentException {
        if (Arrays.asList(colors).contains(color)) {
            this.color = color;
        } else {
            throw new IllegalArgumentException("Invalid color");
        }
    }

    /**
     * Denotes whether the current card is playable. All special cards are playable at any time while normal cards of
     * the same color as the last played card can be played.
     * @param lastPlayed the last played card
     */
    public boolean isPlayable(Card lastPlayed) {
        // For normal and special cards, the color or face value must match
        if (type.equals("Normal") || type.equals("Special")) {
            return color.equals(lastPlayed.color) || value.equals(lastPlayed.value);
        }
        // All wild cards are playable
        return true;
    }
}
