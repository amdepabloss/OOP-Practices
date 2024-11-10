# frozen_string_literal: true

module Irrgarten
  require_relative 'dice'
  require_relative 'combat_element'

class Weapon < CombatElement

  def initialize(e,u)
    super(e,u)
  end

  def attack
    produce_effect
  end

  def to_string
    "W[#{super.to_string}"
  end
  public_class_method :new
end
end



