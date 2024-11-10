# frozen_string_literal: true
require_relative 'labyrinth'
require_relative 'player'
require_relative 'monster'
require_relative 'dice'
require_relative 'orientation'
require_relative 'game_character'

module Irrgarten

class FuzzyPlayer < Player

  def initialize(other)
    copy(other)
  end

  def move(direction, valid_moves)
    Dice.next_step(super(direction, valid_moves), valid_moves, @intelligence)
  end

  def attack
    Dice.intensity(strength) + sum_weapons
  end

  def defensive_energy
    Dice.intensity(intelligence) + sum_shields
  end

  def to_string
    "Fuzzy #{super}"
  end


end
end
