/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author jhony
 */
public abstract class CombatElement {
    private float effect;
    private int uses; 
    
    CombatElement (float effect_, int uses_){ 
        effect=effect_;
        uses=uses_;
    }  
    
    protected float produceEffect (){
        
        if (uses > 0)uses--;
        else return 0;
     
        return effect;  
    }
    
    public String toString(){
       return "["+effect+","+uses+"]";  
    }
    
    boolean discard(){
        return Dice.discardElement(uses); 
    }
}
