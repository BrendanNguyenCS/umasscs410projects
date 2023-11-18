package cs410.uno;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class NormalCardTest {

    @Nested
    @DisplayName("Normal Card: Constructor")
    class ExceptionTests {

        @Test
        @DisplayName("valid card and color values")
        void validCard() {
            assertDoesNotThrow(() -> { new NormalCard(9, "Red"); });
        }

        @Test
        @DisplayName("invalid card value")
        void invalidValue() {
            assertThrows(IllegalArgumentException.class, () -> { new NormalCard(10, "Red"); });
        }

        @Test
        @DisplayName("invalid color value")
        void invalidColor() {
            assertThrows(IllegalArgumentException.class, () -> { new NormalCard(8, "Purple"); });
        }
    }

    @Nested
    @DisplayName("Normal Card: Playable")
    class PlayableTests {

        NormalCard red5 = new NormalCard(5, "Red");

        @Test
        @DisplayName("Red 5 is playable on Red 6")
        void playable1() {
            NormalCard red6 = new NormalCard(6, "Red");
            assertTrue(red5.isPlayable(red6));
        }

        @Test
        @DisplayName("Red 5 is playable on Red Skip")
        void playable2() {
            SpecialCard redSkip = new SpecialCard("Skip", "Red");
            assertTrue(red5.isPlayable(redSkip));
        }

        @Test
        @DisplayName("Red 5 is playable on Blue 5")
        void playable3() {
            NormalCard blue5 = new NormalCard(5, "Blue");
            assertTrue(red5.isPlayable(blue5));
        }

        @Test
        @DisplayName("Red 5 is playable on Wild with Red effective color")
        void playable4() {
            WildCard w = new WildCard();
            w.setEffectiveColor("Red");
            assertTrue(red5.isPlayable(w));
        }

        @Test
        @DisplayName("Red 5 is not playable on Green 9")
        void notPlayable1() {
            NormalCard green9 = new NormalCard(9, "Green");
            assertFalse(red5.isPlayable(green9));
        }

        @Test
        @DisplayName("Red 5 is not playable on Yellow Reverse")
        void notPlayable2() {
            SpecialCard yellowReverse = new SpecialCard("Reverse", "Yellow");
            assertFalse(red5.isPlayable(yellowReverse));
        }

        @Test
        @DisplayName("Red 5 is not playable on Wild with Green effective color")
        void notPlayable3() {
            WildCard w = new WildCard();
            w.setEffectiveColor("Green");
            assertFalse(red5.isPlayable(w));
        }
    }
}