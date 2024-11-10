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
public class Player extends LabyrinthCharacter {
    private static int MAX_WEAPONS = 2;
    private static int MAX_SHIELDS = 3;
    private static int INITIAL_HEALTH = 10;
    private static int HITS2LOSE = 3;
   
    private char number;
    
    private int consecutiveHits; 
    private ArrayList <Weapon> weapons; 
    private ArrayList <Shield> shields;
    private WeaponCardDeck weaponCardDeck;
    private ShieldCardDeck shieldCardDeck;
            
    
    Player (char number, float intelligence, float strength){                                    
        super("Player#" + number, intelligence, strength, INITIAL_HEALTH);
        shields = new ArrayList<>();
        weapons = new ArrayList<>();
        weaponCardDeck = new WeaponCardDeck();
        shieldCardDeck = new ShieldCardDeck();
        consecutiveHits = 0;
        this.number = number;
    }
    
    Player (Player other){
        super(other);
        //number = other.number;
        consecutiveHits = other.consecutiveHits;
        weapons = other.weapons;
        shields = other.shields;
        weaponCardDeck = other.weaponCardDeck;
        shieldCardDeck = other.shieldCardDeck;
    }
    
    public void resurrect(){                      
        setHealth(INITIAL_HEALTH);
        consecutiveHits = 0;
        weapons.clear();
        shields.clear();
    }
  
    public char getNumber(){
        return number;
    }
    
    public Directions move(Directions direction,ArrayList<Directions> validMoves){                 
        int size = validMoves.size();
        boolean contained = validMoves.contains(direction);
        if(size>0 && !contained)
            return validMoves.get(0);                                       
        else return direction; 
    }
    
    public float attack(){                         
        return getStrength() + sumWeapons(); 
    }
    
    public boolean defend(float receivedAttack){
        return manageHit(receivedAttack);
    }
    
    public void receiveReward(){                                                                       
        int wReward = Dice.weaponsReward();
        int sReward = Dice.shieldsReward();
        
        for (int i=0;i<wReward;i++){
            receiveWeapon(weaponCardDeck.nextCard());
        }
        
        for (int i=0;i<sReward;i++){
            receiveShield(shieldCardDeck.nextCard());
        }

        int extraHealth = Dice.healthReward();
        setHealth(getHealth() + extraHealth);
        
    }
    
    public String weaponsToString(){
        String armas="";
        for(int i=0; i<weapons.size();i++){
            armas += weapons.get(i).toString();
            armas += "\n";
        }
        return armas;
       
    }
    
    public String shieldsToString(){
        String escudos="";
        for(int i=0; i<shields.size();i++){
            escudos += shields.get(i).toString();
            escudos += "\n";
        }
        return escudos;
    }
        
    public String toString(){
        return "P["+super.toString()+","+weaponsToString()+","+shieldsToString()+","+consecutiveHits+"]";
    }
    
    private void receiveWeapon(Weapon w){
        for(int i=weapons.size()-1;i>=0;i--){
            boolean discard = weapons.get(i).discard();
            if(discard)
                weapons.remove(weapons.get(i));
        }
        int size = weapons.size();
        if(size < MAX_WEAPONS)
            weapons.add(w);
    }
    
    private void receiveShield(Shield s){
        for(int i=shields.size()-1;i>=0;i--){
            boolean discard = shields.get(i).discard();
            if(discard)
                shields.remove(shields.get(i));
        }
        int size = shields.size();
        if(size < MAX_SHIELDS)
            shields.add(s);
    }
    
    private Weapon newWeapon(){                                                     
        return new Weapon(Dice.weaponPower(), Dice.usesLeft());
    }
    
    private Shield newShield(){                                                    
        return new Shield(Dice.shieldPower(), Dice.usesLeft());
    }
    
    protected float sumWeapons(){                                                     
        float suma = 0;
        for (int i=0; i<weapons.size(); i++){
            suma+= weapons.get(i).attack(); 
        }                               
        return suma; 
    }
    
    protected float sumShields(){                                                      
        float suma = 0;
        for (int i=0; i<shields.size(); i++){
            suma+=shields.get(i).protect();
        }
        return suma;
    }
    protected float defensiveEnergy(){
        return getIntelligence() + sumShields(); 
    }
    
    private boolean manageHit(float receivedAttack){                                
        boolean lose; 
        float defense = defensiveEnergy();
        if(defense<receivedAttack){
            gotWounded();
            incConsecutiveHits();
        }else resetHits(); 
        
        if((consecutiveHits == HITS2LOSE) || (dead())){
            resetHits();
            lose = true; 
        }else lose = false;
        
        return lose; 
    }
    
    private void resetHits(){                                                       
        consecutiveHits = 0;
    }
    
    private void incConsecutiveHits(){                                              
        consecutiveHits++;
    }
    
    
    
    
    
   
    
    
    
    
}