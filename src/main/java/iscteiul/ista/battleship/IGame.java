/**
 * Game API for firing at positions and querying shot statistics.
 */
package iscteiul.ista.battleship;

import java.util.List;

public interface IGame {
    /**
     * Fire a shot at the given board position.
     *
     * @param pos the target position to fire at
     * @return the ship hit at that position, or null if the shot missed or sank nothing
     */
    IShip fire(IPosition pos);

    /**
     * Returns the list of all positions that have been fired upon.
     *
     * @return list of fired positions (in chronological order)
     */
    List<IPosition> getShots();

    /**
     * Number of shots that repeated a previously fired position.
     *
     * @return repeated shot count
     */
    int getRepeatedShots();

    /**
     * Number of invalid shots (e.g., out of bounds).
     *
     * @return invalid shot count
     */
    int getInvalidShots();

    /**
     * Number of successful hits recorded.
     *
     * @return hit count
     */
    int getHits();

    /**
     * Number of ships that have been sunk.
     *
     * @return sunk ships count
     */
    int getSunkShips();

    /**
     * Number of ships still floating (not sunk).
     *
     * @return remaining ship count
     */
    int getRemainingShips();

    /**
     * Print a board view showing valid shots.
     */
    void printValidShots();

    /**
     * Print a board view showing the fleet positions.
     */
    void printFleet();
}
