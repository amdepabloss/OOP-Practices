/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import irrgarten.Game;
import controller.Controller;
import UI.GraphicalUI;

/**
 *
 * @author dpant
 */
public class MainGUI {
    
   
    /* RUBY:
    vista = UI::TextUI.new
            juego = Irrgarten::Game.new(1)
            controlador = controller.new(juego,vista)
                    puts "Indico al controlador que empiece la partida"
                    controlador.play/*

    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        // TODO code application logic here
    TextUI vista = new TextUI();
    Game juego = new Game(3);
    Controller controlador = new Controller(juego,vista);
    
    controlador.play();
    }*/
    public static void main(String[] args) throws InterruptedException {
        GraphicalUI vista = new GraphicalUI();
        Game game = new Game(2);
        Controller controller = new Controller(game,vista);
        controller.play(); 
    }

}
