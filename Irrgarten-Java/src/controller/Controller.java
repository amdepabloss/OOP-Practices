package controller;
import UI.UI;
import irrgarten.Directions;
import irrgarten.Game;
public class Controller {
 
private Game game;
private UI view;
 
public Controller(Game game, UI view) {
 this.game = game;
 this.view = view;
}
 
public void play() {
 boolean endOfGame = false;
 while (!endOfGame) {
 view.showGame(game.getGameState());
 Directions direction = view.nextMove();
 endOfGame = game.nextStep(direction);
 }
 view.showGame(game.getGameState()); 
}
 
}
