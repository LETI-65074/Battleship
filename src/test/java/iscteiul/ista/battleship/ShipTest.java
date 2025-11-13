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
    @Test
    @DisplayName("stillFloating é true enquanto houver posições não atingidas")
    void stillFloatingTrueWhileNotAllHit() {
        Ship ship = Ship.buildShip("galeao", Compass.EAST, new Position(5, 5));

        // Nenhuma posição foi atingida ainda
        assertTrue(ship.stillFloating());

        // Disparar só numa parte do navio
        ship.getPositions().get(0).shoot();

        // Ainda deve estar a flutuar (há posições não atingidas)
        assertTrue(ship.stillFloating());
    }

    @Test
    @DisplayName("stillFloating é false depois de todas as posições serem atingidas")
    void stillFloatingFalseAfterAllHits() {
        Ship ship = Ship.buildShip("galeao", Compass.EAST, new Position(5, 5));

        // Disparar em todas as posições do navio
        for (IPosition p : ship.getPositions()) {
            p.shoot();
        }

        assertFalse(ship.stillFloating());
    }

    @Test
    @DisplayName("getTop/Bottom/Left/RightMostPos correspondem às posições do navio")
    void extremePositionsAreComputedCorrectly() {
        Ship ship = Ship.buildShip("galeao", Compass.SOUTH, new Position(2, 2));

        List<IPosition> positions = ship.getPositions();

        int expectedTop    = positions.stream().mapToInt(IPosition::getRow).min().orElseThrow();
        int expectedBottom = positions.stream().mapToInt(IPosition::getRow).max().orElseThrow();
        int expectedLeft   = positions.stream().mapToInt(IPosition::getColumn).min().orElseThrow();
        int expectedRight  = positions.stream().mapToInt(IPosition::getColumn).max().orElseThrow();

        assertAll(
                () -> assertEquals(expectedTop,    ship.getTopMostPos()),
                () -> assertEquals(expectedBottom, ship.getBottomMostPos()),
                () -> assertEquals(expectedLeft,   ship.getLeftMostPos()),
                () -> assertEquals(expectedRight,  ship.getRightMostPos())
        );
    }

    @Test
    @DisplayName("occupies é true para posições do próprio navio e false para uma posição distante")
    void occupiesChecksPositionsCorrectly() {
        Ship ship = Ship.buildShip("caravela", Compass.EAST, new Position(4, 4));

        IPosition oneOfItsPositions = ship.getPositions().get(0);
        IPosition farAway = new Position(0, 0); // escolhe algo que saibas que não pertence ao navio

        assertAll(
                () -> assertTrue(ship.occupies(oneOfItsPositions)),
                () -> assertFalse(ship.occupies(farAway))
        );
    }

    @Test
    @DisplayName("shoot marca posição como atingida quando o tiro acerta no navio")
    void shootMarksPositionAsHit() {
        Ship ship = Ship.buildShip("barca", Compass.NORTH, new Position(7, 3));

        IPosition target = ship.getPositions().get(0);

        assertFalse(target.isHit(), "Antes do tiro, não deve estar atingida");

        ship.shoot(target);

        assertTrue(target.isHit(), "Depois do tiro, deve ficar atingida");
    }

    @Test
    @DisplayName("tooCloseTo(IShip) é true quando os navios estão adjacentes")
    void tooCloseToShipWhenAdjacent() {
        Ship s1 = Ship.buildShip("barca", Compass.NORTH, new Position(5, 5));
        // Supondo que (5,6) fica imediatamente ao lado de (5,5)
        Ship s2 = Ship.buildShip("barca", Compass.NORTH, new Position(5, 6));

        assertTrue(s1.tooCloseTo(s2) || s2.tooCloseTo(s1));
    }

    @Test
    @DisplayName("toString inclui categoria, orientação e posição")
    void toStringContainsUsefulInfo() {
        Position refPos = new Position(1, 1);
        Ship ship = Ship.buildShip("barca", Compass.NORTH, refPos);

        String str = ship.toString();

        assertAll(
                () -> assertTrue(str.contains(ship.getCategory()),
                        "toString deve conter a categoria"),
                () -> assertTrue(str.contains(ship.getBearing().toString()),
                        "toString deve conter o bearing"),
                () -> assertTrue(str.contains(ship.getPosition().toString()),
                        "toString deve conter a posição de referência")
        );
    }


}
