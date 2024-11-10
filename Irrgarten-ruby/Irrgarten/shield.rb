# frozen_string_literal: true

require_relative 'dice'
module Irrgarten

class Shield < CombatElement

  def initialize(p,u)
    super(p,u)
  end

  def protect
     produce_effect
  end

  def to_string
    "S[#{super.to_string}"
  end
  public_class_method :new
end
end
