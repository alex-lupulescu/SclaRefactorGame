import com.adaptionsoft.games.uglytrivia.Game
import org.scalatest.funsuite.AnyFunSuite

import java.util.LinkedList

class GameTest extends AnyFunSuite{

  test("initialize test") {
    val game = new Game()
    assert(game.popQuestions.size == 50)
    assert(game.scienceQuestions.size == 50)
    assert(game.sportsQuestions.size == 50)
    assert(game.rockQuestions.size == 50)
  }

  test("Unplayable game") {
    val game = new Game()
    game.addPlayer("onePlayer")

    assert(!game.isPlayable, "Game should not be playable with only one player.")
  }

  test("Playable game") {
    val game = new Game()
    game.addPlayer("playerOne")
    game.addPlayer("playerTwo")
    game.addPlayer("playerThree")

    assert(game.isPlayable, "Game should be playable if it has 2 ore more players!")
  }

}
