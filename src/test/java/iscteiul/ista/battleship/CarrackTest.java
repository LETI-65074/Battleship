package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for Carrack (size 3)")
class CarrackTest {

    @Test
    @DisplayName("Carrack (NORTH) occupies 3 vertical cells")
    void carrackNorthPositions() throws Exception {
        IPosition ref = new Position(2, 2);
        Carrack carr = new Carrack(Compass.NORTH, ref);

        assertEquals(3, carr.getSize());
        assertTrue(carr.occupies(new Position(2,2)));
        assertTrue(carr.occupies(new Position(3,2)));
        assertTrue(carr.occupies(new Position(4,2)));
    }

    @Test
    @DisplayName("Partial hits keep ship floating; full hits sink it")
    void carrackHitsBehavior() throws Exception {
        Carrack carr = new Carrack(Compass.EAST, new Position(6, 6));
        // hit middle only
        carr.shoot(new Position(6,7));
        assertTrue(carr.stillFloating());
        // hit remaining
        carr.shoot(new Position(6,6));
        carr.shoot(new Position(6,8));
        assertFalse(carr.stillFloating());
    }

    @Test
    @DisplayName("Carrack invalid bearing is rejected")
    void carrackInvalidBearing() {
        assertThrows(IllegalArgumentException.class, () -> new Carrack(Compass.UNKNOWN, new Position(0,0)));
    }
}
