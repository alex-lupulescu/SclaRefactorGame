import com.adaptionsoft.games.uglytrivia.Constants.{POP_PLAYERS_CATEGORY, SCIENCE_PLAYERS_CATEGORY, SPORTS_PLAYERS_CATEGORY}

0 % 4
1 % 4
3 % 4
6 % 4

def tst(poz: Int): Unit = {
  poz % 4 match {
    case POP_PLAYERS_CATEGORY => println("pop")
    case SCIENCE_PLAYERS_CATEGORY => println("science")
    case SPORTS_PLAYERS_CATEGORY => println("sports")
    case _ => println("rock")
  }
}

tst(0)
tst(1)
tst(2)
tst(3)
tst(4)
tst(5)
tst(6)
tst(7)


import scala.collection.mutable.Queue

// Creăm o coadă goală
val queue: Queue[String] = Queue()

// O listă de stringuri
val stringList: List[String] = List("primul", "al doilea", "al treilea")

// Adăugăm lista în coadă
val x = Queue(stringList: _*)

// Afișăm conținutul cozii
println(x.dequeue())