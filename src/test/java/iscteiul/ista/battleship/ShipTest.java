package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Test
    @DisplayName("buildShip devolve o subtipo correto para cada categoria conhecida")
    void buildShipCreatesCorrectSubtype() {
        Position pos = new Position(3, 4);

        Ship barca    = Ship.buildShip("barca",    Compass.EAST,  pos);
        Ship caravela = Ship.buildShip("caravela", Compass.NORTH, pos);
        Ship nau      = Ship.buildShip("nau",      Compass.SOUTH, pos);
        Ship fragata  = Ship.buildShip("fragata",  Compass.WEST,  pos);
        Ship galeao   = Ship.buildShip("galeao",   Compass.EAST,  pos);

        assertAll(
                () -> assertNotNull(barca),
                () -> assertTrue(barca instanceof Barge),

                () -> assertNotNull(caravela),
                () -> assertTrue(caravela instanceof Caravel),

                () -> assertNotNull(nau),
                () -> assertTrue(nau instanceof Carrack),

                () -> assertNotNull(fragata),
                () -> assertTrue(fragata instanceof Frigate),

                () -> assertNotNull(galeao),
                () -> assertTrue(galeao instanceof Galleon)
        );
    }

    @Test
    @DisplayName("buildShip devolve null para categorias desconhecidas")
    void buildShipUnknownKindReturnsNull() {
        Ship unknown = Ship.buildShip("submarino", Compass.NORTH, new Position(0, 0));
        assertNull(unknown);
    }
}
