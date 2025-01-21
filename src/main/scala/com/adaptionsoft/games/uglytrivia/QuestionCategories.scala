package com.adaptionsoft.games.uglytrivia

object QuestionCategories extends Enumeration {
  private type QuestionCategory = String

  val Pop: QuestionCategory = "Pop"
  val Science: QuestionCategory = "Science"
  val Sports: QuestionCategory = "Sports"
  val Rock: QuestionCategory = "Rock"
}