package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes para Galleon (tamanho 5)")
class GalleonTest {

    @Test
    @DisplayName("Galleon tem tamanho 5 e nome correto")
    void testGalleonProperties() {
        Galleon g = new Galleon(Compass.NORTH, new Position(5, 5));
        assertEquals(5, g.getSize());
        // Nota: No código do Galleon.java a constante é "Galeao" (sem til), verifica se bate certo
        assertEquals("Galeao", g.getCategory());
    }

    @Test
    @DisplayName("Galleon Geometria NORTH (Formato T)")
    void testGalleonNorth() {
        // fillNorth: (r,c), (r,c+1), (r,c+2) E (r+1,c+1), (r+2,c+1)
        IPosition start = new Position(2, 2);
        Galleon g = new Galleon(Compass.NORTH, start);

        assertEquals(5, g.getPositions().size());

        // Barra horizontal superior
        assertTrue(g.occupies(new Position(2, 2)));
        assertTrue(g.occupies(new Position(2, 3)));
        assertTrue(g.occupies(new Position(2, 4)));

        // Haste vertical central
        assertTrue(g.occupies(new Position(3, 3)));
        assertTrue(g.occupies(new Position(4, 3)));
    }

    @Test
    @DisplayName("Galleon Geometria SOUTH (Formato T invertido)")
    void testGalleonSouth() {
        // fillSouth: (r,c), (r+1,c) E (r+2,c-1), (r+2,c), (r+2,c+1)
        IPosition start = new Position(4, 4);
        Galleon g = new Galleon(Compass.SOUTH, start);

        // Haste vertical superior
        assertTrue(g.occupies(new Position(4, 4)));
        assertTrue(g.occupies(new Position(5, 4)));

        // Barra horizontal inferior
        assertTrue(g.occupies(new Position(6, 3))); // 4 + (j-3) onde j=2 -> 4-1 = 3
        assertTrue(g.occupies(new Position(6, 4)));
        assertTrue(g.occupies(new Position(6, 5)));
    }

    @Test
    @DisplayName("Galleon Geometria EAST")
    void testGalleonEast() {
        // fillEast: (r,c) E (r+1,c-2 até c) E (r+2,c)
        IPosition start = new Position(5, 5);
        Galleon g = new Galleon(Compass.EAST, start);

        assertTrue(g.occupies(new Position(5, 5))); // Topo

        // Linha do meio (c+i-3) -> i=1 (c-2), i=2 (c-1), i=3 (c)
        assertTrue(g.occupies(new Position(6, 3)));
        assertTrue(g.occupies(new Position(6, 4)));
        assertTrue(g.occupies(new Position(6, 5)));

        assertTrue(g.occupies(new Position(7, 5))); // Fundo
    }

    @Test
    @DisplayName("Galleon Geometria WEST")
    void testGalleonWest() {
        // fillWest: (r,c) E (r+1,c até c+2) E (r+2,c)
        IPosition start = new Position(2, 2);
        Galleon g = new Galleon(Compass.WEST, start);

        assertTrue(g.occupies(new Position(2, 2))); // Topo

        // Linha do meio (c+i-1) -> i=1 (c), i=2 (c+1), i=3 (c+2)
        assertTrue(g.occupies(new Position(3, 2)));
        assertTrue(g.occupies(new Position(3, 3)));
        assertTrue(g.occupies(new Position(3, 4)));

        assertTrue(g.occupies(new Position(4, 2))); // Fundo
    }

    @Test
    @DisplayName("Galleon lança exceção com bearing null (Robustez)")
    void testGalleonNullBearing() {
        // Aceita AssertionError (do Ship) ou NullPointerException (do Galleon)
        Throwable exception = assertThrows(Throwable.class, () ->
                new Galleon(null, new Position(0, 0))
        );
        assertTrue(exception instanceof AssertionError || exception instanceof NullPointerException);
    }

    @Test
    @DisplayName("Galleon lança exceção com bearing UNKNOWN")
    void testGalleonUnknownBearing() {
        assertThrows(IllegalArgumentException.class, () ->
                new Galleon(Compass.UNKNOWN, new Position(0, 0))
        );
    }
}