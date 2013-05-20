package dk.itu

object SemesterData {
  /*
  Map of semesters to ids. F = Fall, S = Spring
  id is created by ITU's it department. Found by looking at the POST data in the courses base
  When new semesters are created you only need to update this map
   */
  val semester_ids = Map(
    "F1999" -> "127260",
    "S2000" -> "127262",
    "F2000" -> "127264",
    "S2001" -> "127266",
    "F2001" -> "127268",
    "S2002" -> "127270",
    "F2002" -> "127272",
    "S2003" -> "127274",
    "F2003" -> "127276",
    "S2004" -> "127278",
    "F2004" -> "127280",
    "S2005" -> "127282",
    "F2005" -> "177371",
    "S2006" -> "235230",
    "F2006" -> "253799",
    "S2007" -> "293157",
    "F2007" -> "409901",
    "S2008" -> "454681",
    "F2008" -> "478897",
    "S2009" -> "717578",
    "F2009" -> "785862",
    "S2010" -> "859988",
    "F2010" -> "912846",
    "S2011" -> "991552",
    "F2011" -> "1062206",
    "S2012" -> "1183866",
    "F2012" -> "1226768",
    "S2013" -> "1315139",
    "F2013" -> "1376480"
  )
}
