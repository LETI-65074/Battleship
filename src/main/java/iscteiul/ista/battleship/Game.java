/**
 * Game controller handling firing, tracking shots and reporting statistics.
 */
package iscteiul.ista.battleship;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fba
 *
 */
public class Game implements IGame {
    private IFleet fleet;
    private List<IPosition> shots;

    private int countInvalidShots;
    private int countRepeatedShots;
    private int countHits;
    private int countSinks;


    /**
     * Create a Game attached to the provided fleet.
     *
     * @param fleet fleet instance that the game will manage and use for shots/placement
     */
    public Game(IFleet fleet) {
        shots = new ArrayList<>();
        this.fleet = fleet;

        countInvalidShots = 0;
        countRepeatedShots = 0;
        countHits = 0;
        countSinks = 0;
    }


    /*
     * (non-Javadoc)
     *
     * @see battleship.IGame#fire(battleship.IPosition)
     */
    @Override
    public IShip fire(IPosition pos) {
        if (!validShot(pos))
            countInvalidShots++;
        else { // valid shot!
            if (repeatedShot(pos))
                countRepeatedShots++;
            else {
                shots.add(pos);
                IShip s = fleet.shipAt(pos);
                if (s != null) {
                    s.shoot(pos);
                    countHits++;
                    if (!s.stillFloating()) {
                        countSinks++;
                        return s;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns the list of all recorded shots.
     *
     * @return list of positions fired upon
     */
    @Override
    public List<IPosition> getShots() {
        return shots;
    }

    /**
     * Number of repeated shots (shots targeting an already fired position).
     *
     * @return repeated shot count
     */
    @Override
    public int getRepeatedShots() {
        return this.countRepeatedShots;
    }

    /**
     * Number of invalid shots (e.g. out of bounds).
     *
     * @return invalid shot count
     */
    @Override
    public int getInvalidShots() {
        return this.countInvalidShots;
    }

    /**
     * Number of successful hit events recorded.
     *
     * @return hits count
     */
    @Override
    public int getHits() {
        return this.countHits;
    }

    /**
     * Number of ships sunk so far.
     *
     * @return sunk ships count
     */
    @Override
    public int getSunkShips() {
        return this.countSinks;
    }

    /**
     * Number of ships still floating.
     *
     * @return remaining ships count
     */
    @Override
    public int getRemainingShips() {
        List<IShip> floatingShips = fleet.getFloatingShips();
        return floatingShips.size();
    }

    private boolean validShot(IPosition pos) {
        return (pos.getRow() >= 0 && pos.getRow() <= Fleet.BOARD_SIZE && pos.getColumn() >= 0
                && pos.getColumn() <= Fleet.BOARD_SIZE);
    }

    private boolean repeatedShot(IPosition pos) {
        for (int i = 0; i < shots.size(); i++)
            if (shots.get(i).equals(pos))
                return true;
        return false;
    }


    /**
     * Prints the given positions on a simple ASCII board using the provided marker.
     *
     * @param positions positions to mark on the printed board
     * @param marker    character used to mark the provided positions when printing
     */
    public void printBoard(List<IPosition> positions, Character marker) {
        char[][] map = new char[Fleet.BOARD_SIZE][Fleet.BOARD_SIZE];

        for (int r = 0; r < Fleet.BOARD_SIZE; r++)
            for (int c = 0; c < Fleet.BOARD_SIZE; c++)
                map[r][c] = '.';

        for (IPosition pos : positions)
            map[pos.getRow()][pos.getColumn()] = marker;

        for (int row = 0; row < Fleet.BOARD_SIZE; row++) {
            for (int col = 0; col < Fleet.BOARD_SIZE; col++)
                System.out.print(map[row][col]);
            System.out.println();
        }

    }


    /**
     * Prints the board showing valid shots that have been fired.
     */
    public void printValidShots() {
        printBoard(getShots(), 'X');
    }


    /**
     * Prints the board showing the fleet.
     */
    public void printFleet() {
        List<IPosition> shipPositions = new ArrayList<IPosition>();

        for (IShip s : fleet.getShips())
            shipPositions.addAll(s.getPositions());

        printBoard(shipPositions, '#');
    }

}
