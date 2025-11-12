/**
 * Single-cell ship (Barge).
 */
package iscteiul.ista.battleship;

public class Barge extends Ship {
    private static final Integer SIZE = 1;
    private static final String NAME = "Barca";

    /**
     * Create a single-cell Barge at the provided position.
     *
     * @param bearing the bearing of the barge (ignored for size 1 but kept for API consistency)
     * @param pos     upper-left position of the barge (its single occupied cell)
     */
    public Barge(Compass bearing, IPosition pos) {
        super(Barge.NAME, bearing, pos);
        getPositions().add(new Position(pos.getRow(), pos.getColumn()));
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
