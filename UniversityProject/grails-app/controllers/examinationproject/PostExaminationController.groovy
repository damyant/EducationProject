package examinationproject

import grails.converters.JSON
import universityproject.PostExaminationService

import javax.activation.MimetypesFileTypeMap

import static examinationproject.ProgramDetail.*
import static examinationproject.ProgramSession.*

/**
 * Created by Digvijay on 3/6/14.
 */
class PostExaminationController {
    def pdfRenderingService
    def postExaminationService
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
        if(params.btn=="pdf"){
            println('PostExamination Controller-->generateMarksFoilSheet-->Params :: ' +params)

            def course = ProgramDetail.findById(params.programId).courseName
            def subject = ProgramDetail.findById(params.programId)
            def semester = Semester.findById(params.programTerm).semesterNo
            def session =ProgramSession.findBySessionOfProgram(params.session)
            def regYear =(ProgramSession.findBySessionOfProgram(params.session).sessionOfProgram).substring(0,4)

            println("Registration Year =="+subject.id)

            def studentObj = Student.createCriteria()
            def stuList = studentObj.list{
                programDetail{
                    eq('id', subject.id)
                }
                and
                {
                    eq('semester', Integer.parseInt(params.programTerm) )
                }
                and
                        {
                            eq('status', Status.findById(4))
                        }
                and{
                    eq('registrationYear',Integer.parseInt(regYear ))
                }
            }

            println('this is the list of students '+ stuList.referenceNumber)
            println("SEMESTER =::"+semester)

            def args = [template: "generateMarksFoil",model: [program:course,semester:semester,stuList:stuList.referenceNumber],filename:"MarksFoilSheet.pdf"]
            pdfRenderingService.render(args + [controller: this], response)
        }else{

            println('PostExamination Controller-->generateMarksFoilSheet-->Params :: ' +params)
            println('PostExamination Controller-->generateMarksFoilSheet-->Params value :: ' +params.value)
//            if(params.value=='session' && params.session){
//                if(params.inExcel){
                    def webRootDir = servletContext.getRealPath("/")
                    println("webRootDir-"+webRootDir)
                    def userDir = new File(webRootDir,'/Report')
                    println("userDir--"+userDir)
                    userDir.mkdirs()
                    def excelPath = servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'MarksFoilSheet.xls'
                    println('excelPath-- '+excelPath)
                    def status = postExaminationService.getMarksFoilData(params, excelPath)

                    if(status){
                        println("back in controller "+ status)
                        File myFile = new File(servletContext.getRealPath("/")+'Report'+System.getProperty('file.separator')+'MarksFoilSheet.xls')
                        //response.setHeader "Content-disposition", "attachment; filename="+'Student_List_'+params.session+".xls"
                        response.setHeader "Content-disposition", "attachment; filename="+'MarksFoilSheet'+".xls"
                        response.contentType = new MimetypesFileTypeMap().getContentType(myFile )
                        response.outputStream << myFile .bytes
                        response.outputStream.flush()
                        myFile.delete()
                    }
                }
//            }
//        }
    }


    def marksEntering={
        println("Inside marksEntering Action.....")
    }


    def resultProcessing={
        println("Inside resultProcessing Action..")
    }

    def finalResult={
        println("Inside finalResult Action")
    }

}// CLOSING BRACKETS
