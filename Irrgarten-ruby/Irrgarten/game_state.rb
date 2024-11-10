# frozen_string_literal: true
module Irrgarten
class GameState
  @labyrinthv
  @players
  @monsters
  @current_player
  @winner
  @log

  def initialize(l,p,m,cp,w,log_)
    @labyrinthv = l
    @players = p
    @monsters = m
    @current_player = cp
    @winner = w
    @log = log_
  end

  def labyrinthv
    return @labyrinthv
  end

  def players
    return @players
  end

  def monsters
    return @monsters
  end

  def current_players
    return @current_player
  end

  def winner
    return @winner
  end

  def log
    return @log
  end
end
end
