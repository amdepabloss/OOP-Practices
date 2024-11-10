# frozen_string_literal: true

require_relative 'monster'
require_relative 'directions'
require_relative 'dice'
module Irrgarten

class Labyrinth
  @@BLOCK_CHAR = 'X'
  @@EMPTY_CHAR = '-'
  @@MONSTER_CHAR = 'M'
  @@COMBAT_CHAR = 'C'
  @@EXIT_CHAR = 'E'
  @@COL = 1
  @@ROW = 0

  def initialize(nrow, ncol, er, ec)
    @n_rows = nrow
    @n_cols = ncol
    @exit_row = er
    @exit_col = ec
    @labyrinth = Array.new(@n_rows){Array.new(@n_cols){@@EMPTY_CHAR}}
    @labyrinth[@exit_row][@exit_col] = @@EXIT_CHAR
    @monsters = Array.new(@n_rows){Array.new(@n_cols){nil}}
    @players = Array.new(@n_rows){Array.new(@n_cols){nil}}
  end

  def have_a_winner

    if @players[@exit_row][@exit_col] == nil
      hay_ganador= false  #NO HAY JUGADOR EN LA CASILLA DE SALIDA
      else hay_ganador = true
    end
    hay_ganador
  end


  def to_string

    labyrinth_string = ""
    @labyrinth.each do |fila|
      fila.each do |elemento|
        labyrinth_string+= "#{elemento}"
      end
      labyrinth_string+="\n"
    end
    puts " "

    labyrinth_string
  end

  def add_monster(row, col, monster)
    if pos_ok(row,col) && empty_pos(row,col)
      @labyrinth[row][col] = @@MONSTER_CHAR
      @monsters[row][col] = monster
      monster.set_pos(row, col)
    end
  end

  def spread_players(players)                                               #REVISAR
    players.each do |e|
      pos = random_empty_pos
      put_player_2D(-1,-1,pos[@@ROW],pos[@@COL],e)
    end
  end

  def put_player(direction, player)                                                     #REVISAR
    old_row = player.get_row
    old_col = player.get_col
    new_pos = dir2_pos(old_row,old_col,direction)
    monster = put_player_2D(old_row, old_col, new_pos[@@ROW], new_pos[@@COL], player)
    monster
  end

  def add_block(orientation, start_row, start_col, length)                              #REVISAR

    if(orientation == Orientation::VERTICAL)
      inc_row = 1
      inc_col = 0
    else
      inc_row = 0
      inc_col = 1
    end
    row = start_row
    col = start_col

    while((pos_ok(row,col))&&(empty_pos(row,col))&&(length > 0))
      @labyrinth[row][col] = @@BLOCK_CHAR
      length-=1
      row += inc_row
      col += inc_col
    end
  end

  def valid_moves(row, col)
    output = Array.new
    if(can_step_on(row+1,col))
      output.push(Directions::DOWN)
    end
    if(can_step_on(row-1,col))
      output.push(Directions::UP)
    end
    if(can_step_on(row,col+1))
      output.push(Directions::RIGHT)
    end
    if(can_step_on(row,col-1))
      output.push(Directions::LEFT)
    end
    output
  end

  def colocar_fuzzy(jugador_tonto)
    update_old_pos(jugador_tonto.row, jugador_tonto.col)
    put_player_2D(-1,-1,jugador_tonto.row,jugador_tonto.col,jugador_tonto)
  end

  #/////////////////////////////////////////////////////////////

  private
  def pos_ok(row, col)

    if(row<@n_rows && col<@n_cols && row>=0 && col>=0)
      return true
    end

  end

  def empty_pos(row,col)
    vacia = false
    if(@labyrinth[row][col] == @@EMPTY_CHAR)
      vacia = true
    end
    vacia
  end

  def monster_pos(row, col)
    monstruo = false
    if (@labyrinth[row][col] == @@MONSTER_CHAR)
      monstruo = true
    end
    monstruo
  end

  def exit_pos(row,col)
    salida = false
    if(@labyrinth[row][col] == @@EXIT_CHAR)
      salida = true
    end
    salida
  end

  def combat_pos(row,col)
    combate = false
    if(@labyrinth[row][col] == @@COMBAT_CHAR)
      combate = true
    end
    combate
  end


  def can_step_on(row,col)
    if pos_ok(row, col)
      return empty_pos(row, col) || monster_pos(row, col) || exit_pos(row,col)
    end
    return false
  end


  def update_old_pos(row,col)
    if (pos_ok(row,col))
      if(combat_pos(row,col))
        @labyrinth[row][col] = @@MONSTER_CHAR
      else
        @labyrinth[row][col] = @@EMPTY_CHAR
      end
    end
  end


  def dir2_pos(row,col,direction)

    if(direction == Directions::LEFT )
      col -=1
    end

    if(direction == Directions::RIGHT)
      col +=1
    end

    if(direction == Directions::UP)
      row -=1
    end

    if(direction == Directions::DOWN)
      row +=1
    end

    [row,col]
  end

  def random_empty_pos

    x=Dice.random_pos(@n_rows)
    y=Dice.random_pos(@n_cols)

    while @labyrinth[x][y] != @@EMPTY_CHAR
    x=Dice.random_pos(@n_rows)
    y=Dice.random_pos(@n_cols)
    end
    return [x,y]
  end
  '''def random_empty_pos

    fila = Dice.random_pos(@n_rows)
    columna = Dice.random_pos(@n_cols)

    while !empty_pos(fila,columna)
      fila = Dice.random_pos(@n_rows)
      columna = Dice.random_pos(@n_col)
    end

    posicion = [fila,columna]
    posicion
  end'''

  def put_player_2D(old_row,old_col,row,col,player)

    output = nil
    if(can_step_on(row,col))
      if(pos_ok(old_row, old_col))
        p = @players[old_row][old_col]
        if(p == player)
          update_old_pos(old_row,old_col)
          @players[old_row][old_col] = nil
        end
      end
      monster_pos = monster_pos(row,col)
      if(monster_pos)
        @labyrinth[row][col]=@@COMBAT_CHAR
        output = @monsters[row][col]
      else
        number = player.get_number
        @labyrinth[row][col] = number
      end
      @players[row][col] = player
      player.set_pos(row,col)
    end
    output

  end
end
end
