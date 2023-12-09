package cs410.uno;

/**
 * Represents a normal card in the Uno game.
 * <p>
 * Class invariant: Card values must be between {@code 0} and {@code 9} inclusive.
 */
public class NormalCard extends Card {
    /**
     * Constructor
     *
     * @param value the card's value
     * @param color the card's (effective) color
     */
    public NormalCard(int value, String color) throws IllegalArgumentException {
        super("Normal", color);
        if (value >= 0 && value <= 9) {
            this.setValue(String.valueOf(value));
        } else {
            throw new IllegalArgumentException("Invalid values detected");
        }
    }

    /**
     * @return the string representation of this normal card
     */
    @Override
    public String toString() {
        return getColor() + " " + getValue();
    }
}
