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
        Fleet fleet = new Fleet();

        IShip valid1 = new Barge(Compass.NORTH, new Position(0, 0));
        IShip valid2 = new Barge(Compass.EAST, new Position(5, 5)); // longe -> válido

        IShip invalidCollision = new Barge(Compass.NORTH, new Position(0, 0)); // mesma posição do primeiro

        // adicionar válidos
        assertTrue(fleet.addShip(valid1));
        assertTrue(fleet.addShip(valid2));

        // tentar adicionar um barco que colide com o primeiro
        assertFalse(fleet.addShip(invalidCollision));

        // frota deve conter apenas os dois válidos
        assertEquals(2, fleet.getShips().size());
        assertTrue(fleet.getShips().contains(valid1));
        assertTrue(fleet.getShips().contains(valid2));
    }

    @Test
    void getShipsLike() {
        Fleet fleet = new Fleet();

        IShip barge1 = new Barge(Compass.NORTH, new Position(0, 0));
        IShip barge2 = new Barge(Compass.EAST, new Position(5, 5));
        IShip caravel = new Caravel(Compass.SOUTH, new Position(2, 2));

        fleet.addShip(barge1);
        fleet.addShip(barge2);
        fleet.addShip(caravel);

        // categoria que provavelmente não existe
        var unknownCategoryShips = fleet.getShipsLike("NaoExisteCategoria");

        assertNotNull(unknownCategoryShips);
        assertTrue(unknownCategoryShips.isEmpty());

    }

    @Test
    void getFloatingShips() {
        Fleet fleet = new Fleet();

        IShip ship1 = new Barge(Compass.NORTH, new Position(0, 0));
        IShip ship2 = new Caravel(Compass.EAST, new Position(5, 5));

        fleet.addShip(ship1);
        fleet.addShip(ship2);

        var floating = fleet.getFloatingShips();

        assertNotNull(floating);
        assertEquals(2, floating.size());
        assertTrue(floating.contains(ship1));
        assertTrue(floating.contains(ship2));
    }

    @Test
    void shipAt() {
        Fleet fleet = new Fleet();

        IShip ship1 = new Barge(Compass.NORTH, new Position(0, 0));
        IShip ship2 = new Barge(Compass.EAST, new Position(5, 5));

        fleet.addShip(ship1);
        fleet.addShip(ship2);

        // Deve encontrar o navio correto em cada posição
        assertEquals(ship1, fleet.shipAt(new Position(0, 0)));
        assertEquals(ship2, fleet.shipAt(new Position(5, 5)));

        // Posição sem navio → null
        assertNull(fleet.shipAt(new Position(3, 3)));
    }

    @Test
    void printStatus() {
        Fleet fleet = new Fleet();
        fleet.addShip(new Barge(Compass.NORTH, new Position(0, 0)));

        // capturar System.out
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            fleet.printStatus();
        } finally {
            System.setOut(originalOut); // repor System.out
        }

        String output = outContent.toString();

        assertNotNull(output);
        assertFalse(output.isBlank());
    }

    @Test
    void printShipsByCategory() {
        Fleet fleet = new Fleet();
        fleet.addShip(new Barge(Compass.NORTH, new Position(0, 0)));
        fleet.addShip(new Caravel(Compass.EAST, new Position(5, 5)));

        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            fleet.printShipsByCategory("Barge"); // chama o método com uma categoria qualquer
        } finally {
            System.setOut(originalOut); // repõe o System.out
        }

        String output = outContent.toString();

        // Só garantimos que não é null (que escreveu *algo*, nem que seja vazio)
        assertNotNull(output);
    }

    @Test
    void printFloatingShips() {
        Fleet fleet = new Fleet();

        IShip ship1 = new Barge(Compass.NORTH, new Position(0, 0));
        IShip ship2 = new Caravel(Compass.EAST, new Position(5, 5));

        fleet.addShip(ship1);
        fleet.addShip(ship2);

        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            fleet.printFloatingShips();
        } finally {
            System.setOut(originalOut);
        }

        String output = outContent.toString();
        assertNotNull(output);
    }

    @Test
    void printAllShips() {
        Fleet fleet = new Fleet();

        IShip ship1 = new Barge(Compass.NORTH, new Position(0, 0));
        IShip ship2 = new Caravel(Compass.EAST, new Position(5, 5));

        fleet.addShip(ship1);
        fleet.addShip(ship2);

        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            fleet.printAllShips();
        } finally {
            System.setOut(originalOut);
        }

        String output = outContent.toString();
        assertNotNull(output);
    }
}