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
public class FuzzyPlayer extends Player{
    
    FuzzyPlayer(Player other){
        super(other);
    }
    
    @Override
    public Directions move (Directions direction, ArrayList <Directions> validMoves){
        return Dice.nextStep(super.move(direction, validMoves),validMoves,getIntelligence());
    }
    
    @Override
    public float attack(){
        return Dice.intensity(getStrength()) + super.sumWeapons();
    }
    
    @Override
    protected float defensiveEnergy(){
       return Dice.intensity(getIntelligence()) + super.sumShields();
    }
    
    @Override
    public String toString() {
       return "Fuzzy"+super.toString();
    }
}
