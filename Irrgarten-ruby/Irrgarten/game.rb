# frozen_string_literal: true
require_relative 'labyrinth'
require_relative 'player'
require_relative 'monster'
require_relative 'dice'
require_relative 'orientation'
require_relative 'game_character'
require_relative 'fuzzy_player'
require_relative 'directions'
module Irrgarten

class Game

  @@MAX_ROUNDS = 10

  def initialize(n_players)
    @current_player_index = Dice.who_starts(n_players)
    @log=""
    @players = Array.new
    @monsters = Array.new

    @labyrinth= Labyrinth.new(6,6,5,5)


    #Forma de iterar teniendo un vector vacío y que vas a querer ir rellenando.
    for i in 0...n_players
       @players.push(Player.new(i, 1,1))
      end

      #n_players.times do |i|
      #@players << (Player.new(i.to_s[0], Dice.random_intelligence,Dice.random_strength))
    @current_player=@players[@current_player_index]
    configure_labyrinth

  end

  def  finished
    return @labyrinth.have_a_winner
  end

  #Metodos aux para poder meter en los parametros de Game_State
  def players_to_s
    jugadores = ""
    @players.each do |i|
      jugadores += i.to_string
      jugadores += "\n"
    end
    jugadores
  end


  def monsters_to_s
    monstruos = ""
    @monsters.each do |i|
      monstruos += i.to_string

      monstruos += "\n"
    end
    monstruos
  end


  def get_game_state
    GameState.new(@labyrinth.to_string, players_to_s, monsters_to_s, @current_player_index,finished,@log)
  end

  def next_step(preferred_direction)
    dead = @current_player.dead
    if (!dead)
      direction = actual_direction(preferred_direction)
      if (direction != preferred_direction)
        log_player_no_orders
      end
      monster = @labyrinth.put_player(direction, @current_player)
      if (monster == nil)
        log_no_monster
      else
        winner = combat(monster)
        manage_reward(winner)
      end
    else
      manage_resurrection
    end
    end_game = finished

    if(!end_game)
      next_player
    end
    end_game
  end

  private

  def configure_labyrinth

    monstruo0 = Monster.new("Monstruo0",10000,10000)
    monstruo1 = Monster.new("Monstruo1",10000,10000)
    @labyrinth.add_monster(1,1, monstruo0)
    @labyrinth.add_monster(3,3, monstruo1)
    @monsters.push(monstruo0)
    @monsters.push(monstruo1)
    @labyrinth.add_block(Orientation::HORIZONTAL,0,0,1)
    @labyrinth.add_block(Orientation::HORIZONTAL,1,3,2)
    @labyrinth.add_block(Orientation::HORIZONTAL,2,0,1)
    @labyrinth.add_block(Orientation::HORIZONTAL,3,1,1)
    @labyrinth.add_block(Orientation::HORIZONTAL,4,3,1)
    @labyrinth.spread_players(@players)
  end

  def next_player
    @current_player_index = (@current_player_index+1)%@players.length
    @current_player = @players[@current_player_index]
  end

  def log_player_won
    @log+="El jugador actual ha ganado\n"
  end


  def log_monster_won
    @log+="El monstruo actual ha ganado\n"
  end


  def log_resurrected
    @log+="El jugador actual ha resucitado\n"

  end


  def log_player_skip_turn
    @log+="El jugador ha perdido el turno por estar muerto\n"

  end


  def log_player_no_orders
    @log+="El jugador no ha seguido las instrucciones del jugador humano (no fue posible)\n"

  end


  def log_no_monster
    @log+="El jugador se ha movido a una celda vacía o no le ha sido posible moverse\n"

  end


  def log_rounds(rounds,max)
    @log+="Se han producido #{rounds} de #{max} rondas de combate\n"

  end


  def actual_direction(preferred_direction)
    current_row = @current_player.get_row
    current_col = @current_player.get_col
    valid_moves = @labyrinth.valid_moves(current_row,current_col)
    output = @current_player.move(preferred_direction,valid_moves)
    output
  end


  def combat(monster)
    rounds = 0
    winner = GameCharacter::PLAYER
    player_attack = @current_player.attack
    lose = monster.defend(player_attack)
    while((!lose) && (rounds < @@MAX_ROUNDS))
      winner = GameCharacter::MONSTER
      rounds=rounds+1
      monster_attack = monster.attack
      lose = @current_player.defend(monster_attack)

      if(!lose)
        player_attack = @current_player.attack
        winner = GameCharacter::PLAYER
        lose = monster.defend(player_attack)
      end
    end
    log_rounds(rounds,@@MAX_ROUNDS)
    winner
  end

  def manage_reward(winner)
    if(winner == GameCharacter::PLAYER)
      @current_player.receive_reward
      log_player_won
    else
      log_monster_won
    end
  end

  def manage_resurrection
    resurrect = Dice.resurrect_player
    if(resurrect)
      @current_player.resurrect
      jugador_tonto = FuzzyPlayer.new(@current_player) #donde pone new teníamos copy
      @current_player = jugador_tonto
      @players.insert(@current_player_index,@current_player)
      @labyrinth.colocar_fuzzy(jugador_tonto)

      log_resurrected
      else log_player_skip_turn
    end
  end



end
end


