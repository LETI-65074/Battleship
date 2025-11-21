package iscteiul.ista;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    /**
     * Executa o main com um input simulado e devolve o texto impresso em System.out.
     */
    private String runMainWithInput(String input) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();

        System.setIn(inContent);
        System.setOut(new PrintStream(outContent));

        try {
            App.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        return outContent.toString();
    }

    @Test
    @DisplayName("main termina imediatamente quando a primeira entrada é 'q'")
    void main_quitImmediately() {
        String output = runMainWithInput("q\n");

        assertAll(
                () -> assertTrue(output.contains("***  Battleship Game ***"),
                        "Deve imprimir o título do jogo"),
                () -> assertTrue(output.contains("Tiro (linha,coluna) ou q:"),
                        "Deve mostrar o prompt pelo menos uma vez")
        );
    }

    @Test
    @DisplayName("main: tiro que afunda o navio imprime 'Afundou!'")
    void main_shotSinksShip_printsAfundou() {
        // Primeiro um tiro em (0,0) que acerta na Barge e afunda, depois 'q' para sair
        String output = runMainWithInput("0,0\nq\n");

        assertAll(
                () -> assertTrue(output.contains("Tiro (linha,coluna) ou q:"),
                        "Deve ter pedido o primeiro tiro"),
                () -> assertTrue(output.contains("Afundou!"),
                        "Depois do tiro em (0,0) deve imprimir 'Afundou!'")
        );
    }

    @Test
    @DisplayName("main: tiro que falha imprime '—'")
    void main_shotMiss_printsDash() {
        // Um tiro que falha (0,1), depois 'q'
        String output = runMainWithInput("0,1\nq\n");

        assertAll(
                () -> assertTrue(output.contains("Tiro (linha,coluna) ou q:"),
                        "Deve ter pedido o tiro"),
                () -> assertTrue(output.contains("—"),
                        "Tiro em (0,1) (água) deve imprimir '—'")
        );
    }
}
