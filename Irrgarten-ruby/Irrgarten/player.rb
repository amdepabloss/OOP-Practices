# frozen_string_literal: true
require_relative 'dice'
require_relative 'orientation'
require_relative 'directions'
module Irrgarten

class Player < LabyrinthCharacter
  @@MAX_WEAPONS = 2
  @@MAX_SHIELDS = 3
  @@INITIAL_HEALTH = 1
  @@HITS2LOSE = 3

  def initialize(num,i,s)
    super("Player #{num}",i,s,@@INITIAL_HEALTH)
    @number = num
    @consecutive_hits = 0
    @weapons= Array.new
    @shields = Array.new
  end

  def copy(other)
    super(other)
    #@number = other.get_number
    #@health = @@INITIAL_HEALTH
    @consecutive_hits = 0
    @weapons = other.weapons.dup
    @shields = other.shields.dup


  end

  def resurrect
    @weapons.clear
    @shields.clear
    reset_hits
    @health = @@INITIAL_HEALTH
  end

  def get_number
    @number
  end

  def move (direction,valid_moves)
    size = valid_moves.size
    contained = valid_moves.include?(direction)
    if((size > 0) && (!contained))
      return valid_moves[0]
    else
      return direction
    end
  end

  def attack
    @strength + sum_weapons
  end

  def defend(received_attack)
    manage_hit(received_attack)
  end

  def receive_reward
    wReward = Dice.weapons_reward
    sReward = Dice.shields_reward
    (0..wReward).each do
      wnew = new_weapon
      receive_weapon(wnew)
    end
    (0..sReward).each do
      snew = new_shield
      receive_weapon(snew)
    end
    extra_health = Dice.health_reward
    @health += extra_health
  end

  def weapons_to_string
    armas = ""
    @weapons.each do |weapon|
      armas += weapon.to_s + "\n"
    end
    armas
  end

  def shields_to_string
    escudos = ""
    @shields.each do |weapon|
      escudos += weapon.to_s + "\n"
    end
    escudos
  end


  def to_string
    "P[#{super.to_s},#{weapons_to_string},#{shields_to_string},#{@consecutive_hits}]"   #OJO COMPROBAR
  end

  private

  def receive_weapon(w)
    @weapons.reverse_each do |e|
      discard = e.discard
      if(discard)
        @weapons.delete(e)
      end
    end
    size = @weapons.length
    if(size<@@MAX_WEAPONS)
      @weapons.push(w)
    end
  end

  def receive_shield(s)
    @shields.reverse_each do |e|
      discard =  e.discard
      if(discard)
        @shields.delete(e)
      end
    end
    size = @shields.length
    if(size<@@MAX_SHIELDS)
      @shields.push(s)
    end
  end

  def new_weapon
    Weapon.new(Dice.weapon_power, Dice.uses_left)
  end

  def new_shield
    Shield.new(Dice.shield_power, Dice.uses_left)
  end

  def manage_hit(received_attack)
    defense = defensive_energy
    if(defense<received_attack)
      got_wounded
      inc_consecutive_hits
    else
      reset_hits
    end

    if((@consecutive_hits == @@HITS2LOSE)||(dead))
      reset_hits
      lose = true
    else
      lose = false
    end
    lose
  end

  def reset_hits
    @consecutive_hits = 0
  end

  def inc_consecutive_hits
    @consecutive_hits +=1
  end
  
  protected
  def sum_shields
    suma = 0
    @shields.each do |s|
      suma += s.protect
    end
    suma
  end

  def sum_weapons
    suma = 0
    @weapons.each do |s|
      suma +=  s.attack
    end
    suma
  end

  def defensive_energy
    @intelligence + sum_shields()
  end

  public_class_method :new
end
end