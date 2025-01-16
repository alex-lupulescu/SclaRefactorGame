package com.adaptionsoft.games.uglytrivia

import com.adaptionsoft.games.uglytrivia.Constants.{DEFAULT_NUMBER_OF_QUESTIONS, POP_PLAYERS_CATEGORY, ROCK_PLAYERS_CATEGORY, SCIENCE_PLAYERS_CATEGORY, SPORTS_PLAYERS_CATEGORY}
import com.adaptionsoft.games.uglytrivia.QuestionCategories.{Pop, Rock, Science, Sports}
import com.adaptionsoft.games.uglytrivia.Questions.generateQuestions

import scala.collection.immutable.Queue
import java.util.ArrayList


class Game(numberOfQuestions: Int = DEFAULT_NUMBER_OF_QUESTIONS):
  var players: ArrayList[String] = new ArrayList[String]
  var places: Array[Int] = new Array[Int](6)
  var purses: Array[Int] = new Array[Int](6)
  var inPenaltyBox: Array[Boolean] = new Array[Boolean](6)

  val popQuestions: Queue[String] = generateQuestions(numberOfQuestions).pop
  val scienceQuestions: Queue[String] = generateQuestions(numberOfQuestions).science
  val sportsQuestions: Queue[String] = generateQuestions(numberOfQuestions).sports
  val rockQuestions: Queue[String] = generateQuestions(numberOfQuestions).rock

  var currentPlayer: Int = 0
  var isGettingOutOfPenaltyBox: Boolean = false
  
  // to be used after all players are added to the game in order to validate it
  def isPlayable: Boolean = (howManyPlayers >= 2)

  def addPlayer(playerName: String): Boolean =
    players.add(playerName)
    places(howManyPlayers) = 0
    purses(howManyPlayers) = 0
    inPenaltyBox(howManyPlayers) = false
    println(playerName + " was added")
    println("They are player number " + players.size)
    true

  def howManyPlayers: Int = players.size

  def roll(roll: Int): Unit =
    println(players.get(currentPlayer) + " is the current player")
    println("They have rolled a " + roll)
    if (inPenaltyBox(currentPlayer)) {
      if (roll % 2 != 0) {
        isGettingOutOfPenaltyBox = true
        println(players.get(currentPlayer) + " is getting out of the penalty box")
        places(currentPlayer) = places(currentPlayer) + roll
        if (places(currentPlayer) > 11) places(currentPlayer) = places(currentPlayer) - 12
        println(players.get(currentPlayer) + "'s new location is " + places(currentPlayer))
        println("The category is " + currentCategory)
        askQuestion(currentCategory)
      }
      else {
        println(players.get(currentPlayer) + " is not getting out of the penalty box")
        isGettingOutOfPenaltyBox = false
      }
    }
    else {
      places(currentPlayer) = places(currentPlayer) + roll
      if (places(currentPlayer) > 11) places(currentPlayer) = places(currentPlayer) - 12
      println(players.get(currentPlayer) + "'s new location is " + places(currentPlayer))
      println("The category is " + currentCategory)
      askQuestion(currentCategory)
    }

  private def askQuestion(currentCategory: String): Unit =
    currentCategory match {
      case Pop => println(popQuestions.dequeue)
      case Science => println(scienceQuestions.dequeue)
      case Sports => println(sportsQuestions.dequeue)
      case Rock => println(rockQuestions.dequeue)
      case _ => println(s"UNKNOWN CATEGORY DETECTED! $currentCategory")
    }

  private def currentCategory: String =
    /*
    4 may be the number of categories
      1. first category is pop <=> 0
      2. second category is science <=> 1
      3. third category is sports <=> 2
      4. fourth category is rock <=> 3
     */
    val numberOfGameCategories = 4
    
    places(currentPlayer) % numberOfGameCategories match {
      case POP_PLAYERS_CATEGORY => Pop
      case SCIENCE_PLAYERS_CATEGORY => Science
      case SPORTS_PLAYERS_CATEGORY => Sports
      case ROCK_PLAYERS_CATEGORY => Rock
      case _ => throw new Exception("ERROR IN CURRENT CATEGORY")
    }

  def wasCorrectlyAnswered: Boolean =
    if (inPenaltyBox(currentPlayer)) {
      if (isGettingOutOfPenaltyBox) {
        println("Answer was correct!!!!")
        purses(currentPlayer) += 1
        println(players.get(currentPlayer) + " now has " + purses(currentPlayer) + " Gold Coins.")
        var winner: Boolean = didPlayerWin
        currentPlayer += 1
        if (currentPlayer == players.size) currentPlayer = 0
        winner
      }
      else {
        currentPlayer += 1
        if (currentPlayer == players.size) currentPlayer = 0
        true
      }
    }
    else {
      println("Answer was corrent!!!!")
      purses(currentPlayer) += 1
      println(players.get(currentPlayer) + " now has " + purses(currentPlayer) + " Gold Coins.")
      var winner: Boolean = didPlayerWin
      currentPlayer += 1
      if (currentPlayer == players.size) currentPlayer = 0
      winner
    }

  def wrongAnswer: Boolean =
    println("Question was incorrectly answered")
    println(players.get(currentPlayer) + " was sent to the penalty box")
    inPenaltyBox(currentPlayer) = true
    currentPlayer += 1
    if (currentPlayer == players.size) currentPlayer = 0
    true

  private def didPlayerWin: Boolean = !(purses(currentPlayer) == 6)
