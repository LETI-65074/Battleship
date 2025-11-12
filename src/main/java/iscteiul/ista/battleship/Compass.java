/**
 * Compass directions used to orient ships on the board.
 * Each constant exposes a single-character representation.
 *
 * NORTH and SOUTH represent vertical orientation, EAST and WEST horizontal,
 * UNKNOWN is used for unrecognized characters.
 */
package iscteiul.ista.battleship;

/**
 * @author fba
 */
public enum Compass {
    NORTH('n'), SOUTH('s'), EAST('e'), WEST('o'), UNKNOWN('u');

    private final char c;

    Compass(char c) {
        this.c = c;
    }

    /**
     * Return the single-character representation of this compass direction.
     *
     * @return character representing this direction (e.g. 'n', 's', 'e', 'o')
     */
    public char getDirection() {
        return c;
    }

    /**
     * Return a human-readable string for this compass constant.
     *
     * @return single-character string matching getDirection()
     */
    @Override
    public String toString() {
        return "" + c;
    }

    /**
     * Convert a char into the corresponding Compass constant.
     *
     * @param ch input character (lowercase expected: 'n','s','e','o')
     * @return matching Compass constant, or UNKNOWN if not recognized
     */
    static Compass charToCompass(char ch) {
        Compass bearing;
        switch (ch) {
            case 'n':
                bearing = NORTH;
                break;
            case 's':
                bearing = SOUTH;
                break;
            case 'e':
                bearing = EAST;
                break;
            case 'o':
                bearing = WEST;
                break;
            default:
                bearing = UNKNOWN;
        }

        return bearing;
    }
}
