/**
 * Public API for a ship in the game.
 * Exposes category, orientation, occupied positions and basic interactions
 * such as checking occupancy, proximity rules and applying a shot.
 */
package iscteiul.ista.battleship;

import java.util.List;

/**
 * Public API for a ship in the game.
 * Exposes category, orientation, occupied positions and basic interactions
 * such as checking occupancy, proximity rules and applying a shot.
 */
public interface IShip {
    /**
     * Ship category/name (e.g. "caravela", "galeao").
     *
     * @return category string
     */
    String getCategory();

    /**
     * Size (number of grid cells) occupied by this ship.
     *
     * @return ship size
     */
    Integer getSize();

    /**
     * Positions occupied by the ship (reference positions).
     *
     * @return list of occupied positions
     */
    List<IPosition> getPositions();

    /**
     * Reference position used when creating the ship.
     *
     * @return reference position
     */
    IPosition getPosition();

    /**
     * Ship orientation.
     *
     * @return compass bearing
     */
    Compass getBearing();

    /**
     * Indicates whether the ship has any non-hit positions remaining.
     *
     * @return true if at least one position is not hit
     */
    boolean stillFloating();

    int getTopMostPos();

    int getBottomMostPos();

    int getLeftMostPos();

    int getRightMostPos();

    /**
     * Checks whether this ship occupies the given position.
     *
     * @param pos position to check
     * @return true if any occupied position equals {@code pos}
     */
    boolean occupies(IPosition pos);

    /**
     * Checks whether this ship is too close (adjacent) to another ship.
     *
     * @param other other ship
     * @return true if any occupied cell is adjacent to any cell of {@code other}
     */
    boolean tooCloseTo(IShip other);

    /**
     * Checks whether this ship is too close (adjacent) to the provided position.
     *
     * @param pos position to check
     * @return true if any occupied cell is adjacent to {@code pos}
     */
    boolean tooCloseTo(IPosition pos);

    /**
     * Apply a shot to this ship: if the provided position matches an occupied
     * position it should be marked as hit.
     *
     * @param pos target position
     */
    void shoot(IPosition pos);
}
