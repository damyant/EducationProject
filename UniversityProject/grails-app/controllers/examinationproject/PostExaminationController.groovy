package examinationproject

import grails.converters.JSON
import universityproject.PostExaminationService

import static examinationproject.ProgramDetail.*
import static examinationproject.ProgramSession.*

/**
 * Created by Digvijay on 3/6/14.
 */
class PostExaminationController {

    def createMarksFoil = {
        println("Inside PostExaminationController-->Params = "+params)
        def programList = ProgramDetail.list()
        println("Inside PostExaminationController-->programList = "+programList)
        [programList:programList]



//        try{
//            def program = ProgramDetail.findById(Integer.parseInt(params.programDetail))
//            def session = ProgramSession.findBySessionOfProgram(params.session)
//            println(program)
//            println(session)
//        }catch (Exception e){
//            println("problem, exception occurs in catch block--"+e.printStackTrace())
//        }
//        PostExaminationService.generateMarksFoil(params)
//        println("Inside PostExaminationController 2"+params)
//        redirect(action: "")
//        println("Inside PostExaminationController 3"+params)
    }

def getCourseData={
    println('Params === ' +params)
    def subjectList=CourseSubject.findAllBySemesterAndProgramSessionAndCourseDetail(Semester.findById(Integer.parseInt(params.semester)),ProgramSession.findBySessionOfProgram(params.session),ProgramDetail.findById(params.program))*.subject
    println("Semester--"+Semester.findById(Integer.parseInt(params.semester)))
    println("Program Session--"+ProgramSession.findBySessionOfProgram(params.session))
    println("Program Details--"+ProgramDetail.findById(params.program))

    println("Inside PostExamination Controller-22->subjectList=="+subjectList)
    render subjectList as JSON
}

}// CLOSING BRACKETS
