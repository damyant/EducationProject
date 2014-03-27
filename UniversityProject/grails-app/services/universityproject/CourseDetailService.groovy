package universityproject

import examinationproject.CourseMode
import examinationproject.CourseType
import examinationproject.ProgramDetail
import examinationproject.CourseSubject
import examinationproject.Semester
import examinationproject.Subject
import grails.transaction.Transactional

@Transactional
class CourseDetailService {

    def serviceMethod() {

    }

    def saveCourseInfo(params) {
        def status=false
        def semObj

        def existingCourseObj = ProgramDetail.findById(params.courseId)

        if (existingCourseObj) {
            existingCourseObj.courseName = params.courseName
            existingCourseObj.courseCode = Integer.parseInt(params.courseCode)
            existingCourseObj.courseMode = CourseMode.findById(params.courseMode)
            existingCourseObj.courseType = CourseType.findById(params.courseType)
            existingCourseObj.noOfTerms = Integer.parseInt(params.noOfTerms)
            existingCourseObj.noOfAcademicYears = Integer.parseInt(params.noOfAcademicYears)
            existingCourseObj.noOfPapers = Integer.parseInt(params.noOfPapers)
            existingCourseObj.totalMarks = Integer.parseInt(params.totalMarks)
            existingCourseObj.marksPerPaper = Integer.parseInt(params.marksPerPaper)
            existingCourseObj.totalCreditPoints = Integer.parseInt(params.totalCreditPoints)

            existingCourseObj.save(failOnError: true)
            def semesterList = Semester.findAllByCourseDetail(existingCourseObj)
            CourseSubject.removeAll(existingCourseObj)
            semesterList.each {
                it.delete(failOnError: true)
            }

            for (def i = 1; i <= Integer.parseInt(params.noOfTerms); i++) {
                semObj = new Semester()
                semObj.semesterNo = i
                semObj.courseDetail = existingCourseObj
                semObj.save(failOnError: true)

                params.semesterList.each {
                    i
                    it."semester${i}".each { obj ->
                        CourseSubject.create existingCourseObj, Subject.findById(Integer.parseInt(obj.toString())), semObj
                    }
                    status=true
                }
                return status
            }
        } else {

            def courseObj = new ProgramDetail(params)
            courseObj.save(failOnError: true)
           for (def i = 1; i <= Integer.parseInt(params.noOfTerms); i++) {
                semObj = new Semester()
                println("????")
                semObj.semesterNo = i
                semObj.courseDetail = courseObj
                semObj.save(failOnError: true)
                params.semesterList.each {
                    i
                    it."semester${i}".each { obj ->
                        CourseSubject.create courseObj, Subject.findById(Integer.parseInt(obj.toString())), semObj
                    }
                   status=true
                }

            }
            return status
        }


    }

    def getAllCourses() {

        def courseObj = ProgramDetail.findAll()

    }

    def getFullDetailOfCourse(params) {
        def courseDetail = [:], subMap = [:]
        def subList = []
        def courseObj = ProgramDetail.findById(params.courseId)
        courseDetail.course = courseObj
        courseObj.semester.each {
            subMap[it.semesterNo] = CourseSubject.findAllByCourseDetailAndSemester(courseObj, it).subject
            subList = subMap
        }
        courseDetail.semesterList = subList
        return courseDetail

    }

    def deleteCourse(params) {

        def existingCourseObj = ProgramDetail.findById(params.courseId)
        if (existingCourseObj) {
            def semesterList = Semester.findAllByCourseDetail(existingCourseObj)
            CourseSubject.removeAll(existingCourseObj)
            semesterList.each {
                it.delete(failOnError: true)
            }
            existingCourseObj.delete()
        }
    }


}
