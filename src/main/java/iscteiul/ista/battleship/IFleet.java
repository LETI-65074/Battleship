/**
 * Fleet management interface: holds ships and provides queries and printing utilities.
 */
package iscteiul.ista.battleship;

import java.util.List;

public interface IFleet {
    /** Size (rows/columns) of the square game board. */
    Integer BOARD_SIZE = 10;
    /** Default expected number of ships in a fleet. */
    Integer FLEET_SIZE = 10;

    /**
     * Return all ships currently registered in the fleet.
     *
     * @return list of ships
     */
    List<IShip> getShips();

    /**
     * Add a ship to the fleet if it fits and doesn't collide.
     *
     * @param s ship to add
     * @return true if added, false otherwise
     */
    boolean addShip(IShip s);

    /**
     * Return ships whose category matches the provided string.
     *
     * @param category category name or fragment
     * @return list of matching ships
     */
    List<IShip> getShipsLike(String category);

    /**
     * Return ships that are not yet sunk.
     *
     * @return list of floating ships
     */
    List<IShip> getFloatingShips();

    /**
     * Return the ship occupying the given position, or null if none.
     *
     * @param pos position to check
     * @return ship at position or null
     */
    IShip shipAt(IPosition pos);

    /**
     * Print a summary status of the fleet to standard output.
     */
    void printStatus();
}
