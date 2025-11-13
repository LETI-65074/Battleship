package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes à enumeração Compass")
class CompassTest {
    @Test
    @DisplayName("getDirection deve devolver o char associado a cada constante")
    void testGetDirection() {
        assertAll(
                () -> assertEquals('n', Compass.NORTH.getDirection()),
                () -> assertEquals('s', Compass.SOUTH.getDirection()),
                () -> assertEquals('e', Compass.EAST.getDirection()),
                () -> assertEquals('o', Compass.WEST.getDirection()),
                () -> assertEquals('u', Compass.UNKNOWN.getDirection())
        );
    }

    @Test
    @DisplayName("toString deve devolver uma string com o mesmo char de getDirection")
    void testToStringMatchesDirection() {
        assertAll(
                () -> assertEquals(String.valueOf(Compass.NORTH.getDirection()), Compass.NORTH.toString()),
                () -> assertEquals(String.valueOf(Compass.SOUTH.getDirection()), Compass.SOUTH.toString()),
                () -> assertEquals(String.valueOf(Compass.EAST.getDirection()), Compass.EAST.toString()),
                () -> assertEquals(String.valueOf(Compass.WEST.getDirection()), Compass.WEST.toString()),
                () -> assertEquals(String.valueOf(Compass.UNKNOWN.getDirection()), Compass.UNKNOWN.toString())
        );
    }

}