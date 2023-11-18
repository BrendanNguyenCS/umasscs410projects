package cs410;

/**
 * Represents a rectangle on a 2D grid with integer coordinates
 */
public class Rectangle {

    /**
     * Coordinates of one corner of this {@link Rectangle} formatted as {@code (x1,y1)}
     */
    private final int x1, y1;
    /**
     * Coordinate of the opposite corner of this {@link Rectangle} formatted as {@code (x2,y2)}
     */
    private final int x2, y2;

    /**
     * Constructs a new {@link Rectangle} object.
     * @param x1 the x-coordinate of the first corner
     * @param y1 the y-coordinate of the first corner
     * @param x2 the x-coordinate of the opposite corner
     * @param y2 the y-coordinate of the opposite corner
     */
    public Rectangle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Static factory method
     * @param x1 the x-coordinate of the first corner
     * @param y1 the y-coordinate of the first corner
     * @param x2 the x-coordinate of the opposite corner
     * @param y2 the y-coordinate of the opposite corner
     */
    public static Rectangle of(int x1, int y1, int x2, int y2) {
        return new Rectangle(x1, y1, x2, y2);
    }

    /**
     * @return the area of this {@link Rectangle}
     */
    public double area() {
        return Math.abs(x2 - x1) * Math.abs(y2 - y1);
    }

    /**
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @return {@code true} if the given point is in the bounds of the rectangle, {@code false} otherwise
     */
    public boolean contains(int x, int y) {
        // check x coordinates (it is not known if x1 <= x2 or x1 >= x2)
        if ((x >= x1 && x <= x2) || (x <= x1 && x >= x2)) {
            // check y coordinates (it is not known if y1 <= y2 or y1 >= y2)
            return (y >= y1 && y <= y2) || (y <= y1 && y >= y2);
        }
        return false;
    }

    /**
     * @param other another {@link Rectangle} object
     * @return {@code true} if the two {@link Rectangle Rectangles} overlap, {@code false} otherwise.
     * <p>
     * This method checks if any of the two opposite corners of the other {@link Rectangle} object is contained within the current {@link Rectangle} object.
     */
    public boolean overlaps(Rectangle other) {
        return this.contains(other.x1, other.y1) || this.contains(other.x2, other.y2);
    }

    /**
     * Tests the equality of two {@link Rectangle} objects by comparing their coordinates. This method is needed in order to use {@code assertEquals} in tests.
     * @param other the object being compared to
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Rectangle otherRect) {
            // Order of coordinates may not align, so we must check for both possibilities
            // i.e. a Rectangle with corners {(3,0), (13,3)} is the same as a Rectangle
            // with corners {(13,3), (3,0)}
            return (this.x1 == otherRect.x1 && this.y1 == otherRect.y1 &&
                    this.x2 == otherRect.x2 && this.y2 == otherRect.y2) ||
                   (this.x1 == otherRect.x2 && this.y1 == otherRect.y2 &&
                       this.x2 == otherRect.x1 && this.y2 == otherRect.y1);
        } else {
            return false;
        }
    }
}
