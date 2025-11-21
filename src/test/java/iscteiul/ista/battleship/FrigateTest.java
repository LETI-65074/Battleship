package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para Frigate (tamanho 4)")
class FrigateTest {

    @Test
    @DisplayName("Frigate tem tamanho 4")
    void testFrigateSize() {
        Frigate f = new Frigate(Compass.NORTH, new Position(0, 0));
        assertEquals(4, f.getSize());
        assertEquals("Fragata", f.getCategory());
    }

    @Test
    @DisplayName("Frigate ocupa 4 células na vertical (NORTH/SOUTH)")
    void testFrigateVertical() {
        // Teste NORTH
        Frigate fNorth = new Frigate(Compass.NORTH, new Position(2, 2));
        List<IPosition> posNorth = fNorth.getPositions();
        assertEquals(4, posNorth.size());
        assertTrue(fNorth.occupies(new Position(2, 2)));
        assertTrue(fNorth.occupies(new Position(3, 2)));
        assertTrue(fNorth.occupies(new Position(4, 2)));
        assertTrue(fNorth.occupies(new Position(5, 2)));

        // Teste SOUTH (lógica idêntica no código fornecido: row + r)
        Frigate fSouth = new Frigate(Compass.SOUTH, new Position(10, 10));
        assertTrue(fSouth.occupies(new Position(13, 10)));
    }

    @Test
    @DisplayName("Frigate ocupa 4 células na horizontal (EAST/WEST)")
    void testFrigateHorizontal() {
        // Teste EAST
        Frigate fEast = new Frigate(Compass.EAST, new Position(5, 5));
        List<IPosition> posEast = fEast.getPositions();
        assertEquals(4, posEast.size());
        assertTrue(fEast.occupies(new Position(5, 5)));
        assertTrue(fEast.occupies(new Position(5, 6)));
        assertTrue(fEast.occupies(new Position(5, 7)));
        assertTrue(fEast.occupies(new Position(5, 8)));

        // Teste WEST (lógica idêntica no código fornecido: col + c)
        Frigate fWest = new Frigate(Compass.WEST, new Position(0, 0));
        assertTrue(fWest.occupies(new Position(0, 3)));
    }

    @Test
    @DisplayName("Frigate lança exceção com bearing inválido ou nulo")
    void testInvalidCreation() {
        // Null Bearing: Pode lançar AssertionError (Ship) ou NullPointerException/Exception
        assertThrows(Throwable.class, () -> new Frigate(null, new Position(0, 0)));

        // Unknown Bearing: Deve lançar IllegalArgumentException (Default do switch)
        assertThrows(IllegalArgumentException.class, () -> new Frigate(Compass.UNKNOWN, new Position(0, 0)));
    }
}