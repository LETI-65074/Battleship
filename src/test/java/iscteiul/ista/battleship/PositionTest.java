package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void getRow() {
        Position p = new Position(3, 5);
        assertEquals(3, p.getRow());
    }

    @Test
    void getColumn() {
        Position p = new Position(3, 5);
        assertEquals(5, p.getColumn());
    }

    @Test
    void testHashCode() {
        Position p1 = new Position(2, 4);
        Position p2 = new Position(2, 4);
        Position p3 = new Position(3, 4);

        assertEquals(p1.hashCode(), p2.hashCode());   // iguais → mesmo hash
        assertNotEquals(p1.hashCode(), p3.hashCode()); // diferentes → hash diferente
    }

    @Test
    void testEquals() {
    }

    @Test
    void isAdjacentTo() {
    }

    @Test
    void occupy() {
    }

    @Test
    void shoot() {
    }

    @Test
    void isOccupied() {
    }

    @Test
    void isHit() {
    }

    @Test
    void testToString() {
    }
}