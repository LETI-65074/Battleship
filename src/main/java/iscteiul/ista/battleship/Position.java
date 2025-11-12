/**
 * Concrete implementation of IPosition storing row/column and mutable
 * occupancy/hit state.
 */
package iscteiul.ista.battleship;

import java.util.Objects;

public class Position implements IPosition {
    private int row;
    private int column;
    private boolean isOccupied;
    private boolean isHit;

    /**
     * Create a position with the given coordinates.
     *
     * @param row    row index
     * @param column column index
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
        this.isOccupied = false;
        this.isHit = false;
    }

    /*
     * (non-Javadoc)
     *
     * @see battleship.IPosition#getRow()
     */
    @Override
    public int getRow() {
        return row;
    }

    /*
     * (non-Javadoc)
     *
     * @see battleship.IPosition#getColumn()
     */
    @Override
    public int getColumn() {
        return column;
    }


    @Override
    public int hashCode() {
        return Objects.hash(column, isHit, isOccupied, row);
    }

    /*
     * (non-Javadoc)
     *
     * @see battleship.IPosition#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object otherPosition) {
        if (this == otherPosition)
            return true;
        if (otherPosition instanceof IPosition) {
            IPosition other = (IPosition) otherPosition;
            return (this.getRow() == other.getRow() && this.getColumn() == other.getColumn());
        } else {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see battleship.IPosition#isAdjacentTo(battleship.IPosition)
     */
    @Override
    public boolean isAdjacentTo(IPosition other) {
        return (Math.abs(this.getRow() - other.getRow()) <= 1 && Math.abs(this.getColumn() - other.getColumn()) <= 1);
    }

    /**
     * Mark this position as occupied by a ship.
     */
    @Override
    public void occupy() {
        isOccupied = true;
    }

    /**
     * Mark this position as hit.
     */
    @Override
    public void shoot() {
        isHit = true;
    }

    /*
     * (non-Javadoc)
     *
     * @see battleship.IPosition#isOccupied()
     */
    @Override
    public boolean isOccupied() {
        return isOccupied;
    }

    /*
     * (non-Javadoc)
     *
     * @see battleship.IPosition#isHit()
     */
    @Override
    public boolean isHit() {
        return isHit;
    }

    /**
     * Human readable representation for debugging.
     *
     * @return textual representation
     */
    @Override
    public String toString() {
        return ("Linha = " + row + " Coluna = " + column);
    }
}
