package com.adaptionsoft.games.uglytrivia

case class Player(playerName: String,
                  places: Int = 0,
                  purses: Int = 0,
                  inPenaltyBox: Boolean = false,
                  isGettingOutOfPenaltyBox: Boolean = false)
