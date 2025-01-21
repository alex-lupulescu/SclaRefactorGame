package com.adaptionsoft.games.uglytrivia

import com.adaptionsoft.games.uglytrivia.Constants.DEFAULT_NUMBER_OF_QUESTIONS
import com.adaptionsoft.games.uglytrivia.QuestionCategories.{Pop, Rock, Science, Sports}

final case class Questions(
                            pop: List[String],
                            science: List[String],
                            sports: List[String],
                            rock: List[String]
                    )

object Questions {
  val initializeQuestions: Questions = {
    def populateQuestions(category: String, numberOfQuestions: Int = DEFAULT_NUMBER_OF_QUESTIONS) = {
      List.tabulate(numberOfQuestions)(question => s"$category Question $question")
    }

    Questions(pop = populateQuestions(Pop),
      science = populateQuestions(Science),
      sports = populateQuestions(Sports),
      rock = populateQuestions(Rock)
    )
  }
  
}
