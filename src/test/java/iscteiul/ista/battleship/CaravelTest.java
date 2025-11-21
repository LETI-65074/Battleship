package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes completos para Caravel (tamanho 2)")
class CaravelTest {

    @Test
    @DisplayName("Caravel ocupa duas células na orientação EAST")
    void caravelEastOccupiesTwoCells() {
        Caravel c = new Caravel(Compass.EAST, new Position(3, 3));
        assertEquals(2, c.getSize());
        List<IPosition> positions = c.getPositions();
        assertEquals(2, positions.size());
        assertTrue(c.occupies(new Position(3, 3)));
        assertTrue(c.occupies(new Position(3, 4)));
        assertFalse(c.occupies(new Position(3, 5)));
    }

    @Test
    @DisplayName("Caravel ocupa duas células na orientação WEST")
    void caravelWestOccupiesTwoCells() {
        Caravel c = new Caravel(Compass.WEST, new Position(1, 1));
        assertEquals(2, c.getSize());
        assertTrue(c.occupies(new Position(1, 1)));
        assertTrue(c.occupies(new Position(1, 2)));
    }

    @Test
    @DisplayName("Caravel ocupa duas células na orientação NORTH")
    void caravelNorthOccupiesTwoCells() {
        Caravel c = new Caravel(Compass.NORTH, new Position(2, 2));
        assertEquals(2, c.getSize());
        assertTrue(c.occupies(new Position(2, 2)));
        assertTrue(c.occupies(new Position(3, 2)));
    }

    @Test
    @DisplayName("Caravel ocupa duas células na orientação SOUTH")
    void caravelSouthOccupiesTwoCells() {
        Caravel c = new Caravel(Compass.SOUTH, new Position(4, 7));
        assertEquals(2, c.getSize());
        assertTrue(c.occupies(new Position(4, 7)));
        assertTrue(c.occupies(new Position(5, 7)));
    }

    @Test
    @DisplayName("Caravel afunda após dois hits")
    void caravelSinksAfterTwoHits() {
        Caravel c = new Caravel(Compass.EAST, new Position(6, 6));
        assertTrue(c.stillFloating());
        c.shoot(new Position(6, 6));
        assertTrue(c.stillFloating());

        Caravel c2 = new Caravel(Compass.SOUTH, new Position(8, 8));
        for (IPosition p : c2.getPositions()) {
            c2.shoot(new Position(p.getRow(), p.getColumn()));
        }
        assertFalse(c2.stillFloating());
    }

    @Test
    @DisplayName("Caravel lança AssertionError quando bearing é null (Ship lança AssertionError)")
    void caravelNullBearingThrows() {
        assertThrows(AssertionError.class, () ->
                new Caravel(null, new Position(0, 0)));
    }

    @Test
    @DisplayName("Caravel lança IllegalArgumentException quando bearing é UNKNOWN")
    void caravelUnknownBearingThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new Caravel(Compass.UNKNOWN, new Position(0, 0)));
    }

    @Test
    @DisplayName("tooCloseTo detecta adjacências (inclui diagonais)")
    void caravelTooCloseToDetectsAdjacency() {
        Caravel c = new Caravel(Compass.EAST, new Position(4, 4));
        assertTrue(c.tooCloseTo(new Position(3, 3)));
        assertTrue(c.tooCloseTo(new Position(4, 5)));
        assertFalse(c.tooCloseTo(new Position(10, 10)));
    }

    @Test
    @DisplayName("tooCloseTo(IShip) é true quando duas Caravelas estão adjacentes")
    void caravelTooCloseToAnotherCaravelAdjacent() {
        // Primeira caravela em (4,4)-(4,5)
        Caravel c1 = new Caravel(Compass.EAST, new Position(4, 4));
        // Segunda caravela começa em (4,6)-(4,7), imediatamente ao lado
        Caravel c2 = new Caravel(Compass.EAST, new Position(4, 6));

        assertTrue(c1.tooCloseTo(c2) || c2.tooCloseTo(c1),
                "Caravelas adjacentes devem ser consideradas tooCloseTo");
    }

    @Test
    @DisplayName("tooCloseTo(IShip) é false quando Caravelas estão afastadas")
    void caravelNotTooCloseToFarCaravel() {
        Caravel c1 = new Caravel(Compass.EAST, new Position(0, 0));
        Caravel c2 = new Caravel(Compass.EAST, new Position(10, 10));

        assertAll(
                () -> assertFalse(c1.tooCloseTo(c2),
                        "Caravelas afastadas não devem ser tooCloseTo"),
                () -> assertFalse(c2.tooCloseTo(c1),
                        "A relação deve ser falsa nos dois sentidos")
        );
    }


}
