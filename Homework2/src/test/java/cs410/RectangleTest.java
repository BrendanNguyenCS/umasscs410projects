package cs410;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {

    @Test
    @DisplayName("Rectangle: static factory method")
    void of() {
    }

    @Test
    @DisplayName("Rectangle: area")
    void area() {
        // given a Rectangle with coordinates (3,0) and (13,3), should return 39
        assertEquals(39, Rectangle.of(3, 0, 16, 3).area());
        // given a Rectangle with coordinates (3,0) and (3,10), should return 0
        assertEquals(0, Rectangle.of(3, 0, 3, 10).area());
    }

    @Test
    @DisplayName("Rectangle: contains")
    void contains() {
        // given a Rectangle with coordinates (3,0) and (13,3) and a point (2,12), should return true
        assertTrue(Rectangle.of(3, 0, 13, 3).contains(11, 2));
        // given a Rectangle with coordinates (3,0) and (13,3) and a point (7,5), should return false
        assertFalse(Rectangle.of(3, 0, 13, 3).contains(7, 5));
    }

    @Test
    @DisplayName("Rectangle: overlaps")
    void overlaps() {
        // given Rectangles with coordinates (3,0) and (13,3) and (11,2) and (14,4), should return true
        assertTrue(Rectangle.of(3, 0, 13, 3).overlaps(Rectangle.of(11, 2, 14, 4)));
        // given Rectangles with coordinates (3,0) and (13,3) and (14,4) and (15,6), should return false
        assertFalse(Rectangle.of(3, 0, 13, 3).overlaps(Rectangle.of(14, 4, 15, 6)));
    }

    @Test
    @DisplayName("Rectangle: equals")
    void equals() {
        // given Rectangles {(3,0), (13,3)} and {(13,3), (3,0)}, should return true
        assertEquals(Rectangle.of(3, 0, 13, 3), Rectangle.of(13, 3, 3, 0));
        // given Rectangles {(3,0), (13,3)} and {(13,3), (7, 5)}, should return false
        assertNotEquals(Rectangle.of(3, 0, 13, 3), Rectangle.of(13, 3, 7, 5));
    }
}