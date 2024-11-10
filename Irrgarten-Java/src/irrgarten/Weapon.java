/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author dpant
 */
public class Weapon extends CombatElement{
    
    Weapon (float power_, int uses_){ 
       super(power_,uses_);
    }  
    
    public float attack (){
        return produceEffect();
    }
    @Override
    public String toString(){
       return "W"+super.toString();  
    }
  
}
