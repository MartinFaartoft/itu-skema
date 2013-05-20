package dk.itu.stackmob

import com.stackmob.sdk.callback.StackMobCallback
import com.stackmob.sdk.exception.StackMobException
import com.stackmob.sdk.api.{StackMobOptions, StackMob}
import dk.itu._
import dk.itu.Course
import dk.itu.GenericCourse


object StackMobHelper {
  // Connect to StackMob - remember to check if the data base is open
  val api_key = "removed"
  val api_version = 0
  val sm = new StackMob(api_version,api_key)

  def uploadAllDataToStackMob() {
//    println("Uploading " + DataFetcher.rawCourses.length + " SMCourses")
//    for (course <- DataFetcher.rawCourses) {
//      saveCourseToStackMob(course)
//      Thread.sleep(25)
//    }
//
//    // Before getting the generic courses - reset the id generator to start over from 0
//    DataFetcher.resetIdGenerator()
//
//    println("Uploading " + DataFetcher.genericCourses.length + " SMGenericCourses")
//    for (genCourse <- DataFetcher.genericCourses) {
//      saveGenericCourseToStackMob(genCourse)
//      Thread.sleep(25)
//    }

    // Add more studyplans below to be uploaded
    saveStudyPlanToStackMob(StudyPlan.Bachelor.SWU.Fall2007)
    saveStudyPlanToStackMob(StudyPlan.Bachelor.SWU.Fall2011)
    saveStudyPlanToStackMob(StudyPlan.Bachelor.SWU.Fall2012)
    saveStudyPlanToStackMob(StudyPlan.Bachelor.GBI.Fall2010)
    saveStudyPlanToStackMob(StudyPlan.Bachelor.GBI.Fall2011)
    saveStudyPlanToStackMob(StudyPlan.Bachelor.GBI.Fall2012)
    saveStudyPlanToStackMob(StudyPlan.Bachelor.DMD.Fall2010)

  }

  private def saveStudyPlanToStackMob(sp: StudyPlan) {
    val smsp_courses: Array[SMStudyPlanCourse] = (for {
      c <- StudyGuide.coursesForStudyPlan(sp)
    } yield new SMStudyPlanCourse(c.name,c.ects,c.semester,c.genericCourseIds.toArray, c.runsInSpring, c.runsInFall)).toArray

    for (smspc <- smsp_courses) {
      smspc.save(new StackMobCallback() {
        @Override def success(responseBody: String) {

        }
        @Override def failure(e: StackMobException) {
          println("Error in upload " + e)
        }
      })
    }

    // Wait for the upload to finish
    Thread.sleep(10000)


    val firstYearTest: Array[SMFirstYearTestTuple] = (for {
      (courseName, semester, ectsRequired) <- sp.firstYearTest
    } yield new SMFirstYearTestTuple(courseName,semester,ectsRequired)).toArray

    for (test <- firstYearTest) {
      test.save(new StackMobCallback() {
        @Override def success(responseBody: String) {
        }
        @Override def failure(e: StackMobException) {
          println("Error in upload " + e)
        }
      })
    }

    // Wait for the upload to finish
    Thread.sleep(5000)

    val smsp = new SMStudyPlan(sp.name, sp.grad, sp.startsInFall, smsp_courses,sp.studyActivityDemandPerYear, firstYearTest)
    smsp.setID(sp.name)
    smsp.save(new StackMobCallback() {
      @Override def success(responseBody: String) {
        //        println("Succeeded in uploading\n" + responseBody)
      }
      @Override def failure(e: StackMobException) {
        println("Error in upload " + e)
      }
    })
  }

  private def saveGenericCourseToStackMob(c: GenericCourse) {
    val jc = new SMGenericCourse(c.name,c.ects,c.grad,c.runsInSpring,c.runsInFall,c.requirements,c.courseIds.toArray,c.lastRun,c.preReqIds.toArray,c.tags.toArray)
    jc.setID(c.id.toString)
    jc.save(new StackMobCallback() {
      @Override def success(responseBody: String) {
        //        println("Succeeded in uploading\n" + responseBody)
      }
      @Override def failure(e: StackMobException) {
        println("Error in upload " + e)
      }
    })
  }

  private def saveCourseToStackMob(c: Course) {
    val jc = new SMCourse(c.name,c.ects,c.url,c.blog,c.requirements,c.language,c.education,c.grad,c.location,c.days,c.time,c.semester)
    jc.setID(c.id.toString)
    jc.save(new StackMobCallback() {
      @Override def success(responseBody: String) {
        //        println("Succeeded in uploading\n" + responseBody)
      }
      @Override def failure(e: StackMobException) {
        println("Error in upload " + e)
      }
    })
  }
}
