/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;
/**
 *
 * @author jhony
 */
public class Monster extends LabyrinthCharacter{
    private static int INITIAL_HEALTH = 5;
        
    Monster (String name, float intelligence, float strength){
        super(name,intelligence,strength,INITIAL_HEALTH);
    }
   
    @Override
    public float attack(){
        return Dice.intensity(getStrength());  
    }
    
    @Override
    public boolean defend(float receivedAttack){                                                       
        boolean isDead=dead();
        if(!isDead){
            float defensiveEnergy = Dice.intensity(getIntelligence());
            if(defensiveEnergy<receivedAttack){
                gotWounded();
                isDead = dead();
            }
        }
        return isDead;   
    }
}
