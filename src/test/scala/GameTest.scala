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

}
