# frozen_string_literal: true
require_relative '../Control/controller'
require_relative '../UI/text_ui'
require_relative '../Irrgarten/game'
require_relative '../Irrgarten/labyrinth'
require_relative '../Irrgarten/monster'
require_relative '../Irrgarten/player'
require_relative '../Irrgarten/dice'
require_relative '../Irrgarten/game_character'
require_relative '../Irrgarten/orientation'
require_relative '../Irrgarten/directions'
require_relative '../Irrgarten/game_state'
require_relative '../Irrgarten/weapon'
require_relative '../Irrgarten/shield'

module Main

  class Main

    vista = UI::TextUI.new

    juego = Irrgarten::Game.new(2)

    controlador = Control::Controller.new(juego, vista)

    puts "Indico al controlador que empiece la partida"
    controlador.play


  end
end