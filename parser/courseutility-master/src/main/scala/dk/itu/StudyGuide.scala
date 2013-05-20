package dk.itu

import java.io.{FileOutputStream, FileInputStream, File}
import util.Marshal

object StudyGuide {
  private def readStudyPlanCoursesFromDisk(filePath: String): List[(StudyPlanCourse, List[GenericCourse])] = {
    val in = new FileInputStream(filePath)
    val bytes = Stream.continually(in.read).takeWhile(-1 !=).map(_.toByte).toArray
    Marshal.load[List[(StudyPlanCourse, List[GenericCourse])]](bytes)
  }

  private def writeStudyPlanCoursesToDisk(coursesToWrite: List[(StudyPlanCourse, List[GenericCourse])], filePath: String) {
    val out = new FileOutputStream(filePath)
    out.write(Marshal.dump(coursesToWrite))
    out.close()
  }

  // Get all matching generic courses for a study plan with its graduation (eg Bachelor, M.Sc...)
  def coursesForStudyPlan(sp: StudyPlan): List[StudyPlanCourse] = {
    // Find all generic courses matching the names from the study plan
    val coursesForStudyPlan: List[StudyPlanCourse] = for {
      spc: StudyPlanCourse <- sp.courses
      // Only get courses that match the same graduation as the study plan
      genericCourses = DataFetcher.genericCourses.filter(c => c.grad == sp.grad && c.name.toLowerCase.contains(spc.name.toLowerCase))
      genericCourseIds: List[Int] = genericCourses.map(gc => gc.id)
      runsInSpring: Boolean = genericCourses.map(gc => gc.runsInSpring).foldLeft(true)(_ & _)
      runsInFall: Boolean = genericCourses.map(gc => gc.runsInFall).foldLeft(true)(_ & _)
    } yield StudyPlanCourse(spc.name,spc.ects,spc.semester,genericCourseIds, runsInSpring, runsInFall)

    coursesForStudyPlan
  }

  // Check that the required courses are on the right semesters - so that the first year project requirements are fulfilled
  def validateFirstYearTest(sp: StudyPlan, courseOrder: List[(StudyPlanCourse, List[GenericCourse])]): (Boolean,List[Option[String]]) = {
    val pointsList: List[(Int, Double, Double)] = for {
      (courseName, semesterReq, ectsReq) <- sp.firstYearTest
      course = courseOrder.filter(x => x._1.name == courseName && x._1.semester == semesterReq)
    } yield (semesterReq, if (!course.isEmpty) course.head._1.ects else 0.0, ectsReq)

    // Map each semester to the points taken vs the points required
    val takenVsNeeded: List[(Int, (Double, Double))] = (pointsList groupBy {_._1} mapValues {v => ((v map {_._2}).sum, v.head._3)}).toList

    // Check if each semester fulfills the requirements, if not return false
    val errMessages: List[Option[String]] = for {
      (semesterId, (pointsTaken, pointsReq)) <- takenVsNeeded
      validSemester = pointsTaken >= pointsReq
    } yield if (validSemester) None else Some("Requirements for the "+semesterId+". semester : \n" + (for {course <- sp.firstYearTest.filter(x=>x._2==semesterId)} yield course._1).mkString("\n"))

    // Return a boolean indicating whether the test is fulfilled or not along with optional error messages
    (!errMessages.exists(s => s.isDefined),errMessages)
  }
}
