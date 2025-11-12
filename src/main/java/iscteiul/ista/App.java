package iscteiul.ista;

import iscteiul.ista.battleship.*;

/**
 * Minimal entry point to run the Battleship interactive console.
 * Demonstrates creating a game and reading shot coordinates from stdin.
 */
public class App
{
    /**
     * Application entrypoint. Reads user input lines with "row,column" pairs
     * and fires at the created Game. Enter 'q' to quit.
     *
     * @param args command line arguments (ignored)
     */
    public static void main( String[] args )
    {

        System.out.printf("\n***  Battleship Game ***\n");

    //Game game = new Game(new Fleet());


        // Tasks.taskA();
       // Tasks.taskB(new Game(new Fleet()));

        Game game = new Game(new Fleet()); // usa a tua implementação concreta
        try (java.util.Scanner sc = new java.util.Scanner(System.in)) {
            IShip sunk;
            while (true) {
                System.out.print("Tiro (linha,coluna) ou q: ");
                String s = sc.nextLine().trim();
                if (s.equalsIgnoreCase("q")) break;
                String[] p = s.split(",");
                IPosition pos = new Position(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
                 sunk = game.fire(pos);
                System.out.println(sunk != null ? "Afundou!" : "—");
            }
        }
        //	Tasks.taskC();
        //	Tasks.taskD();
    }
}
