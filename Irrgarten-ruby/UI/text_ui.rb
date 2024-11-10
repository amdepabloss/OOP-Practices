# frozen_string_literal: true

require 'io/console'
require_relative '../Irrgarten/directions'
require_relative '../Irrgarten/game_state'


module UI

  class TextUI

    def read_char
      STDIN.echo = false
      STDIN.raw!

      input = STDIN.getc.chr
      if input == "\e"
        input << STDIN.read_nonblock(3) rescue nil
        input << STDIN.read_nonblock(2) rescue nil
      end
    ensure
      STDIN.echo = true
      STDIN.cooked!

      return input
    end

    def next_move
      print "Where? "
      got_input = false
      while (!got_input)
        c = gets.chomp
        case c
        when "w"
          puts "UP ARROW"
          output = Irrgarten::Directions::UP
          got_input = true
        when "s"
          puts "DOWN ARROW"
          output = Irrgarten::Directions::DOWN
          got_input = true
        when "d"
          puts "RIGHT ARROW"
          output = Irrgarten::Directions::RIGHT
          got_input = true
        when "a"
          puts "LEFT ARROW"
          output = Irrgarten::Directions::LEFT
          got_input = true
        when "\u0003"
          puts "CONTROL-C"
          got_input = true
          exit(1)
        else
          #Error
        end
      end
      output
    end

    def show_game(get_game_state)
      puts "Representación del laberinto: \n"
      puts get_game_state.labyrinthv
      puts "Jugador actual: \n"
      puts (get_game_state.current_players).to_s
      puts "Representación de los monstruos: \n"
      puts get_game_state.monsters
      puts "Representación de los jugadores: \n"
      puts get_game_state.players
      puts "--------------------------------------"
      puts "Representación del log: \n"
      puts get_game_state.log

      puts "Representación del ganador: \n"
      puts get_game_state.winner

    end

  end # class

end # module