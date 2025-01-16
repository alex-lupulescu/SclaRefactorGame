package com.adaptionsoft.games.uglytrivia

import com.adaptionsoft.games.uglytrivia.Constants.DEFAULT_NUMBER_OF_QUESTIONS
import com.adaptionsoft.games.uglytrivia.QuestionCategories.{Pop, Rock, Science, Sports}

import scala.collection.immutable.Queue

final case class Questions(
                            pop: Queue[String],
                            science: Queue[String],
                            sports: Queue[String],
                            rock: Queue[String]
                    )

object Questions {
  val questions: Questions = {
    def populateQuestions(category: String, numberOfQuestions: Int = DEFAULT_NUMBER_OF_QUESTIONS) = {
      (1 to numberOfQuestions)
        .map(questionNumber => s"$category Question $questionNumber")
        .toList
    }

    Questions(pop = Queue(populateQuestions(Pop): _*),
      science = Queue(populateQuestions(Science): _*),
      sports = Queue(populateQuestions(Sports): _*),
      rock = Queue(populateQuestions(Rock): _*)
    )
  }
  
}
