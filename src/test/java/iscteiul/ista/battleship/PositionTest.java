package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void getRow() {
        Position p = new Position(3, 5);
        assertEquals(3, p.getRow());
    }

    @Test
    void getColumn() {
        Position p = new Position(3, 5);
        assertEquals(5, p.getColumn());
    }

    @Test
    void testHashCode() {
        Position p1 = new Position(2, 4);
        Position p2 = new Position(2, 4);
        Position p3 = new Position(3, 4);

        assertEquals(p1.hashCode(), p2.hashCode());   // iguais → mesmo hash
        assertNotEquals(p1.hashCode(), p3.hashCode()); // diferentes → hash diferente
    }

    @Test
    void testEquals() {
        Position p1 = new Position(2, 4);
        Position p2 = new Position(2, 4);
        Position p3 = new Position(3, 4);
        Object otherType = "not a position";

        // reflexivo
        assertEquals(p1, p1);

        // simétrico para objetos equivalentes
        assertEquals(p1, p2);
        assertEquals(p2, p1);

        // diferente linha → diferente
        assertNotEquals(p1, p3);

        // null → false
        assertNotEquals(p1, null);

        // outro tipo → false
        assertNotEquals(p1, otherType);
    }

    @Test
    void isAdjacentTo() {
        Position center = new Position(5, 5);

        Position up = new Position(4, 5);
        Position down = new Position(6, 5);
        Position left = new Position(5, 4);
        Position right = new Position(5, 6);

        // adjacentes diretos (óbvios)
        assertAll(
                () -> assertTrue(center.isAdjacentTo(up)),
                () -> assertTrue(center.isAdjacentTo(down)),
                () -> assertTrue(center.isAdjacentTo(left)),
                () -> assertTrue(center.isAdjacentTo(right))
        );
    }

    @Test
    void occupy() {
        Position p = new Position(2, 3);
        assertFalse(p.isOccupied());  // antes
        p.occupy();
        assertTrue(p.isOccupied());   // depois
    }

    @Test
    void shoot() {
        Position p = new Position(1, 1);
        assertFalse(p.isHit());     // antes
        p.shoot();
        assertTrue(p.isHit());      // depois
    }

    @Test
    void isOccupied() {
        Position p = new Position(4, 7);

        assertFalse(p.isOccupied());
        p.occupy();
        assertTrue(p.isOccupied());
    }

    @Test
    void isHit() {
        Position p = new Position(3, 3);

        assertFalse(p.isHit());
        p.shoot();
        assertTrue(p.isHit());
    }

    @Test
    void testToString() {
        Position p = new Position(2, 3);
        assertEquals("Linha = 2 Coluna = 3", p.toString());
    }

    @Test
    void isAdjacentToFalseCases() {
        Position center = new Position(5, 5);

        // 1) Diferença na linha > 1 → primeira condição (linha) falha
        Position farRow = new Position(7, 5); // |7 - 5| = 2 > 1, coluna igual

        // 2) Diferença na coluna > 1 → primeira condição (linha) passa, segunda falha
        Position farCol = new Position(5, 8); // |5 - 5| = 0 <= 1, |8 - 5| = 3 > 1

        assertAll(
                () -> assertFalse(center.isAdjacentTo(farRow),
                        "Posição com diferença de linha > 1 não deve ser adjacente"),
                () -> assertFalse(center.isAdjacentTo(farCol),
                        "Posição com diferença de coluna > 1 não deve ser adjacente")
        );
    }

}