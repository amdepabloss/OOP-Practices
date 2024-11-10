/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;
import java.util.Random;
import java.util.ArrayList;

/**
 *
 * @author dpant
 */
public class Dice {
private static int MAX_USES = 5; //(número máximo de usos de armas y escudos)
private static double MAX_INTELLIGENCE = 10.0; //(valor máximo para la inteligencia de jugadores y monstruos)
private static double MAX_STRENGTH = 10.0; //(valor máximo para la fuerza de jugadores y monstruos)
private static double RESURRECT_PROB = 0.3; //(probabilidad de que un jugador sea resucitado en cada turno) 
private static int WEAPONS_REWARD = 2; //(numero máximo de armas recibidas al ganar un combate) 
private static int SHIELDS_REWARD = 3; //(numero máximo de escudos recibidos al ganar un combate) 
private static int HEALTH_REWARD = 5; //(numero máximo de unidades de salud recibidas al ganar un combate) 
private static int MAX_ATTACK = 3; //(máxima potencia de las armas)
private static int MAX_SHIELD = 2; //(máxima potencia de los escudos)
private static Random generator= new Random();


static public int randomPos(int max){return generator. nextInt(max);}
static public int whoStarts(int nplayers){return generator.nextInt(nplayers);}
static public float randomIntelligence(){return generator.nextFloat()*(float)MAX_INTELLIGENCE;}
static public float randomStrength(){return generator.nextFloat()*(float)MAX_STRENGTH;}
static public boolean resurrectPlayer(){
    // El 1 lo dejamos o ponemos variable
    if(generator.nextFloat(1)> RESURRECT_PROB) return true;
    else return false;  
}

static public int weaponsReward(){return generator.nextInt(WEAPONS_REWARD+1);}
static public int shieldsReward(){return generator.nextInt(SHIELDS_REWARD+1);}
static public int healthReward(){return generator.nextInt(HEALTH_REWARD+1);}   
static public float weaponPower(){return generator.nextFloat()*MAX_ATTACK;}
static float shieldPower(){return generator.nextFloat()*MAX_SHIELD;}
static public int usesLeft(){return generator.nextInt(MAX_USES+1);}
static float intensity(float competence){return generator.nextFloat()*competence;}

static boolean discardElement(int usesLeft){
    if(generator.nextFloat()<1.0*(MAX_USES-usesLeft)/MAX_USES) return true;
    else return false;
}
static public Directions nextStep (Directions preference, ArrayList <Directions> validMoves, float intelligence){
    if(generator.nextFloat()*MAX_INTELLIGENCE<intelligence){
        return preference;
    }else
        return validMoves.get(randomPos(validMoves.size()));      
} 
}
