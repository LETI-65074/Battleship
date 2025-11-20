package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for Barge (size 1)")
class BargeTest {

    @Test
    @DisplayName("Barge has size 1 and occupies the reference position")
    void bargeSizeAndPosition() {
        IPosition ref = new Position(2, 3);
        Barge b = new Barge(Compass.EAST, ref);

        assertEquals(1, b.getSize());
        assertTrue(b.occupies(new Position(2, 3)));
        assertFalse(b.occupies(new Position(2, 4)));
    }

    @Test
    @DisplayName("Shooting the single cell sinks the barge")
    void bargeSinksOnSingleHit() {
        Barge b = new Barge(Compass.NORTH, new Position(5, 5));
        assertTrue(b.stillFloating());
        b.shoot(new Position(5, 5));
        assertFalse(b.stillFloating(), "after one hit a size-1 ship must not be still floating");
    }

    @Test
    @DisplayName("tooCloseTo reports adjacency (including diagonal)")
    void bargeTooCloseTo() {
        Barge b = new Barge(Compass.EAST, new Position(2, 2));
        // adjacent diagonal
        assertTrue(b.tooCloseTo(new Position(3, 3)));
        // far away
        assertFalse(b.tooCloseTo(new Position(5, 5)));
    }
}
