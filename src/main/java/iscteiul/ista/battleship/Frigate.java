/**
 * Frigate ship implementation (length 4).
 */
package iscteiul.ista.battleship;

public class Frigate extends Ship {
    private static final Integer SIZE = 4;
    private static final String NAME = "Fragata";

    /**
     * Build a Frigate oriented along the given bearing.
     *
     * @param bearing orientation for placement
     * @param pos     starting reference position
     * @throws IllegalArgumentException if bearing is invalid
     */
    public Frigate(Compass bearing, IPosition pos) throws IllegalArgumentException {
        super(Frigate.NAME, bearing, pos);
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
                throw new IllegalArgumentException("ERROR! invalid bearing for thr frigate");
        }
    }

    /**
     * Returns the fixed size (number of cells) of this ship type.
     *
     * @return ship size in grid cells
     */
    @Override
    public Integer getSize() {
        return Frigate.SIZE;
    }

}
