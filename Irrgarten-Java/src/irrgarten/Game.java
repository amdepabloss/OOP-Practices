/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;
import java.util.ArrayList;

/**
 *
 * @author jhony
 */
public class Game {
    private static int MAX_ROUNDS = 10;
    private int currentPlayerIndex;
    private String log;
    private Player currentPlayer;
    private ArrayList<Monster> monsters;
    private Labyrinth labyrinth;
    private ArrayList<Player> players;
    
    public Game(int nplayers){
        monsters=new ArrayList<>();
        currentPlayerIndex = Dice.whoStarts(nplayers);
        labyrinth = new Labyrinth(6,6,5,5);
        log = "";
        players = new ArrayList<>(nplayers);
        for(int i=0; i<nplayers;i++){
            players.add(new Player(Integer.toString(i).charAt(0),Dice.randomIntelligence(),Dice.randomStrength()));
        } 
        currentPlayer = players.get(currentPlayerIndex);
        
       configureLabyrinth(); 
    }
    
    boolean finished(){
        return labyrinth.haveAWinner();   
    }
    
    String playersToString(){
        String jugadores="";
        for(int i=0; i<players.size();i++){
            jugadores += players.get(i).to_String();
            jugadores += "\n";
        }
        return jugadores;
    }
    
    String monstersToString(){
        String monstruos="";
        for(int i=0; i<monsters.size();i++){
            monstruos += monsters.get(i).to_String();
            monstruos+= "\n";
        }
        return monstruos;
    }
    
    public GameState getGameState(){    
        //System.out.println(monstersToString());
        //System.out.println(playersToString());
        GameState estado = new GameState(labyrinth.toString(),playersToString(),monstersToString(),currentPlayerIndex,finished(),log);
        return estado;
    }
    
    private void configureLabyrinth(){
        Monster m1 = new Monster("Monstruo0",Dice.randomIntelligence(),Dice.randomStrength());
        Monster m2 = new Monster("Monstruo1",Dice.randomIntelligence(),Dice.randomStrength());
        
        labyrinth.addMonster(1,1,m1);
        labyrinth.addMonster(3,3,m2);
        
        monsters.add(m1);
        monsters.add(m2);
        
        labyrinth.addBlock(Orientation.HORIZONTAL, 0, 1, 1);
        labyrinth.addBlock(Orientation.HORIZONTAL, 1, 3, 2);
        labyrinth.addBlock(Orientation.HORIZONTAL, 2, 0, 1);
        labyrinth.addBlock(Orientation.HORIZONTAL, 3, 1, 1);
        labyrinth.addBlock(Orientation.HORIZONTAL, 4, 3, 1);
        
        labyrinth.spreadPlayer(players);
    }
            
    private void nextPlayer(){                                                                                             
        currentPlayerIndex = (currentPlayerIndex+1)%players.size();
        currentPlayer = players.get(currentPlayerIndex);
    }
    
    private void logPlayerWon(){      
        log += "El jugador actual ha ganado";
        log+="\n";
    }
    
    private void logMonsterWon(){
        log += "El monstruo actual ha ganado";
        log+="\n";
    }
    
    private void logResurrected(){       
        log += "El jugador actual ha resucitado";
        log+="\n";
    }
    
    private void logPlayerSkipTurn(){      
        log += "El jugador actual ha perdido el turno por estar muerto";
        log+="\n";
    }
    
    private void logPlayerNoOrders(){       
        log += "El jugador no ha seguido las instrucciones del jugador humano (no fue posible)";
        log+="\n";
    }
    
    private void logNoMonster(){
        log += "El jugador se ha movido a una celda vacía o no le ha sido posible moverse";
        log+="\n";
    }
    
    private void logRounds(int rounds, int max){
        log += "Se han producido "+rounds+" de "+max+" rondas de combate";
        log+="\n";
    }
    
    public boolean nextStep(Directions preferredDirection){
        String log = "";
        boolean dead = currentPlayer.dead();
        
        if(!dead){
            Directions direction = actualDirection(preferredDirection);
            if(direction != preferredDirection) logPlayerNoOrders();
            Monster monster = labyrinth.putPlayer(direction, currentPlayer);
            
            if(monster == null) logNoMonster();
            else{
                GameCharacter winner = combat(monster);
                manageReward(winner);
            }
            
        }else manageResurrection();
        
        boolean endGame = finished();
        
        if(!endGame) nextPlayer();
        
        return endGame;
    }
    
    private Directions actualDirection(Directions preferredDirection){
        
        int currentRow = currentPlayer.getRow();
        int currentCol = currentPlayer.getCol();
        ArrayList <Directions> validMoves = labyrinth.validMoves(currentRow,currentCol);
        Directions output = currentPlayer.move(preferredDirection,validMoves);
        return output;
    } 
    
    private GameCharacter combat(Monster monster){
        
        int rounds = 0;
        GameCharacter winner = GameCharacter.PLAYER;
        float playerAttack = currentPlayer.attack();
        boolean lose = monster.defend(playerAttack);
        
        while((!lose) && (rounds < MAX_ROUNDS)){
            winner = GameCharacter.MONSTER;
            rounds++;
            float monsterAttack = monster.attack();
            lose = currentPlayer.defend(monsterAttack);                        
            if(!lose){
                playerAttack = currentPlayer.attack();                         
                winner = GameCharacter.PLAYER;
                lose = monster.defend(playerAttack);
            }
            
        }
        logRounds(rounds, MAX_ROUNDS);
        return winner;
    }
    
    private void manageResurrection(){                            
        boolean resurrect = Dice.resurrectPlayer();
        if(resurrect){
            currentPlayer.resurrect();    
            FuzzyPlayer jugadorTonto = new FuzzyPlayer(currentPlayer);                  
            currentPlayer = jugadorTonto; 
            players.set(currentPlayerIndex,currentPlayer);
            labyrinth.colocarFuzzy(jugadorTonto);
            logResurrected();
        }else logPlayerSkipTurn();
           
    }
    
    private void manageReward(GameCharacter winner){               
        if(winner==GameCharacter.PLAYER){
            currentPlayer.receiveReward();
            logPlayerWon();
        }else logMonsterWon();
    } 
}
