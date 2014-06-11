package examinationproject

import grails.converters.JSON
import universityproject.PostExaminationService

import static examinationproject.ProgramDetail.*
import static examinationproject.ProgramSession.*

/**
 * Created by Digvijay on 3/6/14.
 */
class PostExaminationController {
    def pdfRenderingService
    def createMarksFoil = {
        println("Inside PostExaminationController-->Params = "+params)
        def programList = ProgramDetail.list()
        println("Inside PostExaminationController-->programList = "+programList)
        [programList:programList]
    }
    def markMismatchReport = {

    }
    def marksUpdation = {

    }


    def getCourseData={
        println('Params === ' +params)
//        def subjectList=CourseSubject.findAllBySemesterAndProgramSessionAndCourseDetail(Semester.findById(Integer.parseInt(params.semester)),ProgramSession.findBySessionOfProgram(params.session),ProgramDetail.findById(params.program))*.subject
          def programDetail= ProgramDetail.findById(params.program)
        def programSession=  ProgramSession.findBySessionOfProgramAndProgramDetailId(params.session, programDetail)
        def semester = Semester.findByProgramSessionAndSemesterNo(programSession, Integer.parseInt(params.semester))

        println('this is the program '+ programDetail.id)
           println(' this is the session '+ programSession.id)
        println(' this is the  semester '+ semester)
          def subjectList = CourseSubject.findAllByCourseDetailAndSemesterAndProgramSession(programDetail, semester, programSession)
//        println("Semester--"+Semester.findById(Integer.parseInt(params.semester)))
//        println("Program Session--"+ProgramSession.findBySessionOfProgram(params.session))
//        println("Program Details--"+ProgramDetail.findById(params.program))
        println('this is the list of subjects '+ subjectList)
        def resultMap = [:]
        println("Inside PostExamination Controller-22->subjectList=="+subjectList[0].subject.subjectName)
        println('these are all subjects '+ subjectList.subject)
        resultMap.subject = subjectList.subject
        resultMap.subjectList= subjectList
        render resultMap as JSON
}

    def generateMarksFoilSheet={
        println('PostExamination Controller-->generateMarksFoilSheet-->Params :: ' +params)
        def subject = ProgramDetail.findById(params.programId).courseName
        def semester = Semester.findById(Integer.parseInt(params.semester)).semesterNo

        def args = [template: "generateMarksFoil",model: [program:subject,semester:semester],filename:"MarksFoilSheet.pdf"]
        pdfRenderingService.render(args + [controller: this], response)
    }

}// CLOSING BRACKETS
