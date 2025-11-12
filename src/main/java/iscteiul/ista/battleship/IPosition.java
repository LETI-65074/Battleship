/**
 * Represents a coordinate on the game board.
 * Provides information about occupancy and hit status and basic actions
 * (occupy, shoot) as well as adjacency checks.
 *
 * Implementations are expected to be immutable in coordinates but mutable
 * in occupancy/hit state.
 *
 * @author fba
 */
package iscteiul.ista.battleship;

/**
 * @author fba
 */
public interface IPosition {

    /**
     * Returns the row index of this position.
     *
     * @return row index
     */
    int getRow();

    /**
     * Returns the column index of this position.
     *
     * @return column index
     */
    int getColumn();

    /**
     * Equality is based on row and column coordinates only.
     *
     * @param other object to compare
     * @return true if coordinates are the same
     */
    boolean equals(Object other);

    /**
     * Checks whether this position is adjacent (including diagonals) to {@code other}.
     *
     * @param other position to compare
     * @return true if positions are at most 1 cell apart in both dimensions
     */
    boolean isAdjacentTo(IPosition other);

    /**
     * Mark this position as occupied by a ship.
     */
    void occupy();

    /**
     * Mark this position as having been shot (hit).
     */
    void shoot();

    /**
     * Indicates whether this position is occupied by a ship.
     *
     * @return true if occupied
     */
    boolean isOccupied();

    /**
     * Indicates whether this position has been shot.
     *
     * @return true if hit
     */
    boolean isHit();
}
