package cs410.uno;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    @Nested
    @DisplayName("Game State: start game")
    class startGame {

        @Nested
        @DisplayName("Exceptions")
        class startGameExceptions {

            @Test
            @DisplayName("Invalid number of players")
            void invalidPlayers() {
                assertThrows(IllegalArgumentException.class, () -> { GameState.startGame(1, 1, 1, 1, 1); });
            }

            @Test
            @DisplayName("Invalid initial cards per player")
            void invalidInitialCards() {
                assertThrows(IllegalArgumentException.class, () -> { GameState.startGame(2, 0, 1, 1, 1); });
            }

            @Test
            @DisplayName("Invalid count digit cards per color")
            void invalidDigitCount() {
                assertThrows(IllegalArgumentException.class, () -> { GameState.startGame(2, 2, 0, 1, 1); });
            }

            @Test
            @DisplayName("Invalid count special cards per color")
            void invalidSpecialCount() {
                assertThrows(IllegalArgumentException.class, () -> { GameState.startGame(2, 2, 1, -1, 1); });
            }

            @Test
            @DisplayName("Invalid count wild cards")
            void invalidWildCount() {
                assertThrows(IllegalArgumentException.class, () -> { GameState.startGame(2, 2, 0, 0, -1); });
            }
        }
    }

    @Test
    public void isGameOver() {
    }

    @Test
    public void runOneTurn() {
    }
}