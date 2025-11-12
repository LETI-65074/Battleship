/**
 * Carrack ship implementation (length 3).
 */
package iscteiul.ista.battleship;

public class Carrack extends Ship {
    private static final Integer SIZE = 3;
    private static final String NAME = "Nau";

    /**
     * Construct a Carrack oriented along the given bearing.
     *
     * @param bearing orientation for placement
     * @param pos     reference starting position
     * @throws IllegalArgumentException if bearing is invalid
     */
    public Carrack(Compass bearing, IPosition pos) throws IllegalArgumentException {
        super(Carrack.NAME, bearing, pos);
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
                throw new IllegalArgumentException("ERROR! invalid bearing for the carrack");
        }
    }

    /**
     * Returns the fixed size (number of cells) of this ship type.
     *
     * @return ship size in grid cells
     */
    @Override
    public Integer getSize() {
        return Carrack.SIZE;
    }

}
