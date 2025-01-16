package com.adaptionsoft.games.uglytrivia

import com.adaptionsoft.games.uglytrivia.QuestionCategories.{Pop, Rock, Science, Sports}

import scala.collection.immutable.Queue

final case class Questions(
                            pop: Queue[String],
                            science: Queue[String],
                            sports: Queue[String],
                            rock: Queue[String]
                    )

object Questions {
  def generateQuestions(numberOfQuestions: Int): Questions = {
    Questions(pop = Queue(populateQuestions(Pop, numberOfQuestions): _*),
      science = Queue(populateQuestions(Science, numberOfQuestions): _*),
      sports = Queue(populateQuestions(Sports, numberOfQuestions): _*),
      rock = Queue(populateQuestions(Rock, numberOfQuestions): _*)
    )
  }
  
  private def populateQuestions(category: String, numberOfQuestions: Int) = {
    (1 to numberOfQuestions)
      .map(questionNumber => s"$category Question $questionNumber")
      .toList
  }
  
}
