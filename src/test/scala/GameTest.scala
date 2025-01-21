import com.adaptionsoft.games.trivia.runner.GameRunner
import com.adaptionsoft.games.uglytrivia.Constants.DEFAULT_NUMBER_OF_QUESTIONS
import com.adaptionsoft.games.uglytrivia.Game
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers.shouldBe

import scala.util.Random

class GameTest extends AnyFunSuite{

  def newGame: Game = Game.initializeGame(numberOfQuestions = DEFAULT_NUMBER_OF_QUESTIONS)

  test("TG01: GameRunner produces expected outcome with fixed random sequence") {
    val randomSeed = Random
    randomSeed.setSeed(888)

    val gameSession = Game.initializeGame(50)
      .add("Chet")
      .add("Pat")
      .add("Sue")

    val finalSession = GameRunner.runGame(gameSession, randomSeed)

    assert(finalSession.players(finalSession.currentPlayerId - 1).playerName.equals("Pat"))
  }

  test("TG02: Test adding new player") {
    val game = newGame
    val updatedGame = game.add("Chet")

    assert(updatedGame.players.size.equals(1))
    assert(updatedGame.players.head.playerName.equals("Chet"))
  }

  test("TG03: When roll and player is not in penalty box, player should move forward") {
    val gameSession = newGame
      .add("Chet")

    val rolledGame = gameSession.roll(3)

    val player = rolledGame.players.head
    assert(player.places == 3)
    assert(!player.inPenaltyBox)
  }

  test("TG04:  When roll and player is in penalty box, roll even => should stay") {
    val gameSession = newGame
      .add("Chet")

    val playerInBox = gameSession.copy(
      players = gameSession.players.updated(0,
        gameSession.players.head.copy(inPenaltyBox = true)
      )
    )

    val rolledGame = playerInBox.roll(4)

    val player = rolledGame.players.head
    assert(player.inPenaltyBox)
    assert(player.places == 0)
  }

  test("TG05: When roll and player is in penalty box, odd roll => move out") {
    val gameSession = newGame
      .add("Chet")

    val playerInBox = gameSession.copy(
      players = gameSession.players.updated(0,
        gameSession.players.head.copy(inPenaltyBox = true)
      )
    )

    val rolledGame = playerInBox.roll(3)
    val player = rolledGame.players.head
    println(player)

    assert(player.inPenaltyBox)
    assert(player.isGettingOutOfPenaltyBox)
    assert(player.places == 3)
  }

  test("TG06: CorrectAnswer player gain 1 point") {
    val gameSession = newGame
      .add("Chet")
      .add("Pat")

    val (afterAnswer, winner) = gameSession.correctAnswer

    val player = afterAnswer.players.head
    assert(player.purses == 1)
    assert(winner)

    val currentPlayer = afterAnswer.players(afterAnswer.currentPlayerId)
    assert(currentPlayer.playerName == "Pat")
  }

  test("TG06: WrongAnswer player get in penalty box") {
    val gameSession = newGame
      .add("Chet")
      .add("Pat")

    val (updatedGame, winner) = gameSession.wrongAnswer

    val player = updatedGame.players.head
    assert(player.inPenaltyBox)
    assert(winner)

    val currentPlayer = updatedGame.players(updatedGame.currentPlayerId)
    assert(currentPlayer.playerName == "Pat")
  }

}
