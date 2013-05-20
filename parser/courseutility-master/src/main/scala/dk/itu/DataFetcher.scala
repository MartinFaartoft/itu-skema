package dk.itu

import SemesterData._
import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
import xml.factory.XMLLoader
import xml.{XML, Elem}
import java.io.{FileInputStream, FileOutputStream, File}
import util.Marshal

object DataFetcher {
  // Deletes all currents caches and rebuilds the data. Use with caution - it takes 10-15 minutes on an intel i7 with 16gb of ram.
  def rebuildAllData() {
    val directoryName = "courseData"
    val directory = new File(directoryName)
    if (!directory.exists())
      directory.mkdir()
    for {
      files <- Option(directory.listFiles)
      file <- files if file.getName.endsWith(".txt")
    } file.delete()

    /*
     Get courses for each study plan to rebuild all files.
     Remember to add new semesters in SemesterData.scala if a new semester is available.
      */
  }

  // Disk utilities below
  private val fp = "courseData/courses.txt"

  // Get the course data. If it exists use that file, if not fetch all course data first then return it.
  def rawCourses: List[Course] = {
    val filePath = fp
    val file = new File(filePath)
    if (file.exists())
      readCoursesFromDisk(filePath).courses
    else {
      fetchCourseData()
      rawCourses
    }
  }

  private def fetchCourseData() {
    val filePath = fp
    val courses = Courses(getAllCourses)
    writeCoursesToDisk(courses, filePath)
  }

  private def writeCoursesToDisk(coursesToWrite: Courses, filePath: String) {
    val out = new FileOutputStream(filePath)
    out.write(Marshal.dump(coursesToWrite))
    out.close()
  }

  private def readCoursesFromDisk(filePath: String): Courses = {
    val in = new FileInputStream(filePath)
    val bytes = Stream.continually(in.read).takeWhile(-1 !=).map(_.toByte).toArray
    Marshal.load[Courses](bytes)
  }

  // Generic Course wrapper for all the courses. TODO Better description of generic courses
  lazy val genericCourses: List[GenericCourse] = {
    val allCourses: List[Course] = for {
      course: Course <- rawCourses
    // Building new courses from the fetched courses because not all courses that are equal are written equally. Here we are changing the name by title casing it - eg 'B8 tech Business' will become 'B8 Tech Business'
    } yield Course(course.id, course.name.split(" ").map(s => s.capitalize).mkString(" "), course.ects, course.url, course.blog, course.requirements, course.language, course.education, course.grad, course.location, course.days, course.time, course.semester)

    // Get all the courses names sorted without duplicates
    val courseNames: List[(String, String)] = (for {
      course: Course <- allCourses
    } yield (course.name,course.grad)).distinct.sorted

    // Get all the courses and group multiple runs of the courses
    val courses: List[Course] = for {
      (courseName,grad) <- courseNames
      course: Course <- allCourses.filter(c => c.name == courseName && c.grad == grad)
    } yield course

    // Create the generic courses
    val tempGenericCourses: List[GenericCourse] = for {
      (courseName,grad) <- courseNames
      course = courses.filter(c => c.name == courseName && c.grad == grad).last
      matchingCourses = courses.filter(c => c.name == courseName && c.grad == grad)
    } yield new GenericCourse(
        idGenerator.nextId,
        courseName,
        // Get the amount of ects from the first courses matching the name
        course.ects,
        // Which type of education is it
        course.grad,
        // Check if the courses runs in the spring semester
        matchingCourses.exists(c => c.semester.startsWith("S")),
        // Check if the courses runs in the fall semester
        matchingCourses.exists(c => c.semester.startsWith("F")),
        // Formal requirements for the course
        course.requirements,
        // Id's of the runs of this courses
        (for {c <- matchingCourses} yield c.id),
        // Last semester this courses was offered
        matchingCourses.last.semester,
        // List of prerequisite course id's
        List(),
        // Tags for the courses - maybe needed for future use
        List()
      )
    tempGenericCourses
  }

  // Runs through all semester id's in semester_ids and returns a List[Course]
  private def getAllCourses: List[Course] = {
    val all_semester_ids = for {
      (semester,semester_id) <- semester_ids.toList.sorted
    } yield semester_id
    def courseList(s_ids: List[String]): List[Course] = s_ids match {
      case Nil => Nil
      case id :: more_ids => DataFetcher.courseListForSemester(id) ++ courseList(more_ids)
    }
    courseList(all_semester_ids)
  }

  // Load the url content into a string using ISO-8859-1 encoding to avoid messing up æ's etc.
  private def contentFromUrl(url: String) = scala.io.Source.fromURL(url)("ISO-8859-1").mkString

  // Get all the courses for the provided semester id
  def courseListForSemester(semester_id: String): List[Course] = {
    // url with all courses in english
    val url = "https://mit.itu.dk/ucs/cb_www/index.sml?mode=search&lang=en&semester_id=" + semester_id

    // XML from site with regions of interest from page selected
    val ns = TagSoupXmlLoader.get().loadString(contentFromUrl(url)) \\ "p" \\ "table" \\ "td"

    // Using the courses's direct url fetch some extra data
    def getCourseData(url: String): Seq[String] = {
      // Get the xml starting from the right place in the html - think jquery \\ 'form' \\ 'table' \\ 'tr'
      val xml = TagSoupXmlLoader.get().loadString(contentFromUrl(url)) \\ "form" \ "table" \ "tr"
      val courseData = xml.map(_ match {
        case a if (a \\ "b").text == "Omfang i ECTS:" =>
          // Get the text from the ECTS field without the last char \u00a0 which is just a space.
          ((a \ "td" drop 1).text init)
        case a if (a \\ "b").text == "Kursushjemmeside:" =>
          // Get the text from the blog url field without the last char \u00a0 which is just a space
          ((a \ "td" drop 1).text init)
        case a if (a \\ "b").text == "Formelle forudsætninger:" =>
          // Get the text from the formal requirements field without the last char \u00a0 which is just a space
          ((a \ "td" drop 1).text init)
        // If it is not a ECTS, blog url field or a formal requirement string just return an empty string
        case _ => ""
        // Filter out all the empty strings, so we only get the ECTS and the blog url
      }).filterNot(_ == "")
      courseData
    }
    // Parse the XML to sequences of tuples we will deconstruct below
    val tempCourses: Seq[(String, String, String, String, String)] = ns.map(_ match {
      case a if (a \ "@id").text == "grad" => (a.text.trim, "", "", "", "")
      case a if (a \ "@id").text == "study" => (a.text.trim, "", "", "", "")
      case a if (a \ "@id").text == "course" =>
        val url = "https://mit.itu.dk" + (a \ "a" \ "@href").text.trim
        val courseData = getCourseData(url)
        // If courseData has data return the first item, which is the number of ECTS for the courses
        val ects = if (courseData.length > 0) courseData.head else ""
        // If courseData has more than one item, return the second item which is the link for the courses blog
        val blog = if (courseData.length > 1) courseData(1) else ""
        // If courseData has more than two items, return the third item which is the formal requirements
        val requirements = if (courseData.length > 2) courseData(2) else ""
        // Remove ?'s from the courses name
        val courseName = a.text.replace("*","").trim
        (courseName, ects, url, blog, requirements)
      case a if (a \ "@id").text == "days" => (a.text.trim, "", "", "", "")
      case a if (a \ "@id").text == "time" => (a.text.trim, "", "", "", "")
      case a if (a \ "@id").text == "loca" => (a.text.trim, "", "", "", "")
      case a if (a \ "@id").text == "lang" => (a.text.trim, "", "", "", "")
      case _ => ("", "", "", "", "")
    })

    // Utility function used to convert ects string to a double
    def convertToDouble(s: String) = if (s.isEmpty) 0.0 else s.replace(",", ".").toDouble

    /*
    Convert the temporary courses into our own class Course.
    The order of the pattern match is important, it needs to be the same order as the XML.
    The pattern match above in tempCourses reflects this order.
      */
    def createCourses(seq: List[(String, String, String, String, String)]): List[Course] = seq match {
      case (grad, _, _, _, _) :: (study, _, _, _, _) :: (courseName, ects, url, blog, requirements) :: (days, _, _, _, _) :: (time, _, _, _, _) :: (loca, _, _, _, _) :: (lang, _, _, _, _) :: xs =>
        // Only create the courses if the name is not empty
        if (!courseName.isEmpty)
        // Build the courses from the data we have. First generate a unique id. Then add the data extracted from the web page. Finally reverse semester_ids map so that we can add a readable semester name from a semester id.
          Course(idGenerator.nextId, courseName, convertToDouble(ects), url, blog, requirements, lang, study, grad, loca, days, time, semester_ids map {_.swap} apply semester_id) :: createCourses(xs)
        else createCourses(xs)
      case _ => Nil
    }

    // Rollback the id since we are dropping the head of the table which does not contain a courses
    idGenerator.prevId

    //Drop the first element because it is just the header of the courses table
    createCourses(tempCourses.toList).tail
  }

  def resetIdGenerator() {
    idGenerator.id = 0
  }
}

// Using TagSoup for XML parsing
private object TagSoupXmlLoader {
  private val factory = new SAXFactoryImpl()

  def get(): XMLLoader[Elem] = {
    XML.withSAXParser(factory.newSAXParser())
  }
}

// Can generate an id starting at 1 and counting up
private object idGenerator {
  var id: Int = _

  def nextId = {
    id += 1
    id
  }

  def prevId = {
    id -= 1
    id
  }
}