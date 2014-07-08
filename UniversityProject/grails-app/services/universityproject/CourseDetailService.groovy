package universityproject

import examinationproject.CourseMode
import examinationproject.CourseType
import examinationproject.ProgramDetail
import examinationproject.CourseSubject

import examinationproject.ProgramType

import examinationproject.ProgramSession

import examinationproject.Semester
import examinationproject.Subject
import examinationproject.SubjectSession
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import postexamination.MarksType
import postexamination.SubjectMarksDetail

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
                if (ProgramSession.findByProgramDetailIdAndSessionOfProgram(existingCourseObj, params.session)) {
                    sessionObj = ProgramSession.findByProgramDetailIdAndSessionOfProgram(existingCourseObj, params.session)
                } else {
                    sessionObj = new ProgramSession(sessionOfProgram: params.session, programDetailId: existingCourseObj).save(flush: true, failOnError: true)
                }
            } else {

                sessionObj = new ProgramSession(sessionOfProgram: params.session, programDetailId: existingCourseObj).save(flush: true, failOnError: true)
            }
            def semesterList = Semester.findAllByProgramSession(sessionObj)

            if (sessionObj) {

            }
            CourseSubject.removeAll(existingCourseObj, sessionObj)
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
                        CourseSubject.create existingCourseObj, Subject.findById(Integer.parseInt(obj.toString())), semObj, sessionObj
                    }
                    status = 'updated'
                }

            }
            return status
        } else {

            def courseObj = new ProgramDetail(params)
            courseObj.save(failOnError: true, flush: true)
            if (session > 0) {

                if (ProgramSession.findBySessionOfProgramAndProgramDetailId(params.session, courseObj)) {
                    sessionObj = ProgramSession.findBySessionOfProgramAndProgramDetailId(params.session, courseObj)

                } else {
                    sessionObj = new ProgramSession(sessionOfProgram: params.session, programDetailId: courseObj).save(flush: true, failOnError: true)
                }
            } else {
                sessionObj = new ProgramSession(sessionOfProgram: params.session, programDetailId: courseObj).save(flush: true, failOnError: true)
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
                        CourseSubject.create courseObj, Subject.findById(Integer.parseInt(obj.toString())), semObj, sessionObj
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

        def programSession = ProgramSession.findById(Long.parseLong(params.courseSessionId))

        courseDetail.course = programSession.programDetailId
        courseDetail.courseType = programSession.programDetailId.courseType.courseTypeName
        courseDetail.ProgramType = programSession.programDetailId.programType.type
        courseDetail.courseMode = programSession.programDetailId.courseMode.modeName
        courseDetail.sessionOfCourse = programSession.sessionOfProgram

        programSession.semester.each {
            subMap[it.semesterNo] = CourseSubject.findAllByCourseDetailAndSemesterAndProgramSession(programSession.programDetailId, it, programSession).subject
            subList = subMap
        }
        courseDetail.semesterList = subList

        return courseDetail

    }

    def deleteCourse(params) {
        def status = false

        def existingProgramSession = ProgramSession.findById(Long.parseLong(params.courseSessionId))
        if (existingProgramSession) {
            def semesterList = Semester.findAllByProgramSession(existingProgramSession)
            CourseSubject.removeAll(existingProgramSession.programDetailId, existingProgramSession)
            semesterList.each {
                it.delete(failOnError: true, flush: true)
            }
            existingProgramSession.delete(flush: true)
            def courseSubject = ProgramSession.findByProgramDetailId(existingProgramSession.programDetailId)

            if (courseSubject == null) {
                if (ProgramDetail.findById(existingProgramSession.programDetailId.id).delete(failOnError: true, flush: true)) {
                    status = true
                }
            }
        }
        return status

    }

    def saveCourseDetail(params) {
        def subjectIns

        try {
            if (params.subjectId) {
                subjectIns = Subject.get(params.subjectId)
                subjectIns.subjectCode = params.subjectCode
                subjectIns.subjectName = params.subjectName
                subjectIns.aliasCode = params.aliasCode
                subjectIns.creditPoints = Integer.parseInt(params.creditPoints)
                subjectIns.save(flush: true)
            } else {
                subjectIns = new Subject(params)
                subjectIns.save(failOnError: true, flush: true)
            }
            def session = SubjectSession.count()
            def sessionObj
            if (session > 0) {
                if (SubjectSession.findBySubjectIdAndSessionOfSubject(subjectIns, params.session)) {
                    sessionObj = SubjectSession.findBySubjectIdAndSessionOfSubject(subjectIns, params.session)
                } else {
                    sessionObj = new SubjectSession(sessionOfSubject: params.session, subjectId: subjectIns).save(flush: true, failOnError: true)
                }
            } else {

                sessionObj = new SubjectSession(sessionOfSubject: params.session, subjectId: subjectIns).save(flush: true, failOnError: true)
            }

                  sessionObj.subjectMarksDetail.toList().each {
                      sessionObj.removeFromSubjectMarksDetail(it)
                    it.delete()
                }

            def marksTypeList = MarksType.list()
            def i = 0
            marksTypeList.each {
                if (params.totalMarks[i]) {
                    def subjectMarksDetailIns = new SubjectMarksDetail()
                    subjectMarksDetailIns.marks = Integer.parseInt(params.totalMarks[i].toString())
                    subjectMarksDetailIns.minPassingMarks = Integer.parseInt(params.minPassingMarks[i].toString())
                    subjectMarksDetailIns.marksTypeId = it
                    subjectMarksDetailIns.subjectSession = sessionObj
                    subjectMarksDetailIns.save(failOnError: true)
                }
                ++i
            }

        }

        catch (Exception e) {
            println(" There is some problem in saving Course=" + e)

        }

    }

    def getCourseOnProgramCode(params){
        def finalSubjectList=[],resultList=[]
        def subList=Subject.createCriteria()
         finalSubjectList= subList.list {
            like("subjectCode",params.courseCode+"%")
           and {
               eq('programTypeId',ProgramType.get(params.programType))
           }
         }

        finalSubjectList.each{
            resultList<< SubjectSession.findBySubjectId(it).subjectId
        }

        return resultList


    }
}
