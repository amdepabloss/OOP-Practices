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
public class Labyrinth {
    private static char BLOCK_CHAR ='X';
    private static char EMPTY_CHAR ='-';
    private static char MONSTER_CHAR ='M';
    private static char COMBAT_CHAR ='C';
    private static char EXIT_CHAR ='E';
    private static int ROW = 0;
    private static int COL = 1;
    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;
    private char [][] labyrinth;
    private Monster[][] monsters;
    private Player[][] players;
    
   
    Labyrinth (int nRows, int nCols, int exitRow, int exitCol){
        
        this.nRows=nRows;
        this.nCols=nCols;
        this.exitRow=exitRow;
        this.exitCol=exitCol;
        
        labyrinth = new char[this.nRows][this.nCols];
        monsters = new Monster[this.nRows][this.nCols];
        players = new Player[this.nRows][this.nCols];
        
        for (int i=0; i<nRows; i++){
            for(int j=0; j<nCols; j++){
                labyrinth[i][j]=EMPTY_CHAR;
                monsters[i][j]=null;
                players[i][j]=null;
            }   
        } 
        labyrinth[exitRow][exitCol]=EXIT_CHAR;
    }
    
    boolean haveAWinner(){
        if (players[exitRow][exitCol]==null){
            return false;  
        } else return true;
    }
    
    public String toString(){
        
        
        String cadena = "";
        for (int i=0; i<nRows; i++){
            for(int j=0; j<nCols; j++){
                cadena+= " "+labyrinth[i][j]+" ";
            }
            cadena+= "\n";
        }
        return cadena;  
    }
    
   
    
    void addMonster(int row, int col, Monster monster){                    
        if(posOK(row,col)&&emptyPos(row,col)){                           
            labyrinth[row][col] = MONSTER_CHAR;
            monsters[row][col] = monster;
            monster.setPos(row,col);
        }
    }
    
    private boolean posOK (int row, int col){ 
        if (row<nRows && col<nCols && row >= 0 && col >= 0) return true;
         else return false;
    }
    
    private boolean emptyPos (int row,int col){
        if (labyrinth[row][col]== EMPTY_CHAR){
            return true;
        } else return false;
    }
    
    private boolean monsterPos (int row,int col){
         if (labyrinth[row][col]== MONSTER_CHAR){
            return true;
        } else return false;
    }
    
    private boolean exitPos (int row,int col){
         if (labyrinth[row][col]== EXIT_CHAR){
            return true;
        } else return false;
    }
    
    private boolean combatPos (int row,int col){
         if (labyrinth[row][col]== COMBAT_CHAR){
            return true;
        } else return false;
    }
    
    private boolean canStepOn (int row,int col){
         if (posOK(row,col)) return (emptyPos(row,col) || monsterPos(row,col) || exitPos(row,col));
        else return false;
    }
    
    private void updateOldPos(int row,int col){
        if (posOK(row,col)){
            if (combatPos(row,col)){
                labyrinth[row][col] = MONSTER_CHAR;
            } else labyrinth[row][col] = EMPTY_CHAR; 
        }
    }
    
    private int[] dir2Pos(int row,int col,Directions direction){
        int[] posicion = {row,col};
        if(direction == Directions.LEFT ){
           posicion[COL]--;
        }
        if(direction == Directions.RIGHT){
           posicion[COL]++;
        }
        if(direction == Directions.UP){
           posicion[ROW]--;
        }
        if(direction == Directions.DOWN){
           posicion[ROW]++;
        }
        return posicion;
    }
    
    private int[] randomEmptyPos(){                                                             
  
        int[] posicion={ROW,COL};
        
        posicion[ROW]=Dice.randomPos(nRows);
        posicion[COL]=Dice.randomPos(nCols); 
        
        while (!emptyPos(posicion[ROW],posicion[COL])){
            posicion[ROW]=Dice.randomPos(nRows);
            posicion[COL]=Dice.randomPos(nCols); 
        }
        return posicion;
    }
    
    public void addBlock(Orientation orientation, int startRow, int startCol, int length){       
        int incRow;
        int incCol;
        
        if(orientation == Orientation.VERTICAL){
            incRow=1;
            incCol=0;
        }else{
            incRow=0;
            incCol=1; 
        }
        int row = startRow;
        int col = startCol; 
        
        while((posOK(row,col)) && (emptyPos(row,col)) && (length>0) ){
            labyrinth[row][col]=BLOCK_CHAR;
            length -=1;
            row+=incRow;
            col+=incCol; 
        }
    }
    
    public Monster putPlayer(Directions direction,Player player){                               
        int oldRow = player.getRow();             
        int oldCol= player.getCol();
        int[] newPos = dir2Pos(oldRow,oldCol,direction);
        Monster monster = putPlayer2D(oldRow,oldCol,newPos[ROW],newPos[COL],player);
        return monster; 
    }
    
    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player){                    
        
        Monster output = null;
        
        if(canStepOn(row,col)){
            if(posOK(oldRow,oldCol)){
                Player p = players[oldRow][oldCol];
                if(p == player) updateOldPos(oldRow,oldCol);
                else players[oldRow][oldCol] = null;   
            }
            
            boolean monsterPos = monsterPos(row,col);
            
            if(monsterPos){
                labyrinth[row][col]=COMBAT_CHAR;
                output = monsters[row][col];
            }else{
                char number = player.getNumber();
                labyrinth[row][col]=number;
            }
            
            players[row][col]=player;
            player.setPos(row,col);
        }
        return output;
    }
    
    public void spreadPlayer(ArrayList<Player> players){                           
        for(int i=0;i< players.size();i++){
            Player p = players.get(i);
            int[]pos=randomEmptyPos();    
            putPlayer2D(-1,-1,pos[ROW],pos[COL],p);
        }
    }
    
    public ArrayList<Directions> validMoves(int row, int col){                      
        
        ArrayList<Directions> directions = new ArrayList<>();
        
        if(canStepOn(row+1,col)) directions.add(Directions.DOWN);   
        if(canStepOn(row-1,col)) directions.add(Directions.UP);       
        if(canStepOn(row,col+1)) directions.add(Directions.RIGHT);              
        if(canStepOn(row,col-1)) directions.add(Directions.LEFT);
       
        return directions; 
    }
    
    public void colocarFuzzy(FuzzyPlayer jugadorTonto){
        players [jugadorTonto.getRow()][jugadorTonto.getCol()] = jugadorTonto;
    }
}
