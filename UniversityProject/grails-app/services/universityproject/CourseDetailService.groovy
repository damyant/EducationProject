package universityproject

import examinationproject.CourseMode
import examinationproject.CourseType
import examinationproject.ProgramDetail
import examinationproject.CourseSubject
import examinationproject.ProgramSession
import examinationproject.Semester
import examinationproject.Subject
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import java.io.File;
@Transactional
class CourseDetailService {

    def serviceMethod() {

    }

    def saveCourseInfo(params) {
        def status = ""
        def semObj
        def existingCourseObj
//        println(params.courseId)
        if (params.courseId) {
            existingCourseObj = ProgramDetail.findById(Integer.parseInt(params.courseId))
        }

        def session = ProgramSession.count()
        def sessionObj

        if (existingCourseObj) {
//            println("innnn" + params)
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

            existingCourseObj.save(failOnError: true, flush: true)
            if (session > 0) {
                if (ProgramSession.findByProgramDetailIdAndSessionOfProgram(existingCourseObj,params.session)) {
                    sessionObj = ProgramSession.findByProgramDetailIdAndSessionOfProgram(existingCourseObj,params.session)
                } else {
                    sessionObj = new ProgramSession(sessionOfProgram: params.session, programDetailId:existingCourseObj).save(flush: true, failOnError: true)
                }
            } else {

                sessionObj = new ProgramSession(sessionOfProgram: params.session, programDetailId:existingCourseObj).save(flush: true, failOnError: true)
            }
            def semesterList = Semester.findAllByProgramSession(sessionObj)

            if(sessionObj){

            }
            CourseSubject.removeAll(existingCourseObj,sessionObj)
            semesterList.each {
                it.delete(failOnError: true)
            }

            for (def i = 1; i <= Integer.parseInt(params.noOfTerms); i++) {
                semObj = new Semester()
                semObj.semesterNo = i
                semObj.programSession = sessionObj
                semObj.save(failOnError: true)

                params.semesterList.each {
                    i
                    def subjectList = it."semester${i}".sort()
                    subjectList.each { obj ->
                        CourseSubject.create existingCourseObj, Subject.findById(Integer.parseInt(obj.toString())), semObj,sessionObj
                    }
                    status = 'updated'
                }

            }
            return status
        } else {

            def courseObj = new ProgramDetail(params)
            courseObj.save(failOnError: true, flush: true)
            if (session > 0) {
                if (ProgramSession.findBySessionOfProgram(params.session)) {
                    sessionObj = ProgramSession.findBySessionOfProgram(params.session)
                } else {
                    sessionObj = new ProgramSession(sessionOfProgram: params.session, programDetailId:courseObj).save(flush: true, failOnError: true)
                }
            } else {
                println("---------------------------isme aa gya")
                sessionObj = new ProgramSession(sessionOfProgram: params.session, programDetailId:courseObj).save(flush: true, failOnError: true)
            }
            for (def i = 1; i <= Integer.parseInt(params.noOfTerms); i++) {
                semObj = new Semester()

                semObj.semesterNo = i
                semObj.programSession = sessionObj
                semObj.save(failOnError: true)
                params.semesterList.each {
                    i
                    def subjectList = it."semester${i}".sort()
                    subjectList.each { obj ->
                        CourseSubject.create courseObj, Subject.findById(Integer.parseInt(obj.toString())), semObj,sessionObj
                    }
                    status = 'Created'
                }

            }
            return status
        }


    }

    def getAllCourses() {

        def courseObj = ProgramDetail.list(sort: 'courseName')


    }

    def getFullDetailOfCourse(params) {
        def courseDetail = [:], subMap = [:]
        def subList = []

        def programSession= ProgramSession.findById(Long.parseLong(params.courseSessionId))
//        println("***********"+courseObj)
        courseDetail.course = programSession.programDetailId
        courseDetail.courseType = programSession.programDetailId.courseType.courseTypeName
        courseDetail.ProgramType = programSession.programDetailId.programType.type
        courseDetail.courseMode = programSession.programDetailId.courseMode.modeName
        courseDetail.sessionOfCourse=programSession.sessionOfProgram

        programSession.semester.each {
            subMap[it.semesterNo] = CourseSubject.findAllByCourseDetailAndSemesterAndProgramSession(programSession.programDetailId, it, programSession).subject
            subList = subMap
        }
        courseDetail.semesterList = subList
        return courseDetail

    }

    def deleteCourse(params) {

//        def existingCourseObj = ProgramDetail.findById(params.courseId)
        def existingProgramSession= ProgramSession.findById(Long.parseLong(params.courseSessionId))
        println("*****="+existingProgramSession)
        if (existingProgramSession) {
            def semesterList = Semester.findAllByProgramSession(existingProgramSession)
            CourseSubject.removeAll(existingProgramSession.programDetailId,existingProgramSession)
            println("??????????"+semesterList)
            semesterList.each {
                it.delete(failOnError: true,flush: true)
            }
            existingProgramSession.delete(flush: true)
            def courseSubject = ProgramSession.findByProgramDetailId(existingProgramSession.programDetailId)

            if(courseSubject==null){
                ProgramDetail.findById(existingProgramSession.programDetailId.id).delete(failOnError: true,flush: true)
            }
        }
    }


}
