package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for Caravel (size 2)")
class CaravelTest {

    @Test
    @DisplayName("Caravel horizontal (EAST) occupies two consecutive columns")
    void caravelEastPositions() throws Exception {
        IPosition ref = new Position(3, 3);
        Caravel c = new Caravel(Compass.EAST, ref);

        assertEquals(2, c.getSize());
        // expected cells: (3,3) and (3,4)
        assertTrue(c.occupies(new Position(3, 3)));
        assertTrue(c.occupies(new Position(3, 4)));
        assertFalse(c.occupies(new Position(3, 5)));
    }

    @Test
    @DisplayName("Caravel vertical (SOUTH) occupies two consecutive rows and sinks after two hits")
    void caravelSouthHits() throws Exception {
        IPosition ref = new Position(4, 7);
        Caravel c = new Caravel(Compass.SOUTH, ref);

        assertTrue(c.stillFloating());
        // hit first cell
        c.shoot(new Position(4,7));
        assertTrue(c.stillFloating(), "after 1/2 hits stillFloating should be true");
        // hit second cell
        c.shoot(new Position(5,7));
        assertFalse(c.stillFloating(), "after 2/2 hits ship should be sunk (stillFloating false)");
    }

    @Test
    @DisplayName("Caravel throws on invalid bearing")
    void caravelInvalidBearing() {
        assertThrows(IllegalArgumentException.class, () -> new Caravel(Compass.UNKNOWN, new Position(1,1)));
    }
}
