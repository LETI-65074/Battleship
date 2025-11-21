package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes completos para Carrack (tamanho 3)")
class CarrackTest {

    @Test
    @DisplayName("Carrack ocupa três células na orientação EAST")
    void carrackEastOccupiesThreeCells() {
        Carrack n = new Carrack(Compass.EAST, new Position(2, 2));
        assertEquals(3, n.getSize());
        assertTrue(n.occupies(new Position(2, 2)));
        assertTrue(n.occupies(new Position(2, 3)));
        assertTrue(n.occupies(new Position(2, 4)));
        assertFalse(n.occupies(new Position(2, 5)));
    }

    @Test
    @DisplayName("Carrack ocupa três células na orientação WEST")
    void carrackWestOccupiesThreeCells() {
        Carrack n = new Carrack(Compass.WEST, new Position(1, 1));
        assertEquals(3, n.getSize());
        assertTrue(n.occupies(new Position(1, 1)));
        assertTrue(n.occupies(new Position(1, 2)));
        assertTrue(n.occupies(new Position(1, 3)));
    }

    @Test
    @DisplayName("Carrack ocupa três células na orientação NORTH")
    void carrackNorthOccupiesThreeCells() {
        Carrack n = new Carrack(Compass.NORTH, new Position(3, 3));
        assertEquals(3, n.getSize());
        assertTrue(n.occupies(new Position(3, 3)));
        assertTrue(n.occupies(new Position(4, 3)));
        assertTrue(n.occupies(new Position(5, 3)));
    }

    @Test
    @DisplayName("Carrack ocupa três células na orientação SOUTH")
    void carrackSouthOccupiesThreeCells() {
        Carrack n = new Carrack(Compass.SOUTH, new Position(4, 0));
        assertEquals(3, n.getSize());
        assertTrue(n.occupies(new Position(4, 0)));
        assertTrue(n.occupies(new Position(5, 0)));
        assertTrue(n.occupies(new Position(6, 0)));
    }

    @Test
    @DisplayName("Carrack parcialmente atingido flutua; afunda após 3 hits")
    void carrackHitsAndSinking() {
        Carrack n = new Carrack(Compass.EAST, new Position(6, 6));
        List<IPosition> posList = n.getPositions();
        assertEquals(3, posList.size());

        n.shoot(posList.get(0));
        assertTrue(n.stillFloating());

        for (int i = 1; i < posList.size(); i++) {
            n.shoot(posList.get(i));
        }
        assertFalse(n.stillFloating());
    }

    @Test
    @DisplayName("Carrack lança AssertionError quando bearing é null (Ship lança AssertionError)")
    void carrackNullBearingThrows() {
        assertThrows(AssertionError.class, () ->
                new Carrack(null, new Position(0, 0)));
    }

    @Test
    @DisplayName("Carrack lança IllegalArgumentException quando bearing é UNKNOWN")
    void carrackUnknownBearingThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new Carrack(Compass.UNKNOWN, new Position(0, 0)));
    }

    @Test
    @DisplayName("tooCloseTo detecta adjacências (inclui diagonais)")
    void carrackTooCloseToDetectsAdjacency() {
        Carrack n = new Carrack(Compass.SOUTH, new Position(5, 5));
        assertTrue(n.tooCloseTo(new Position(5, 6)));
        assertTrue(n.tooCloseTo(new Position(6, 6)));
        assertFalse(n.tooCloseTo(new Position(10, 10)));
    }
}
