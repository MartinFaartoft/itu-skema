package dk.itu

case class GenericCourse(id: Int,
                         name: String,
                         ects: Double,
                         grad: String,
                         runsInSpring: Boolean,
                         runsInFall: Boolean,
                         requirements: String,
                         courseIds: List[Int],
                         lastRun: String,
                         preReqIds: List[Int],
                         tags: List[String])

// A courses at mit.ITU
case class Course(id: Int,
                  name: String,
                  ects: Double,
                  url: String,
                  blog: String,
                  requirements: String,
                  language: String,
                  education: String,
                  grad: String,
                  location: String,
                  days: String,
                  time: String,
                  semester: String)

// Placeholder for a list of courses, mainly used for serialization purposes - eg lets use save all the courses to a file
case class Courses(courses: List[Course])

/*
firstYearTest is a collection of courses that is part of the first year test on bachelor educations.
It is a tuple consisting of the course name, the semester it needs to be taken on, and the number of ects needed for that semester
*/
case class StudyPlan(name: String, grad: String, startsInFall: Boolean, courses: List[StudyPlanCourse], studyActivityDemandPerYear: Double, firstYearTest: List[(String, Int, Double)])

case class StudyPlanCourse(name: String, ects: Double, semester: Int, genericCourseIds: List[Int], runsInSpring: Boolean, runsInFall: Boolean)

object StudyPlan {
  val elective = "Elective Course or Project"
  val specialization1 = "Specialisation Part 1"
  val specialization2 = "Specialisation Part 2"
  val thesis = "Master Thesis"
  val bachelor = "Project Cluster: Bachelor's Project"

  // Implicit conversion that makes it easier to write a course
  implicit def liftToStudyPlanCourse(t: (String, Double, Int)): StudyPlanCourse = StudyPlanCourse(t._1, t._2, t._3, Nil, runsInSpring = false, runsInFall = false)

  object Bachelor {
    object SWU {
      val Fall2007 = StudyPlan(
        name = "SWU_E2007",
        grad = "Bachelor",
        startsInFall = true,
        courses = List(
          ("Systematic Design of User Interfaces", 7.5, 1),
          ("Project Work and Communication", 7.5, 1),
          ("Introductory Programming", 15.0, 1),
          ("Object Oriented Software Construction", 7.5, 2),
          ("Algorithms and Data Structures", 7.5, 2),
          ("Project Cluster: First Year Project", 15.0, 2),
          ("Distributed Systems and Protocols", 7.5, 3),
          ("Data Storage and Formats", 7.5, 3),
          ("Analysis, Design and Software Architecture", 15.0, 3),
          ("System Development and Project Organisation", 7.5, 4),
          (elective, 7.5, 4),
          ("Project Cluster: Second Year Project", 15.0, 4),
          ("Operating Systems and C", 7.5, 5),
          ("Programs as Data", 7.5, 5),
          ("Business Processes and Organisation", 15.0, 5),
          ("Reflections on IT", 7.5, 6),
          (elective, 7.5, 6),
          (bachelor, 15.0, 6)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = List(
          ("Systematic Design of User Interfaces", 1, 15.0),
          ("Project Work and Communication", 1, 15.0),
          ("Introductory Programming", 1, 15.0),
          ("Project Cluster: First Year Project", 2, 15.0)
        )
      )

      val Fall2011 = StudyPlan(
        name = "SWU_E2011",
        grad = "Bachelor",
        startsInFall = true,
        courses = List(
          ("Systematic Design of User Interfaces", 7.5, 1),
          ("Project Work and Communication", 7.5, 1),
          ("Introductory Programming", 15.0, 1),
          ("Object Oriented Software Construction", 7.5, 2),
          ("Algorithms and Data Structures", 7.5, 2),
          ("Project Cluster: First Year Project", 15.0, 2),
          ("Mobile And Distributed Systems", 7.5, 3),
          ("Introduction To Database Design", 7.5, 3),
          ("Analysis, Design and Software Architecture", 15.0, 3),
          ("System Development and Project Organisation", 7.5, 4),
          (elective, 7.5, 4),
          ("Project Cluster: Second Year Project", 15.0, 4),
          ("Operating Systems and C", 7.5, 5),
          ("Programs as Data", 7.5, 5),
          ("Business Processes and Organisation", 15.0, 5),
          ("Reflections on IT", 7.5, 6),
          (elective, 7.5, 6),
          (bachelor, 15.0, 6)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = List(
          ("Systematic Design of User Interfaces", 1, 15.0),
          ("Project Work and Communication", 1, 15.0),
          ("Introductory Programming", 1, 15.0),
          ("Project Cluster: First Year Project", 2, 15.0)
        )
      )

      val Fall2012 = StudyPlan(
        name = "SWU_E2012",
        grad = "Bachelor",
        startsInFall = true,
        courses = List(
          ("Systematic Design of User Interfaces", 7.5, 1),
          ("Project Work and Communication", 7.5, 1),
          ("Introductory Programming", 15.0, 1),
          ("Foundations Of Computing - Discrete Mathematics", 7.5, 2),
          ("Algorithms and Data Structures", 7.5, 2),
          ("Project Cluster: First Year Project", 15.0, 2),
          ("Mobile And Distributed Systems", 7.5, 3),
          ("Introduction To Database Design", 7.5, 3),
          ("Analysis, Design and Software Architecture", 15.0, 3),
          ("System Development and Project Organisation", 7.5, 4),
          (elective, 7.5, 4),
          ("Second Year Project - Functional Programming", 7.5, 4),
          ("Project Cluster: Second Year Project", 7.5, 4),
          ("Operating Systems and C", 7.5, 5),
          ("Programs as Data", 7.5, 5),
          ("Business Processes and Organisation", 15.0, 5),
          ("Reflections on IT", 7.5, 6),
          (elective, 7.5, 6),
          (bachelor, 15.0, 6)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = List(
          ("Systematic Design of User Interfaces", 1, 15.0),
          ("Project Work and Communication", 1, 15.0),
          ("Introductory Programming", 1, 15.0),
          ("Project Cluster: First Year Project", 2, 15.0)
        )
      )
    }

    object DMD {
      val Fall2010 = StudyPlan(
        name = "DMD_E2010",
        grad = "Bachelor",
        startsInFall = true,
        courses = List(
          ("Introduction To Media And Communication", 7.5, 1),
          ("Introduction To Interaction Design", 7.5, 1),
          ("Dissemination, Project Work And Web Design", 15.0, 1),
          ("Digital Media Culture", 7.5, 2),
          ("Sketching And Prototyping", 7.5, 2),
          ("Project Cluster: Crossmedia", 15.0, 2),
          ("Mobile Media And Social IT", 7.5, 3),
          ("Digital Aesthetics", 7.5, 3),
          ("Co-design - Understanding And Involving Users", 15.0, 3),
          ("Bachelor Elective", 7.5, 4),
          ("Bachelor Elective", 7.5, 4),
          ("Project Cluster: Concept Development With Industry", 15.0, 4),
          (elective, 7.5, 5),
          (elective, 7.5, 5),
          ("Research Project And Academic Writing", 15.0, 5),
          ("Innovation And Entrepreneurship", 7.5, 6),
          ("Philosophy Of Science", 7.5, 6),
          (bachelor, 15.0, 6)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = List(
          ("Introduction To Media And Communication", 1, 7.5),
          ("Introduction To Interaction Design", 1, 7.5),
          ("Project Cluster: Crossmedia", 2, 15.0)
        )
      )
    }

    object GBI {
      val Fall2012 = StudyPlan(
        name = "GBI_E2012",
        grad = "Bachelor",
        startsInFall = true,
        courses = List(
          ("IT Foundations",7.5,1),
          ("New Media & Communication",7.5,1),
          ("Society and Technology",15.0,1),
          ("Database Use and Design",7.5,2),
          ("Global Project Management",7.5,2),
          ("IT & Work Design",15.0,2),
          ("Business Foundations",7.5,3),
          ("Organisation & Process Theory",7.5,3),
          ("Enterprise Systems & Information Management",15.0,3),
          ("IT & Business Process Modelling",7.5,4),
          ("Philosophy of Science and Technology",7.5,4),
          ("IT-enabled Process Improvement",15.0,4),
          (elective,7.5,5),
          ("IT-enabled Supply Chain Management",7.5,5),
          ("IT, Globalisation & Culture",15.0,5),
          (elective,7.5,6),
          ("IT Governance & Quality Management",7.5,6),
          (bachelor,15.0,6)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = List(
          ("Society and Technology",1,15.0),
          ("IT & Work Design",2,15.0)
        )
      )

      val Fall2011 = StudyPlan(
        name = "GBI_E2011",
        grad = "Bachelor",
        startsInFall = true,
        courses = List(
          ("IT Foundations",7.5,1),
          ("New Media & Communication",7.5,1),
          ("Society and Technology",15.0,1),
          ("Database Design",7.5,2),
          ("Global Project Management",7.5,2),
          ("IT & Work Design",15.0,2),
          ("Business Foundations",7.5,3),
          ("Organisation & Process Theory",7.5,3),
          ("Enterprise Systems & Information Management",15.0,3),
          ("IT & Business Process Modelling",7.5,4),
          ("Philosophy of Science and Technology",7.5,4),
          ("IT-enabled Process Improvement",15.0,4),
          (elective,7.5,5),
          ("IT-enabled Supply Chain Management",7.5,5),
          ("IT, Globalisation & Culture",15.0,5),
          (elective,7.5,6),
          ("IT Governance & Quality Management",7.5,6),
          (bachelor,15.0,6)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = List(
          ("Society and Technology",1,15.0),
          ("IT & Work Design",2,15.0)
        )
      )

      val Fall2010 = StudyPlan(
        name = "GBI_E2010",
        grad = "Bachelor",
        startsInFall = true,
        courses = List(
          ("IT Foundations",7.5,1),
          ("New Media & Communication",7.5,1),
          ("Society and Technology",15.0,1),
          ("Database Design",7.5,2),
          ("IT Project Management",7.5,2),
          ("IT & Work Design",15.0,2),
          ("IT & Business Process Modelling",7.5,3),
          ("Organisation & Process Theory",7.5,3),
          ("Enterprise Systems & Information Management",15.0,3),
          ("Business Foundations",7.5,4),
          ("Philosophy of Science and Technology",7.5,4),
          ("IT-enabled Process Improvement",15.0,4),
          (elective,7.5,5),
          ("IT-enabled Supply Chain Management",7.5,5),
          ("IT, Globalisation & Culture",15.0,5),
          (elective,7.5,6),
          ("IT Governance & Quality Management",7.5,6),
          (bachelor,15.0,6)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = List(
          ("Society and Technology",1,15.0),
          ("IT & Work Design",2,15.0)
        )
      )
    }
  }

  object MSc {
    object DigitalDesignAndCommunication {
      val Fall2012 = StudyPlan(
        name = "DDK_E2012",
        grad = "M.Sc.",
        startsInFall = true,
        courses = List(
          ("Design for Digital Communication Platforms", 10.0, 1),
          ("Scientific Methods and IT Understanding", 5.0, 1),
          ("Interaction Design", 7.5, 1),
          ("Digital Media and Communication", 7.5, 1),
          ("Digital Innovation", 7.5, 2),
          ("Digital Rhetorics", 7.5, 2),
          (specialization1, 7.5, 2),
          (elective, 7.5, 2),
          ("Global IT", 7.5, 3),
          (specialization2, 15.0, 3),
          (elective, 7.5, 3),
          (thesis, 30.0, 4)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = Nil
      )

      val Fall2012PostDMD = StudyPlan(
        name = "DDK_E2012_POSTDMD",
        grad = "M.Sc.",
        startsInFall = true,
        courses = List(
          ("Design for Digital Communication Platforms", 10.0, 1),
          ("Scientific Methods and IT Understanding", 5.0, 1),
          ("Project Post-DMD", 7.5, 1),
          ("Elective Post-DMD", 7.5, 1),
          ("Digital Innovation", 7.5, 2),
          ("Digital Rhetorics", 7.5, 2),
          (specialization1, 7.5, 2),
          (elective, 7.5, 2),
          ("Global IT", 7.5, 3),
          (specialization2, 15.0, 3),
          (elective, 7.5, 3),
          (thesis, 30.0, 4)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = Nil
      )

      val Spring2012 = StudyPlan(
        name = "DDK_F2012",
        grad = "M.Sc.",
        startsInFall = true,
        courses = List(
          ("Interaction Design", 15.0, 1),
          ("Media and Communication", 7.5, 1),
          ("Web Design and Web Communication", 7.5, 1),
          ("Introduction to Scripting, Databases and System Architecture", 7.5, 2),
          ("Innovation, Concept Development and Project Management", 7.5, 2),
          (specialization1, 7.5, 2),
          (elective, 7.5, 2),
          ("Digital Rhetorics", 7.5, 3),
          (specialization2, 15.0, 3),
          (elective, 7.5, 3),
          (thesis, 30.0, 4)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = Nil
      )
    }

    object Games {
      val DesignFall2012 = StudyPlan(
        name = "GAMES_DESIGN_E2012",
        grad = "M.Sc.",
        startsInFall = true,
        courses = List(
          ("Foundations of Play and Games", 15.0, 1),
          ("Game Design", 15.0, 1),
          ("Game Development", 15.0, 2),
          ("Specialisation Part 1", 7.5, 2),
          (elective, 7.5, 2),
          ("Specialisation Part 2", 15.0, 3),
          ("Specialisation Part 3", 7.5, 3),
          (elective, 7.5, 3),
          (thesis, 30.0, 4)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = Nil
      )

      val AnalysisFall2012 = StudyPlan(
        name = "GAMES_ANALYSIS_E2012",
        grad = "M.Sc.",
        startsInFall = true,
        courses = List(
          ("Foundations of Play and Games", 15.0, 1),
          ("Game Design", 15.0, 1),
          ("Digital Game Theory", 15.0, 2),
          ("Specialisation Part 1", 7.5, 2),
          (elective, 7.5, 2),
          ("Specialisation Part 2", 15.0, 3),
          ("Specialisation Part 3", 7.5, 3),
          (elective, 7.5, 3),
          (thesis, 30.0, 4)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = Nil
      )

      val TechnologyFall2012 = StudyPlan(
        name = "GAMES_TECHNOLOGY_E2012",
        grad = "M.Sc.",
        startsInFall = true,
        courses = List(
          ("Game Engines", 15.0, 1),
          ("Game Design", 15.0, 1),
          ("Game Development", 15.0, 2),
          ("Specialisation Part 1", 7.5, 2),
          (elective, 7.5, 2),
          ("Specialisation Part 2", 15.0, 3),
          ("Specialisation Part 3", 7.5, 3),
          (elective, 7.5, 3),
          (thesis, 30.0, 4)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = Nil
      )
    }

    object Ebusiness {
      val Spring2013 = StudyPlan(
        name = "EBUSS",
        grad = "M.Sc.",
        startsInFall = true,
        courses = List(
          ("Elective (T1)", 7.5, 1),
          ("Elective (B1)", 7.5, 1),
          ("Elective (P1)", 7.5, 1),
          ("T-, B- or P-Project", 7.5, 1),
          (elective, 7.5, 2),
          (elective, 7.5, 2),
          (elective, 7.5, 2),
          ("T-, B- or P-Project", 7.5, 2),
          (elective, 7.5, 3),
          (elective, 7.5, 3),
          ("SF23 Perspectives On E-business", 7.5, 3),
          ("T-, B- or P-Project", 7.5, 3),
          (thesis, 30.0, 4)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = Nil
      )
    }

    object SDT {
      val Spring2013_DT = StudyPlan(
        name = "SDT_F2013_DT",
        grad = "M.Sc.",
        startsInFall = true,
        courses = List(
          ("Introductory Programming", 7.5, 1),
          ("Project Cluster: Programming Workshop", 7.5, 1),
          ("Introduction to Database Design", 7.5, 1),
          ("Foundations of Computing - Discrete Mathematics", 7.5, 1),
          (specialization1, 7.5, 2),
          (elective, 7.5, 2),
          ("Software Engineering and Software Qualities", 7.5, 2),
          ("Foundations of Computing - Algorithms and Data Structures", 7.5, 2),
          (specialization2, 15.0, 3),
          (elective, 7.5, 3),
          (elective, 7.5, 3),
          (thesis, 30.0, 4)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = Nil
      )

      val Spring2013_SE = StudyPlan(
        name = "SDT_F2013_SE",
        grad = "M.Sc.",
        startsInFall = true,
        courses = List(
          ("Model Driven Development", 7.5, 1),
          (elective, 7.5, 1),
          (elective, 7.5, 1),
          (elective, 7.5, 1),
          (specialization1, 7.5, 2),
          ("Algorithm Design I", 7.5, 2),
          ("Project Cluster: Global Software Development", 15.0, 2),
          (specialization2, 15.0, 3),
          ("Advanced Software Engineering or Cloud Computing or Enterprise Architecture", 7.5, 3),
          (elective, 7.5, 3),
          (thesis, 30.0, 4)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = Nil
      )

      val Fall2012_DT = StudyPlan(
        name = "SDT_E2012_DT",
        grad = "M.Sc.",
        startsInFall = true,
        courses = List(
          ("Introductory Programming", 7.5, 1),
          ("Project Cluster: Programming Workshop", 7.5, 1),
          ("Foundations of Computing - Discrete Mathematics", 7.5, 1),
          ("Software Engineering and Software Qualities", 7.5, 1),
          ("Introduction to Database Design", 7.5, 2),
          ("Foundations of Computing - Algorithms and Data Structures", 7.5, 2),
          (elective, 7.5, 2),
          (specialization1, 7.5, 2),
          (elective, 7.5, 3),
          (elective, 7.5, 3),
          (specialization2, 15.0, 3),
          (thesis, 30.0, 4)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = Nil
      )

      val Fall2012_SE = StudyPlan(
        name = "SDT_E2012_SE",
        grad = "M.Sc.",
        startsInFall = true,
        courses = List(
          ("Model Driven Development", 7.5, 1),
          ("Algorithm Design I", 7.5, 1),
          (elective, 7.5, 1),
          (elective, 7.5, 1),
          ("Project Cluster: Global Software Development", 15.0, 2),
          ("Advanced Software Engineering", 7.5, 2),
          (specialization1, 7.5, 2),
          (elective, 7.5, 3),
          (elective, 7.5, 3),
          (specialization2, 15.0, 3),
          (thesis, 30.0, 4)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = Nil
      )

      val Spring2012_DT = StudyPlan(
        name = "SDT_F2012_DT",
        grad = "M.Sc.",
        startsInFall = true,
        courses = List(
          ("Introductory Programming", 7.5, 1),
          ("Project Cluster: Programming Workshop", 7.5, 1),
          ("Introduction to Database Design", 7.5, 1),
          ("Foundations of Computing - Discrete Mathematics", 7.5, 1),
          (specialization1, 7.5, 2),
          (elective, 7.5, 2),
          ("Software Engineering and Software Qualities", 7.5, 2),
          ("Foundations of Computing - Algorithms and Data Structures", 7.5, 2),
          (specialization2, 15.0, 3),
          (elective, 7.5, 3),
          (elective, 7.5, 3),
          (thesis, 30.0, 4)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = Nil
      )

      val Spring2012_SE = StudyPlan(
        name = "SDT_F2012_SE",
        grad = "M.Sc.",
        startsInFall = true,
        courses = List(
          (elective, 7.5, 1),
          (elective, 7.5, 1),
          (elective, 7.5, 1),
          (specialization1, 7.5, 1),
          ("Model Driven Development", 7.5, 2),
          ("Distributed Collaboration and Development", 7.5, 2),
          (specialization2, 15.0, 2),
          ("Project Cluster: Global Software Development", 15.0, 3),
          ("Advanced Software Engineering", 7.5, 3),
          (elective, 7.5, 3),
          (thesis, 30.0, 4)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = Nil
      )

      val Fall2011_DT = Fall2012_DT

      val Fall2011_SE = StudyPlan(
        name = "SDT_E2011_SE",
        grad = "M.Sc.",
        startsInFall = true,
        courses = List(
          ("Model Driven Development", 7.5, 1),
          ("Distributed Collaboration and Development", 7.5, 1),
          (elective, 7.5, 1),
          (elective, 7.5, 1),
          ("Project Cluster: Global Software Development", 15.0, 2),
          ("Advanced Software Engineering", 7.5, 2),
          (specialization1, 7.5, 2),
          (elective, 7.5, 3),
          (elective, 7.5, 3),
          (specialization2, 15.0, 3),
          (thesis, 30.0, 4)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = Nil
      )

      val Spring2011_DT = StudyPlan(
        name = "SDT_F2011_DT",
        grad = "M.Sc.",
        startsInFall = true,
        courses = List(
          ("Introductory Programming", 7.5, 1),
          ("Project Cluster: Programming Workshop", 7.5, 1),
          ("Introduction to Database Design", 7.5, 1),
          (elective, 7.5, 1),
          ("Foundations of Computing - Discrete Mathematics", 7.5, 1),
          ("Foundations of Computing - Algorithms and Data Structures", 7.5, 2),
          ("Introduction to Software Testing", 7.5, 2),
          (specialization1, 7.5, 2),
          (elective, 7.5, 3),
          (elective, 7.5, 3),
          (specialization2, 15.0, 3),
          (thesis, 30.0, 4)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = Nil
      )

      val Spring2011_SE = Spring2012_SE

      val Fall2010_DT = StudyPlan(
        name = "SDT_E2010_DT",
        grad = "M.Sc.",
        startsInFall = true,
        courses = List(
          ("Introductory Programming", 7.5, 1),
          ("Project Cluster: Programming Workshop", 7.5, 1),
          ("Introduction to Database Design", 7.5, 1),
          ("Introduction to Software Testing", 7.5, 1),
          ("Foundations of Computing - Discrete Mathematics", 7.5, 2),
          ("Foundations of Computing - Algorithms and Data Structures", 7.5, 2),
          (elective, 7.5, 2),
          (specialization1, 7.5, 2),
          (elective, 7.5, 3),
          (elective, 7.5, 3),
          (specialization2, 15.0, 3),
          (thesis, 30.0, 4)
        ),
        studyActivityDemandPerYear = 7.5,
        firstYearTest = Nil
      )

      val Fall2010_SE = Fall2011_SE
    }
  }
}
