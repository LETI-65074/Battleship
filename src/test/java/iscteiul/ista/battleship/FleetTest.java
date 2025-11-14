package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FleetTest {

    @Test
    void printShips() {
        // preparar lista de navios
        List<IShip> ships = new ArrayList<>();
        ships.add(new Barge(Compass.NORTH, new Position(0, 0)));
        ships.add(new Barge(Compass.EAST, new Position(1, 1)));

        // capturar System.out
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            Fleet.printShips(ships);
        } finally {
            System.setOut(originalOut); // repor System.out
        }

        String output = outContent.toString();

        assertAll(
                () -> assertTrue(output.contains(ships.get(0).toString())),
                () -> assertTrue(output.contains(ships.get(1).toString()))
        );
    }

    @Test
    void getShips() {
        Fleet fleet = new Fleet();

        IShip ship1 = new Barge(Compass.NORTH, new Position(0, 0));
        IShip ship2 = new Barge(Compass.EAST, new Position(5, 5)); // bem longe do primeiro

        // inicialmente deve estar vazia
        assertTrue(fleet.getShips().isEmpty());

        // adicionar navios
        fleet.addShip(ship1);
        fleet.addShip(ship2);

        var ships = fleet.getShips();

        assertEquals(2, ships.size());
        assertTrue(ships.contains(ship1));
        assertTrue(ships.contains(ship2));
    }

    @Test
    void addShip() {
    }

    @Test
    void getShipsLike() {
    }

    @Test
    void getFloatingShips() {
    }

    @Test
    void shipAt() {
    }

    @Test
    void printStatus() {
    }

    @Test
    void printShipsByCategory() {
    }

    @Test
    void printFloatingShips() {
    }

    @Test
    void printAllShips() {
    }
}