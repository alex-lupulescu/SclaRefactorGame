package com.adaptionsoft.games.trivia.runner

import com.adaptionsoft.games.uglytrivia.Game
import scala.annotation.tailrec
import scala.util.Random

object GameRunner:

  def main(args: Array[String]): Unit =
    val gameSession =
      Game.initializeGame(50)
        .add("Chet")
        .add("Pat")
        .add("Sue")

    runGame(gameSession, new Random())

  @tailrec
  def runGame(gameSession: Game, rand: Random): Game =
    val rollSession = gameSession.roll(rand.nextInt(5) + 1)
    val (updatedSession, winner) = if (rand.nextInt(9) == 7) rollSession.wrongAnswer else rollSession.correctAnswer

    if (!winner) updatedSession else runGame(updatedSession, rand)

