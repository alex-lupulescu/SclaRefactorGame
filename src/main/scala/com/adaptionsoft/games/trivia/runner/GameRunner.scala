package com.adaptionsoft.games.trivia.runner

import com.adaptionsoft.games.uglytrivia.Game
import java.util.Random


object GameRunner:
  var notAWinner = true

  @main def main(): Unit =
    var aGame = new Game();
    aGame.addPlayer("Chet")
    aGame.addPlayer("Pat")
    aGame.addPlayer("Sue")

    var rand: Random = new Random

    while notAWinner do
      aGame.roll(rand.nextInt(5) + 1)
      if (rand.nextInt(9) == 7) {
        notAWinner = aGame.wrongAnswer
      }
      else {
        notAWinner = aGame.wasCorrectlyAnswered
      }
