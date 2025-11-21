package iscteiul.ista.battleship;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;



import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class GameTest {

    @Test
    @DisplayName("fire: tiro inválido fora do tabuleiro incrementa invalidShots e não é registado")
    void fire() {
        // Para tiros inválidos, o Game nunca usa a fleet,
        // por isso podemos passar null aqui sem problema.
        Game game = new Game(null);

        // Posição claramente inválida (linha e coluna negativas)
        Position invalidPos = new Position(-1, -1);

        IShip result = game.fire(invalidPos);

        assertAll(
                () -> assertNull(result, "Tiro inválido deve devolver null"),
                () -> assertEquals(1, game.getInvalidShots(),
                        "Deve contar 1 tiro inválido"),
                () -> assertTrue(game.getShots().isEmpty(),
                        "Tiro inválido não deve ser guardado na lista de shots")
        );
    }

    @Test
    @DisplayName("getShots devolve apenas os tiros válidos, pela ordem em que foram disparados")
    void getShots() {
        IFleet fleet = new DummyEmptyFleet(); // frota sem navios
        Game game = new Game(fleet);

        Position p1 = new Position(0, 0);   // válido
        Position p2 = new Position(1, 1);   // válido

        game.fire(p1);                      // deve ser guardado
        game.fire(new Position(-1, -1));    // inválido, NÃO deve ser guardado
        game.fire(p2);                      // deve ser guardado

        List<IPosition> shots = game.getShots();

        assertAll(
                () -> assertEquals(2, shots.size(),
                        "Devem existir exatamente 2 tiros válidos registados"),
                () -> assertEquals(p1, shots.get(0),
                        "O primeiro tiro registado deve ser p1"),
                () -> assertEquals(p2, shots.get(1),
                        "O segundo tiro registado deve ser p2")
        );
    }

    @Test
    @DisplayName("getRepeatedShots conta tiros repetidos na mesma posição")
    void getRepeatedShots() {
        IFleet fleet = new DummyEmptyFleet(); // frota vazia chega para este teste
        Game game = new Game(fleet);

        Position p = new Position(2, 3);   // posição válida (assumindo tabuleiro normal)

        // 1º tiro -> válido, não repetido
        game.fire(p);

        // 2º tiro na MESMA posição -> deve ser contado como repetido
        game.fire(p);

        assertAll(
                () -> assertEquals(1, game.getRepeatedShots(),
                        "Deve haver exatamente 1 tiro repetido"),
                () -> assertEquals(1, game.getShots().size(),
                        "A mesma posição não deve ser adicionada duas vezes à lista de shots"),
                () -> assertEquals(p, game.getShots().get(0),
                        "O único shot registado deve ser a posição p")
        );
    }

    @Test
    @DisplayName("getInvalidShots devolve o número total de tiros inválidos efetuados")
    void getInvalidShots() {
        IFleet fleet = new DummyEmptyFleet(); // frota vazia chega para este teste
        Game game = new Game(fleet);

        // Dois tiros inválidos (coordenadas negativas)
        game.fire(new Position(-1, -1));
        game.fire(new Position(-5, 3));

        // Um tiro válido (por exemplo 0,0 assume-se dentro do tabuleiro)
        game.fire(new Position(0, 0));

        assertAll(
                () -> assertEquals(2, game.getInvalidShots(),
                        "Devem ser contados exatamente 2 tiros inválidos"),
                () -> assertEquals(1, game.getShots().size(),
                        "Apenas o tiro válido deve ser registado na lista de shots")
        );
    }


    @Test
    @DisplayName("getHits conta apenas tiros que acertam em navios")
    void getHits() {
        // Criar duas posições válidas para o navio
        Position p1 = new Position(2, 2);
        Position p2 = new Position(2, 3);

        // Lista de posições do navio
        java.util.List<IPosition> shipPositions = new java.util.ArrayList<>();
        shipPositions.add(p1);
        shipPositions.add(p2);

        // Navio dummy com essas posições
        DummyShip ship = new DummyShip(shipPositions);
        IFleet fleet = new DummyFleetWithOneShip(ship);
        Game game = new Game(fleet);

        // Tiro válido mas miss (não acerta no navio)
        Position miss = new Position(0, 0);
        game.fire(miss);

        // Tiro válido que acerta numa das posições do navio
        game.fire(p1);

        assertAll(
                () -> assertEquals(1, game.getHits(),
                        "Deve haver exatamente 1 hit"),
                () -> assertEquals(0, game.getInvalidShots(),
                        "Não lançámos tiros inválidos"),
                () -> assertEquals(0, game.getRepeatedShots(),
                        "Não houve tiros repetidos"),
                () -> assertEquals(2, game.getShots().size(),
                        "Devem existir 2 tiros válidos registados (miss + hit)"),
                () -> assertEquals(1, game.getRemainingShips(),
                        "O navio ainda deve estar a flutuar (apenas 1 posição atingida)"),
                () -> assertEquals(0, game.getSunkShips(),
                        "Ainda não há navios afundados")
        );
    }


    @Test
    void getSunkShips() {
    }

    @Test
    @DisplayName("getRemainingShips devolve o número de navios ainda a flutuar")
    void getRemainingShips() {
        // Criar duas posições para o navio
        Position p1 = new Position(6, 6);
        Position p2 = new Position(6, 7);

        java.util.List<IPosition> shipPositions = new java.util.ArrayList<>();
        shipPositions.add(p1);
        shipPositions.add(p2);

        DummyShip ship = new DummyShip(shipPositions);
        IFleet fleet = new DummyFleetWithOneShip(ship);
        Game game = new Game(fleet);

        // Antes de qualquer tiro: 1 navio a flutuar
        assertEquals(1, game.getRemainingShips(),
                "Antes dos tiros deve haver 1 navio a flutuar");

        // Primeiro tiro: acerta, mas o navio ainda não afunda
        game.fire(p1);
        assertEquals(1, game.getRemainingShips(),
                "Depois de 1 hit ainda deve haver 1 navio a flutuar");

        // Segundo tiro: acerta na última posição e afunda o navio
        game.fire(p2);
        assertEquals(0, game.getRemainingShips(),
                "Depois de afundar o navio não deve restar nenhum navio a flutuar");
    }


    @Test
    @DisplayName("printBoard imprime o marcador nas posições indicadas")
    void printBoard() {
        Game game = new Game(null); // fleet não é usada em printBoard

        // posições que queremos marcar
        Position p1 = new Position(0, 0);
        Position p2 = new Position(1, 2);

        List<IPosition> positions = new ArrayList<>();
        positions.add(p1);
        positions.add(p2);

        // Capturar o que é impresso em System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            game.printBoard(positions, '#');
        } finally {
            // Restaurar System.out para não estragar outros testes
            System.setOut(originalOut);
        }

        String output = outContent.toString();
        String[] lines = output.split("\\R"); // quebra por qualquer tipo de newline

        // Verificações básicas sobre o tamanho do tabuleiro
        assertEquals(Fleet.BOARD_SIZE, lines.length,
                "O número de linhas impressas deve ser igual a BOARD_SIZE");
        assertEquals(Fleet.BOARD_SIZE, lines[0].length(),
                "O número de colunas por linha deve ser igual a BOARD_SIZE");

        // Verificar que o marcador '#' foi colocado nas posições indicadas
        assertAll(
                () -> assertEquals('#', lines[p1.getRow()].charAt(p1.getColumn()),
                        "Deve haver um '#' na posição p1"),
                () -> assertEquals('#', lines[p2.getRow()].charAt(p2.getColumn()),
                        "Deve haver um '#' na posição p2")
        );
    }

    @Test
    @DisplayName("printValidShots imprime as posições dos tiros válidos com 'X'")
    void printValidShots() {
        IFleet fleet = new DummyEmptyFleet(); // frota sem navios
        Game game = new Game(fleet);

        Position p1 = new Position(0, 0);
        Position p2 = new Position(1, 2);

        // Dois tiros válidos
        game.fire(p1);
        game.fire(p2);

        // Capturar System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            game.printValidShots();
        } finally {
            System.setOut(originalOut);
        }

        String output = outContent.toString();
        String[] lines = output.split("\\R");

        // Verificações básicas sobre o tamanho do tabuleiro
        assertEquals(Fleet.BOARD_SIZE, lines.length,
                "O número de linhas impressas deve ser igual a BOARD_SIZE");
        assertEquals(Fleet.BOARD_SIZE, lines[0].length(),
                "O número de colunas por linha deve ser igual a BOARD_SIZE");

        // 'X' nas posições onde disparamos
        assertAll(
                () -> assertEquals('X', lines[p1.getRow()].charAt(p1.getColumn()),
                        "Deve haver um 'X' na posição do primeiro tiro"),
                () -> assertEquals('X', lines[p2.getRow()].charAt(p2.getColumn()),
                        "Deve haver um 'X' na posição do segundo tiro")
        );
    }


    @Test
    @DisplayName("printFleet imprime '#' nas posições ocupadas pelos navios da frota")
    void printFleet() {
        // Criar posições para o navio
        Position p1 = new Position(2, 2);
        Position p2 = new Position(2, 3);
        Position p3 = new Position(3, 2);

        java.util.List<IPosition> shipPositions = new java.util.ArrayList<>();
        shipPositions.add(p1);
        shipPositions.add(p2);
        shipPositions.add(p3);

        // Navio dummy com essas posições
        DummyShip ship = new DummyShip(shipPositions);
        IFleet fleet = new DummyFleetWithOneShip(ship);
        Game game = new Game(fleet);

        // Capturar System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            game.printFleet();
        } finally {
            System.setOut(originalOut);
        }

        String output = outContent.toString();
        String[] lines = output.split("\\R");

        // Verificações básicas sobre o tamanho do tabuleiro
        assertEquals(Fleet.BOARD_SIZE, lines.length,
                "O número de linhas impressas deve ser igual a BOARD_SIZE");
        assertEquals(Fleet.BOARD_SIZE, lines[0].length(),
                "O número de colunas por linha deve ser igual a BOARD_SIZE");

        // Verificar que as posições do navio têm '#'
        assertAll(
                () -> assertEquals('#', lines[p1.getRow()].charAt(p1.getColumn()),
                        "Deve haver '#' na posição p1"),
                () -> assertEquals('#', lines[p2.getRow()].charAt(p2.getColumn()),
                        "Deve haver '#' na posição p2"),
                () -> assertEquals('#', lines[p3.getRow()].charAt(p3.getColumn()),
                        "Deve haver '#' na posição p3")
        );
    }

    @ParameterizedTest(name = "fire: tiro inválido em ({0},{1}) incrementa invalidShots e não regista shot")
    @CsvSource({
            "-1, 0",
            "0, -1",
            "-2, -3"
    })
    @DisplayName("fire (parametrizado): vários tiros inválidos não são registados")
    void fire_invalidShotsParameterized(int row, int col) {
        Game game = new Game(null); // fleet não é usada para tiros inválidos

        Position invalidPos = new Position(row, col);

        IShip result = game.fire(invalidPos);

        assertAll(
                () -> assertNull(result, "Tiro inválido deve devolver null"),
                () -> assertEquals(1, game.getInvalidShots(),
                        "Em cada execução do teste deve contar 1 tiro inválido"),
                () -> assertTrue(game.getShots().isEmpty(),
                        "Tiros inválidos não devem ser registados na lista de shots")
        );
    }
    @Test
    @DisplayName("fire: tiro com linha acima do limite do tabuleiro é inválido")
    void fire_invalidShot_rowAboveBoardSize() {
        Game game = new Game(null); // fleet não é usada para tiros inválidos

        int rowAbove = Fleet.BOARD_SIZE + 1;
        int validCol = 0;

        Position invalidPos = new Position(rowAbove, validCol);

        IShip result = game.fire(invalidPos);

        assertAll(
                () -> assertNull(result, "Tiro inválido deve devolver null"),
                () -> assertEquals(1, game.getInvalidShots(),
                        "Deve contar 1 tiro inválido"),
                () -> assertTrue(game.getShots().isEmpty(),
                        "Tiros inválidos não devem ser registados na lista de shots")
        );
    }
    @Test
    @DisplayName("fire: tiro com coluna acima do limite do tabuleiro é inválido")
    void fire_invalidShot_colAboveBoardSize() {
        Game game = new Game(null); // fleet não é usada para tiros inválidos

        int validRow = 0;
        int colAbove = Fleet.BOARD_SIZE + 1;

        Position invalidPos = new Position(validRow, colAbove);

        IShip result = game.fire(invalidPos);

        assertAll(
                () -> assertNull(result, "Tiro inválido deve devolver null"),
                () -> assertEquals(1, game.getInvalidShots(),
                        "Deve contar 1 tiro inválido"),
                () -> assertTrue(game.getShots().isEmpty(),
                        "Tiros inválidos não devem ser registados na lista de shots")
        );
    }



    /**
     * Frota vazia para testes: não tem navios e nunca há shipAt().
     */
    static class DummyEmptyFleet implements IFleet {

        @Override
        public List<IShip> getShips() {
            return new ArrayList<>();
        }

        @Override
        public boolean addShip(IShip s) {
            return false;
        }

        @Override
        public List<IShip> getShipsLike(String category) {
            return List.of();
        }

        @Override
        public List<IShip> getFloatingShips() {
            return new ArrayList<>();
        }

        @Override
        public IShip shipAt(IPosition pos) {
            return null; // nunca há navio em lado nenhum
        }

        @Override
        public void printStatus() {

        }
    }
    /**
     * Implementação simples de IShip para testes com um navio real.
     */
    static class DummyShip implements IShip {

        private final java.util.List<IPosition> positions;
        private final java.util.List<IPosition> hits = new java.util.ArrayList<>();

        DummyShip(java.util.List<IPosition> positions) {
            this.positions = new java.util.ArrayList<>(positions);
        }

        @Override
        public String getCategory() {
            return "";
        }

        @Override
        public Integer getSize() {
            return 0;
        }

        @Override
        public java.util.List<IPosition> getPositions() {
            return positions;
        }

        @Override
        public IPosition getPosition() {
            return null;
        }

        @Override
        public Compass getBearing() {
            return null;
        }

        @Override
        public void shoot(IPosition pos) {
            if (positions.contains(pos) && !hits.contains(pos)) {
                hits.add(pos);
            }
        }

        @Override
        public boolean stillFloating() {
            // flutua enquanto nem todas as posições tiverem sido atingidas
            return hits.size() < positions.size();
        }

        @Override
        public int getTopMostPos() {
            return 0;
        }

        @Override
        public int getBottomMostPos() {
            return 0;
        }

        @Override
        public int getLeftMostPos() {
            return 0;
        }

        @Override
        public int getRightMostPos() {
            return 0;
        }

        @Override
        public boolean occupies(IPosition pos) {
            return false;
        }

        @Override
        public boolean tooCloseTo(IShip other) {
            return false;
        }

        @Override
        public boolean tooCloseTo(IPosition pos) {
            return false;
        }
    }

    /**
     * Frota com um único navio, usada para testar hits e remainingShips.
     */
    static class DummyFleetWithOneShip implements IFleet {

        private final IShip ship;

        DummyFleetWithOneShip(IShip ship) {
            this.ship = ship;
        }

        @Override
        public java.util.List<IShip> getShips() {
            java.util.List<IShip> list = new java.util.ArrayList<>();
            list.add(ship);
            return list;
        }

        @Override
        public boolean addShip(IShip s) {
            return false;
        }

        @Override
        public List<IShip> getShipsLike(String category) {
            return List.of();
        }

        @Override
        public java.util.List<IShip> getFloatingShips() {
            java.util.List<IShip> list = new java.util.ArrayList<>();
            if (ship.stillFloating()) {
                list.add(ship);
            }
            return list;
        }

        @Override
        public IShip shipAt(IPosition pos) {
            for (IPosition p : ship.getPositions()) {
                if (p.equals(pos)) {
                    return ship;
                }
            }
            return null;
        }

        @Override
        public void printStatus() {

        }
    }

}
