# frozen_string_literal: true


require_relative 'dice'
require_relative 'directions'
require_relative 'game_character'
require_relative 'game_state'
require_relative 'orientation'
require_relative 'shield'
require_relative 'weapon'
require_relative 'player'
require_relative 'labyrinth'
require_relative 'game'
module Irrgarten

class TestP1

  def main
    '''
    //////////////PRÁCTICA 1//////////////////////////////////////////////////////////////////////////////////
  escudo = Shield.new(5,4)
  arma = Weapon.new(3,1)

  puts escudo.to_s
  puts arma.to_s
  puts escudo.protect()
  puts arma.attack()
  puts arma.attack()



  laberinto = Labyrinth.new(5,5,5,5)

  puts laberinto.to_string





  /puts Directions::LEFT
  puts Directions::RIGHT
  puts Directions::UP
  puts Directions::DOWN
  puts Orientation::VERTICAL
  puts Orientation::HORIZONTAL
  puts GameCharacter::PLAYER
  puts GameCharacter::MONSTER

  puts estado.labyrinthv
  puts estado.players
  puts estado.monsters
  puts estado.current_players
  puts estado.winner
  puts estado.log/

  /for i in(0...100)
    puts Dice.random_pos(5)
  end/

  /for i in(0...100)
    puts Dice.who_starts(5)
  end/

  /for i in(0...100)
    puts Dice.random_intelligence
  end/

  /for i in(0...100)
    puts Dice.random_strength
  end/

  /resucita = 0
  no_resucita = 0
  for i in(0...100)
    if Dice.resurrect_player
    resucita = resucita + 1
    else no_resucita = no_resucita + 1
    end/

  /for i in(0...100)
    puts Dice.weapons_reward
  end/

  /for i in(0...100)
    puts Dice.shields_reward
  end/

  /for i in(0...100)
    puts Dice.health_reward
  end/

  /for i in(0...100)
    puts Dice.weapon_power
  end/

  /for i in(0...100)
    puts Dice.shield_power
  end/

  /for i in(0...100)
    puts Dice.uses_left
  end/

  /for i in(0...100)
    puts Dice.intensity(4)
  end/

  /descarta = 0
  no_descarta = 0
  for i in(0...100)
    if Dice.discard_element(0)
      descarta = descarta + 1
    else no_descarta = no_descarta + 1
    end
  end
  puts descarta
  puts no_descarta/
'''

    #//////////////PRÁCTICA 2//////////////////////////////////////////////////////////////////////////////////

    '''
    monstruo = Monster.new("Paco",5,3)
    puts monstruo.dead
    puts monstruo.attack
    monstruo.set_pos(4,4)
    puts monstruo.to_String


    jugador = Player.new(3,5,6)
    puts jugador.dead
    jugador.set_pos(4,4)
    puts jugador.to_string

    jugador.get_weapon_shield
    puts jugador.defensive_energy

    laberinto = Labyrinth.new(6,6,5,5)
    puts laberinto.have_a_winner
    laberinto.add_monster(3,3,Monster.new("Paco",5,5))
    puts laberinto.to_string
    puts laberinto.can_step_on(5,5)

    puts laberinto.random_empty_pos


    juego = Game.new(3)


    laberinto = Labyrinth.new(6,6,5,5)
    #puts Dice.random_pos(6)
    laberinto.random_empty_pos
'''

  end
end

test = TestP1.new
test.main

end