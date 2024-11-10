# frozen_string_literal: true
require_relative '../Irrgarten/dice'
require_relative '../Irrgarten/orientation'
require_relative '../Irrgarten/directions'
require_relative '../Irrgarten/labyrinth_character'
module Irrgarten
class Monster < LabyrinthCharacter

  @@INITIAL_HEALTH = 5

  def initialize(n,i,s)
    super(n,i,s, @@INITIAL_HEALTH)
  end

  def attack
    Dice.intensity(@strength)   #No pongo get_strenght porque ya tenemos los attr en labyrinth_character
  end

  def defend(received_attack)
    is_dead = dead              #dead es una función de labyrinth character
    if(!is_dead)
      defensive_energy = Dice.intensity(@intelligence)
      if(defensive_energy < received_attack)
        got_wounded
        is_dead = dead
      end
    end
    is_dead
  end
  public_class_method :new
end
end
