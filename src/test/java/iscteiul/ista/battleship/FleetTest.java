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

    @Test
    void addShip_rejectsShipThatIsTooClose() {
        Fleet fleet = new Fleet();

        IShip first = new Barge(Compass.NORTH, new Position(3, 3));
        fleet.addShip(first);

        // Segundo navio não colide, mas fica imediatamente adjacente
        // (depende da implementação de isAdjacentTo, mas (3,4) costuma ser "ao lado")
        IShip tooClose = new Barge(Compass.NORTH, new Position(3, 4));

        boolean added = fleet.addShip(tooClose);

        assertFalse(added, "Navio demasiado próximo não deve ser aceite pela frota");
        assertEquals(1, fleet.getShips().size(),
                "Depois de rejeitar o navio demasiado próximo, ainda só deve haver 1 navio na frota");
    }

    @Test
    void getShipsLike_returnsShipsOfGivenCategory() {
        Fleet fleet = new Fleet();

        IShip barge1 = new Barge(Compass.NORTH, new Position(0, 0));
        IShip barge2 = new Barge(Compass.EAST, new Position(5, 5));
        IShip caravel = new Caravel(Compass.SOUTH, new Position(2, 2));

        fleet.addShip(barge1);
        fleet.addShip(barge2);
        fleet.addShip(caravel);

        // usar a categoria real de um dos navios, em vez de "adivinhar" a string
        String bargeCategory = barge1.getCategory();

        var barges = fleet.getShipsLike(bargeCategory);

        assertAll(
                () -> assertEquals(2, barges.size(), "Devem ser devolvidos 2 navios dessa categoria"),
                () -> assertTrue(barges.contains(barge1)),
                () -> assertTrue(barges.contains(barge2)),
                () -> assertFalse(barges.contains(caravel))
        );
    }
    @Test
    void getFloatingShips_ignoresSunkShips() {
        Fleet fleet = new Fleet();

        Ship floating = new Barge(Compass.NORTH, new Position(0, 0));
        Ship sinking  = new Caravel(Compass.EAST, new Position(5, 5));

        fleet.addShip(floating);
        fleet.addShip(sinking);

        // Afundar completamente o segundo navio
        for (IPosition p : sinking.getPositions()) {
            p.shoot(); // marcar todas as posições como atingidas
        }

        var floatingShips = fleet.getFloatingShips();

        assertAll(
                () -> assertEquals(1, floatingShips.size(), "Só deve restar 1 navio a flutuar"),
                () -> assertTrue(floatingShips.contains(floating)),
                () -> assertFalse(floatingShips.contains(sinking))
        );
    }
    @Test
    void printShipsByCategory_withNoMatchingShips_printsSomethingOrNothingButDoesNotCrash() {
        Fleet fleet = new Fleet();
        fleet.addShip(new Barge(Compass.NORTH, new Position(0, 0)));
        fleet.addShip(new Caravel(Compass.EAST, new Position(5, 5)));

        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            fleet.printShipsByCategory("NaoExisteCategoria"); // nenhuma categoria igual
        } finally {
            System.setOut(originalOut);
        }

        String output = outContent.toString();

        // Não sabemos exatamente o formato, por isso só garantimos que o método correu
        assertNotNull(output);
        // Se quiseres ser mais ambicioso e a implementação escrever "No ships", podes testar isso aqui.
    }
    @Test
    void printFloatingShips_whenAllSunk() {
        Fleet fleet = new Fleet();

        Ship s1 = new Barge(Compass.NORTH, new Position(0, 0));
        Ship s2 = new Caravel(Compass.EAST, new Position(5, 5));

        fleet.addShip(s1);
        fleet.addShip(s2);

        // Afundar TODOS os navios
        for (IPosition p : s1.getPositions()) {
            p.shoot();
        }
        for (IPosition p : s2.getPositions()) {
            p.shoot();
        }

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
        // aqui também podias confirmar que não aparece nenhum dos toString() dos navios, se quiseres
    }

    @Test
    void addShip_rejectsShipOutsideBoardWithNegativeCoords() {
        Fleet fleet = new Fleet();

        IShip outTop = new Barge(Compass.NORTH, new Position(-1, 0)); // linha negativa
        IShip outLeft = new Barge(Compass.EAST, new Position(0, -1)); // coluna negativa

        assertAll(
                () -> assertFalse(fleet.addShip(outTop),
                        "Navio com linha < 0 não deve ser aceite"),
                () -> assertFalse(fleet.addShip(outLeft),
                        "Navio com coluna < 0 não deve ser aceite"),
                () -> assertTrue(fleet.getShips().isEmpty(),
                        "Nenhum destes navios inválidos deve ficar registado")
        );
    }
    @Test
    void addShip_rejectsShipOutsideBoardBeyondMax() {
        Fleet fleet = new Fleet();
        int n = Fleet.BOARD_SIZE;

        IShip outBottom = new Barge(Compass.NORTH, new Position(n, 0)); // linha == BOARD_SIZE
        IShip outRight  = new Barge(Compass.EAST, new Position(0, n));  // coluna == BOARD_SIZE

        assertAll(
                () -> assertFalse(fleet.addShip(outBottom),
                        "Navio com linha > BOARD_SIZE - 1 não deve ser aceite"),
                () -> assertFalse(fleet.addShip(outRight),
                        "Navio com coluna > BOARD_SIZE - 1 não deve ser aceite"),
                () -> assertTrue(fleet.getShips().isEmpty(),
                        "Nenhum destes navios inválidos deve ficar registado")
        );
    }
    @Test
    void addShip_rejectsWhenFleetAlreadyOverCapacity() throws Exception {
        Fleet fleet = new Fleet();

        // Aceder à lista interna 'ships' via reflection
        var field = Fleet.class.getDeclaredField("ships");
        field.setAccessible(true);

        @SuppressWarnings("unchecked")
        List<IShip> internalShips = (List<IShip>) field.get(fleet);

        // Preencher internamente com mais do que FLEET_SIZE navios
        for (int i = 0; i < Fleet.FLEET_SIZE + 1; i++) {
            internalShips.add(new Barge(Compass.NORTH, new Position(0, 0)));
        }

        // Agora ships.size() > FLEET_SIZE antes do addShip ser chamado
        boolean added = fleet.addShip(new Barge(Compass.EAST, new Position(5, 5)));

        assertFalse(added,
                "Quando ships.size() > FLEET_SIZE, a primeira condição do if deve falhar e addShip deve devolver false");
    }
    @Test
    void printShipsByCategory_withNullCategoryThrowsAssertionError() {
        Fleet fleet = new Fleet();

        assertThrows(AssertionError.class,
                () -> fleet.printShipsByCategory(null),
                "printShipsByCategory(null) deve lançar AssertionError por causa do assert category != null");
    }



}