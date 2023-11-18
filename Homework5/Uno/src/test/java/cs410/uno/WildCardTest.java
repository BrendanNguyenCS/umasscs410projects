package cs410.uno;

import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WildCardTest {

    @Nested
    @DisplayName("Wild Card: set effective color")
    class setEffectiveColor {

        @Test
        @DisplayName("set invalid color")
        void invalidColor() {
            WildCard w = new WildCard();
            assertThrows(IllegalArgumentException.class, () -> { w.setEffectiveColor("Purple"); });
        }

        @Test
        @DisplayName("set valid color")
        void validColor() {
            WildCard w = new WildCard();
            assertDoesNotThrow(() -> { w.setEffectiveColor("Blue"); });
        }
    }

    @Nested
    @DisplayName("Wild Card: set random effective color")
    class setRandomEffectiveColor {

        @Test
        @DisplayName("None cannot be an effective color")
        void noneIsInvalid() {
            WildCard w = new WildCard();
            w.setRandomEffectiveColor();

            // We want to reset the color enough times that every color is chosen
            // Ensures that the none option is not chosen
            assertNotEquals("None", w.getColor());
            assertNotEquals("None", w.getColor());
            assertNotEquals("None", w.getColor());
            assertNotEquals("None", w.getColor());
            assertNotEquals("None", w.getColor());
            assertNotEquals("None", w.getColor());
            assertNotEquals("None", w.getColor());
            assertNotEquals("None", w.getColor());
            assertNotEquals("None", w.getColor());
        }

        @Test
        @DisplayName("Effective color is set")
        void correctSet() {
            WildCard w = new WildCard();
            w.setRandomEffectiveColor();
            assertTrue(Set.of("Red", "Yellow", "Green", "Blue").contains(w.getColor()));
        }
    }
}