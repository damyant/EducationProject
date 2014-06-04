package examinationproject

import universityproject.PostExaminationService

import static examinationproject.ProgramDetail.*
import static examinationproject.ProgramSession.*

/**
 * Created by Digvijay on 3/6/14.
 */
class PostExaminationController {

    def createMarksFoil = {
        println("Inside PostExaminationController 1"+params)
        def programList = ProgramDetail.list()
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


}// CLOSING BRACKETS
