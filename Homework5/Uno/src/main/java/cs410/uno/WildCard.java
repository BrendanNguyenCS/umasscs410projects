package cs410.uno;

import java.util.Random;

/**
 * Represents a wild card in Uno
 */
public class WildCard extends Card {
    /**
     * Constructor
     */
    public WildCard() {
        super("Wild", "Wild", "None");
    }

    /**
     * Sets the effective color of this card. Is guarded so that this is only possible for wild cards.
     * @param color the new effective color
     */
    public void setEffectiveColor(String color) {
        super.setColor(color);
    }

    /**
     * Randomly sets the effective color of this card. Is guarded so that this is only possible for wild cards.
     */
    public void setRandomEffectiveColor() {
        Random r = new Random();
        int i = r.nextInt(colors.length - 1);
        setEffectiveColor(colors[i]);
    }
}
