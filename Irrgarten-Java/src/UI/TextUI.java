
package UI;

import irrgarten.*;
//import irrgarten.Directions;
//import irrgarten.GameState;
import java.util.Scanner;


public class TextUI {
    
    private static Scanner in = new Scanner(System.in);
    
    private char readChar() {
        String s = in.nextLine();     
        return s.charAt(0);
    }
    

    public Directions nextMove() {
        System.out.print("Where? ");
        
        Directions direction = Directions.DOWN;
        boolean gotInput = false;
        
        while (!gotInput) {
            char c = readChar();
            switch(c) {
                case 'w':
                    System.out.print(" UP\n");
                    direction = Directions.UP;
                    gotInput = true;
                    break;
                case 's':
                    System.out.print(" DOWN\n");
                    direction = Directions.DOWN;
                    gotInput = true;
                    break;
                case 'd':
                    System.out.print("RIGHT\n");
                    direction = Directions.RIGHT;
                    gotInput = true;
                    break;
                case 'a':
                    System.out.print(" LEFT\n");
                    direction = Directions.LEFT;
                    gotInput = true;    
                    break;
            }
        }    
        return direction;
    }
    
    public void showGame(GameState gameState) {  
        System.out.println("Representación del laberinto: \n");
        System.out.println(gameState.getLabyrinthv());
        System.out.println("Jugador actual: \n");
        System.out.println(Integer.toString(gameState.getCurrentPlayer()));
        System.out.println(gameState.getPlayers());
        System.out.println("Representación del monstruo: \n");
        System.out.println(gameState.getMonsters());
        System.out.println("--------------------------------------");
        System.out.println("Representación del log: \n");
        System.out.println(gameState.getLog());
        System.out.println("Representación del ganador: \n");
        System.out.println(gameState.getWinner());
        
    }
    
}
