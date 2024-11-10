# frozen_string_literal: true


module Irrgarten

class LabyrinthCharacter
  attr_reader :intelligence, :name, :strength, :health
  attr_accessor :health, :intelligence, :name, :strength, :weapons, :shields, :row, :col

  def initialize(n,i,s,h)

    @name = n
    @intelligence = i
    @strength = s
    @health = h
    @row = -1
    @col = -1
  end

  def copy(other)
    #initialize(other.name,other.intelligence,other.strength,other.health)
    @name = other.name
    @intelligence = other.intelligence
    @strength = other.strength
    @health = other.health
    @row = other.row
    @col = other.col

  end

  def dead
    muerto = false
    if @health == 0
      muerto = true
    end
    muerto
  end


  def get_row
    @row
  end

  def get_col
    @col
  end


  def set_pos(row, col)
    @row = row
    @col = col
  end

  def to_string
    return "[#{@name},#{@intelligence},#{@strength},#{@health},#{@row},#{@col}]"
  end

  protected
  def got_wounded
    @health = @health-1
  end


  private_class_method :new

end
end
