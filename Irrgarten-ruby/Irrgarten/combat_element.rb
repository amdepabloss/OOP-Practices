# frozen_string_literal: true

module Irrgarten
class CombatElement

  def initialize(effect_,uses_)
    @effect = effect_
    @uses = uses_

  end

  def to_string
    "{#{@effect},#{@uses}"
  end

  def discard
    Dice.discard_element(@uses)
  end

  protected
  def produce_effect
    if (@uses > 0)
      @uses -=1
    else
      return 0
    end
    return @effect
  end

  private_class_method :new
  end
end
