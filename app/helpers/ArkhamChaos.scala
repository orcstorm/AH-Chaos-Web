package helpers

object ArkhamChaos {

  val specialTokens: Map[Int, String] = Map(-100 -> "Skull", -200 -> "Cultist", -300 -> "Tablet", -400 -> "Cthulhu", -500 -> "Elder Sign" )
  
  
  val zealotBag: Map[String, Vector[Int]] = Map(
	  "easy" -> Vector(1,1, 0,0,-1, -1, -1, -2, -2, -100, -100, -200, -300, -400, -500),
    "standard" -> Vector(1, 0,0,-1, -1, -1, -2, -2, -3, -4, -100, -100, -200, -300, -400, -500),
    "hard" -> Vector(0,0,0,-1,-1,-2, -2, -3, -3, -4, -5, -100, -100, -200, -300, -400, -500),
    "expert" -> Vector(0,-1, -1, -2, -2, -3, -3, -4, -4, -5, -6, -100, -100, -200, -300, -400, -500)
  )

  val dunwichBag: Map[String, Vector[Int]] = Map(
    "easy" -> Vector(1,1, 0,0,-1, -1, -1, -2, -2, -100, -100, -200, -300, -400, -500),
    "standard" -> Vector(1, 0,0,-1, -1, -1, -2, -2, -3, -4, -100, -100, -200, -300, -400, -500),
    "hard" -> Vector(0,0,0,-1,-1,-2, -2, -3, -3, -4, -5, -100, -100, -200, -300, -400, -500),
    "expert" -> Vector(0,-1, -1, -2, -2, -3, -3, -4, -4, -5, -6, -100, -100, -200, -300, -400, -500)
  )

  val bags: Map[String, Map[String, Vector[Int]]] = Map("zealot" -> zealotBag, "dunwich" -> dunwichBag)

  def pullToken(campaign: String, difficulty: String): String = {
  	val bag = bags(campaign)(difficulty)
  	val r = scala.util.Random
  	val token = bag(r.nextInt(bag.size))

  	specialTokens.contains(token) match {
  	  case true => specialTokens(token)
  	  case false => token.toString
  	}
  }

}