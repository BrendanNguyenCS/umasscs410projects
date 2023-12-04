package cs410.uno;

import lombok.Getter;
import java.util.*;

/**
 * Represents a game of Uno
 * <p>
 * Class invariants
 * <ul>
 *     <li>The game must have at least 2 {@link Player players} to start</li>
 *     <li>Players must have 1 or more {@link NormalCard normal} card per digit and color.</li>
 *     <li>Players can have 0 or more {@link SpecialCard special} cards per color.</li>
 *     <li>Players can have 0 or more {@link WildCard wild} cards.</li>
 * </ul>
 */
@Getter
public class GameState {

    /**
     * The Uno game's draw pile
     */
    private final Deck draw;

    /**
     * The Uno game's discard pile
     */
    private final Deck discard;

    /**
     * The direction
     */
    private boolean direction = true;

    /**
     * The players involved in the game
     */
    private final LinkedList<Player> players;

    /**
     * Represents the beginning of a {@link cs410.uno Uno} game
     * @param countPlayers the number of players
     * @param countInitialCardsPerPlayer the number of cards initially dealt to each player
     * @param countDigitCardsPerColor the number of {@link NormalCard normal} cards for each digit and color
     * @param countSpecialCardsPerColor the number of {@link SpecialCard special} cards of each kind for each color
     * @param countWildCards the number of total {@link WildCard wild} cards
     */
    private GameState(int countPlayers,
                     int countInitialCardsPerPlayer,
                     int countDigitCardsPerColor,
                     int countSpecialCardsPerColor,
                     int countWildCards) {
        // create draw and discard piles
        draw = new Deck(countDigitCardsPerColor, countSpecialCardsPerColor, countWildCards);
        discard = new Deck();

        players = new LinkedList<>();
        // add new players
        for (int i = 0; i < countPlayers; i++) {
            players.add(new Player("Player " + (i + 1)));
        }

        // add hands to players
        for (int i = 0; i < countInitialCardsPerPlayer; i++) {
            for (Player p : players) {
                p.addToHand(draw.drawFromDeck());
            }
        }

        // the top card in the draw pile is moved to the discard pile to begin the game
        Card top = draw.drawFromDeck();
        discard.addToDeck(top);
    }

    /**
     * After this method ends, the game state should represent the
     * situation immediately before the first player takes their first turn.
     * <p>
     * That is, the players should be arranged, their initial hands have been dealt,
     * and the discard pile and draw pile have been created.
     */
    public static GameState startGame(int countPlayers,
                                      int countInitialCardsPerPlayer,
                                      int countDigitCardsPerColor,
                                      int countSpecialCardsPerColor,
                                      int countWildCards) {
        if (countPlayers >= 2 && countInitialCardsPerPlayer > 0) {
            return new GameState(countPlayers,
                                 countInitialCardsPerPlayer,
                                 countDigitCardsPerColor,
                                 countSpecialCardsPerColor,
                                 countWildCards);
        }
        else {
            throw new IllegalArgumentException("Invalid values detected");
        }
    }

    /**
     * @return the name of the current player
     */
    String getCurrentPlayer() {
        Player p = players.peekFirst();
        if (p == null) {
            throw new NoSuchElementException("No players found");
        }
        return p.getName();
    }

    /**
     * Indicates whether the game is over.
     */
    boolean isGameOver() {
        for (Player p : players) {
            if (p.hasEmptyHand()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Shifts the players in the forward direction
     * <p>
     * If the current order is {@code 1 > 2 > 3 > 4}, then the new order will be {@code 2 > 3 > 4 > 1}
     */
    void initiateForwardDirection() {
        Player p = players.removeFirst();
        players.addLast(p);
    }

    /**
     * Shifts the players in the reverse direction
     * <p>
     * If the current order is {@code 1 > 2 > 3 > 4}, then the new order will be {@code 4 > 1 > 2 > 3}
     */
    void initiateReverseDirection() {
        Player l = players.removeLast();
        players.addFirst(l);
    }

    /**
     * Draws two cards from the draw pile and adds them to the next player's hand
     */
    void drawTwoToNextPlayer() {
        Player n = players.peekFirst();
        if (n != null) {
            for (int i = 0; i < 2; i++) {
                n.addToHand(draw.drawFromDeck());
            }
        }
    }

    /**
     * Checks if the draw pile is empty, and if so, adds the discard pile to it
     */
    void checkDecks() {
        // if draw pile is empty, add discard pile to it and shuffle
        if (draw.isDeckEmpty()) {
            // save the current top card from discard pile
            Card c = discard.drawFromDeck();

            draw.addToDeck(discard);
            draw.shuffleDeck();

            discard.clearDeck();
            discard.addToDeck(c);
        }
    }

    /**
     * The current player takes their turn, and if they play a special card
     * the corresponding effects are performed. When the method returns,
     * the next player is ready to take their turn.
     * If the game is already over, this method has no effect.
     */
    public void runOneTurn() {
        if (!isGameOver()) {
            // get current player
            Player p = players.peekFirst();
            if (p == null) {
                throw new NoSuchElementException("No players found");
            }

            // get previously played card from discard pile
            Card lastPlayed = discard.getTopCard();

            // check for direction and shift
            if (direction) {
                initiateForwardDirection();
            } else {
                initiateReverseDirection();
            }

            checkDecks();

            Card next = p.playCard(lastPlayed);
            // check if the player has any playable cards
            while (next == null) {
                p.addToHand(draw.drawFromDeck());
                next = p.playCard(lastPlayed);
            }

            // check type of the next card
            switch (next) {
                case NormalCard n:
                    discard.addToDeck(n);
                    break;
                case SpecialCard s:
                    switch (s.getValue()) {
                        case "Draw Two":
                            checkDecks();
                            drawTwoToNextPlayer();
                            break;
                        case "Reverse":
                            direction = !direction;
                            break;
                        case "Skip":
                            // check for direction and shift
                            if (direction) {
                                initiateForwardDirection();
                            } else {
                                initiateReverseDirection();
                            }
                            break;
                    }
                    discard.addToDeck(s);
                    return;
                case WildCard w:
                    w.setRandomEffectiveColor();
                    discard.addToDeck(w);
                    return;
                default:
                    break;
            }
        }
    }

    public static void main(String[] args) {
        GameState g = GameState.startGame(4, 7, 2, 2, 4);
        while (!g.isGameOver()) {
            System.out.println(g.getCurrentPlayer() + "'s turn");
            System.out.println("The last played card is " + g.getDiscard().getTopCard());
            g.runOneTurn();
        }
        System.out.println("Game over!");
    }
}
