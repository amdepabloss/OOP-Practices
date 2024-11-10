/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;


/**
 *
 * @author dpant
 */
public class Shield extends CombatElement  {
    
    Shield(float protection_, int uses_){
       super(protection_,uses_);
    }
    
    public float protect(){
        return produceEffect(); 
    }
    
    @Override
    public String toString(){
       return "S"+super.toString();  
    }

}


