package com.adaptionsoft.games.uglytrivia

import com.adaptionsoft.games.uglytrivia.QuestionCategories.{Pop, Rock, Science, Sports}


case class Game(players: List[Player],
                popQuestions: List[String],
                scienceQuestions: List[String],
                sportsQuestions: List[String],
                rockQuestions: List[String],
                currentPlayerId: Int) {

  private def isPlayable: Boolean = players.size >= 2

  private def currentPlayer: Player = players(currentPlayerId)

  private def currentPlayerName: String = currentPlayer.playerName

  private def currentPlayerPosition: Int = currentPlayer.places

  private def currentPlayerPoints: Int = currentPlayer.purses

  private def totalNumberOfPlayers: Int = players.size

  def add(playerName: String): Game =
    val newPlayer = Player(playerName)
    val updatedSession = copy(players = players :+ newPlayer)
    println(s"${newPlayer.playerName} was added")
    println(s"They are player number ${updatedSession.totalNumberOfPlayers}")
    updatedSession

  def roll(roll: Int): Game =
    println(s"$currentPlayerName is the current player")
    println(s"They have rolled a $roll")
    if (currentPlayer.inPenaltyBox) {
      if (roll % 2 != 0) {
        println(s"$currentPlayerName is getting out of the penalty box")
        val updatedSession = changePlayerPosition(roll)
        updatedSession.copy(players.updated(currentPlayerId, updatedSession.currentPlayer.copy(isGettingOutOfPenaltyBox = true)))
      } else {
        println(s"$currentPlayerName is not getting out of the penalty box")
        copy(players.updated(currentPlayerId, currentPlayer.copy(isGettingOutOfPenaltyBox = false)))
      }
    } else {
      changePlayerPosition(roll)
    }

  private def changePlayerPosition(roll: Int) = {
    val updatedGame = movePlayer(roll)
    val newLocation = updatedGame.currentPlayerPosition
    println(s"$currentPlayerName's new location is $newLocation")
    println(s"The category is ${updatedGame.currentCategory(newLocation)}")
    updatedGame.askQuestion(newLocation)
  }

  private def movePlayer(roll: Int): Game =
    val newLocation = (currentPlayerPosition + roll) % 12
    copy(players.updated(currentPlayerId, currentPlayer.copy(places = newLocation)))

  private def currentCategory(position: Int): String = position match
    case 0 | 4 | 8 => Pop
    case 1 | 5 | 9 => Science
    case 2 | 6 | 10 => Sports
    case _ => Rock

  private def askQuestion(position: Int): Game =
    currentCategory(position) match
      case Pop =>
        println(popQuestions.head)
        copy(popQuestions = popQuestions.tail)
      case Science =>
        println(scienceQuestions.head)
        copy(scienceQuestions = scienceQuestions.tail)
      case Sports =>
        println(sportsQuestions.head)
        copy(sportsQuestions = sportsQuestions.tail)
      case Rock =>
        println(rockQuestions.head)
        copy(rockQuestions = rockQuestions.tail)
      case _ =>
        println("ERROR: Undefined category selected!")
        this

  def correctAnswer: (Game, Boolean) =
    if (currentPlayer.inPenaltyBox) {
      if (currentPlayer.isGettingOutOfPenaltyBox) {
        println("Answer was correct!!!!")
        val updatedGameSessionAddPoints = addPoints()
        println(s"$currentPlayerName now has ${updatedGameSessionAddPoints.currentPlayerPoints} Gold Coins.")
        val winner = updatedGameSessionAddPoints.didPlayerWin
        val updatedGameSessionNewPlayer = updatedGameSessionAddPoints.advanceToTheNextPlayer
        (updatedGameSessionNewPlayer, winner)
      }
      else {
        (advanceToTheNextPlayer, true)
      }
    }
    else {
      println("Answer was correct!!!!")
      val updatedGameSessionAddPoints = addPoints()
      println(s"$currentPlayerName now has ${updatedGameSessionAddPoints.currentPlayerPoints} Gold Coins.")
      val winner = updatedGameSessionAddPoints.didPlayerWin
      val updatedGameSessionNewPlayer = updatedGameSessionAddPoints.advanceToTheNextPlayer
      (updatedGameSessionNewPlayer, winner)
    }

  private def addPoints(): Game = copy(players.updated(currentPlayerId, currentPlayer.copy(purses = currentPlayerPoints + 1)))

  private def didPlayerWin: Boolean = !(currentPlayerPoints == 6)

  private def advanceToTheNextPlayer: Game =
    val newPlayer = (currentPlayerId + 1) % players.size
    copy(currentPlayerId = newPlayer)

  def wrongAnswer: (Game, Boolean) =
    println("Question was incorrectly answered")
    println(s"$currentPlayerName was sent to the penalty box")
    val updatedGameSession = setCurrentPlayerInPenaltyBox(true).advanceToTheNextPlayer
    (updatedGameSession, true)

  private def setCurrentPlayerInPenaltyBox(inBox: Boolean): Game =
    copy(players.updated(currentPlayerId, currentPlayer.copy(inPenaltyBox = inBox)))
}

object Game {
  def initializeGame(numberOfQuestions: Int): Game = {
    val questions = Questions.initializeQuestions

    Game(
      players = List.empty,
      popQuestions = questions.pop,
      scienceQuestions = questions.science,
      sportsQuestions = questions.sports,
      rockQuestions = questions.rock,
      currentPlayerId = 0
    )
  }
}
