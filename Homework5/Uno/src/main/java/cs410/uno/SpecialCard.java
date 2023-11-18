package cs410.uno;

import java.util.Arrays;

public class SpecialCard extends Card {

    /**
     * Possible values for a Uno special card
     */
    public static final String[] values = {"Skip", "Reverse", "Draw Two"};

    /**
     * Constructor
     *
     * @param value the card's value
     * @param color the card's (effective) color
     */
    public SpecialCard(String value, String color) {
        super("Special", color);
        if (Arrays.asList(values).contains(value)) {
            super.setValue(value);
        } else {
            throw new IllegalArgumentException("Invalid values detected");
        }
    }
}
