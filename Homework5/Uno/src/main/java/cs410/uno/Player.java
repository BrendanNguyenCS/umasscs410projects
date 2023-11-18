package cs410.uno;

import java.util.*;

/**
 * Represents a player in the Uno game.
 */
public class Player {

    /**
     * The player's current hand
     */
    private final List<Card> hand;

    /**
     * Constructor
     */
    public Player() {
        hand = new ArrayList<>();
    }

    /**
     * Add a new card to the player's hand
     * @param c the card to add
     */
    public void addToHand(Card c) {
        hand.add(c);
    }

    /**
     * Plays a random playable card from the player's hand
     * @param lastPlayed the last played card
     * @return the card being played, or {@code null} if the player has no playable cards
     */
    public Card playCard(Card lastPlayed) {
        ArrayList<Card> playableCards = new ArrayList<>();
        for (Card card : hand) {
            if (card.isPlayable(lastPlayed)) {
                playableCards.add(card);
            }
        }
        Random rand = new Random();
        int i = rand.nextInt(playableCards.size());
        Card c = playableCards.get(i);
        if (hand.remove(c)) {
            return c;
        } else {
            return null;
        }
    }

    /**
     * Determines if this player's hand is empty
     */
    public boolean hasEmptyHand() {
        return hand.isEmpty();
    }
}
