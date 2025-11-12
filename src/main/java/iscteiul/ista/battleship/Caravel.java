/**
 * Small two-cell ship implementation (Caravel).
 * The Caravel occupies SIZE consecutive cells in the given bearing
 * starting at the provided position.
 */
package iscteiul.ista.battleship;

public class Caravel extends Ship {
    private static final Integer SIZE = 2;
    private static final String NAME = "Caravela";

    /**
     * Create a Caravel oriented along the given bearing with its
     * reference position at pos.
     *
     * @param bearing the bearing where the Caravel heads to (orientation)
     * @param pos     initial point for positioning the Caravel (reference cell)
     * @throws NullPointerException     if bearing is null
     * @throws IllegalArgumentException if the bearing value is unsupported
     */
    public Caravel(Compass bearing, IPosition pos) throws NullPointerException, IllegalArgumentException {
        super(Caravel.NAME, bearing, pos);

        if (bearing == null)
            throw new NullPointerException("ERROR! invalid bearing for the caravel");

        switch (bearing) {
            case NORTH:
            case SOUTH:
                for (int r = 0; r < SIZE; r++)
                    getPositions().add(new Position(pos.getRow() + r, pos.getColumn()));
                break;
            case EAST:
            case WEST:
                for (int c = 0; c < SIZE; c++)
                    getPositions().add(new Position(pos.getRow(), pos.getColumn() + c));
                break;
            default:
                throw new IllegalArgumentException("ERROR! invalid bearing for the caravel");
        }

    }

    /**
     * Returns the fixed size (number of cells) of this ship type.
     *
     * @return ship size in grid cells
     */
    @Override
    public Integer getSize() {
        return SIZE;
    }

}
