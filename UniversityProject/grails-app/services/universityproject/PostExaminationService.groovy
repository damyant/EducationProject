package universityproject

import examinationproject.CourseSubject
import examinationproject.ProgramDetail
import examinationproject.ProgramSession
import examinationproject.Semester
import examinationproject.Status
import examinationproject.Student
import examinationproject.StudyCenter
import examinationproject.Subject
import grails.transaction.Transactional
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.write.WritableWorkbook

@Transactional
class PostExaminationService {
    //def writeExcelService
    def marksFoilExcelService
    def serviceMethod() {

    }

    def generateMarksFoilService(params){
        println("Inside PostExaminationService 1")
        def programIns = ProgramDetail.findById(Integer.parseInt(params.program))
//      def programIns=ProgramDetail.findById(Long.parseLong(params.programList))
        def programSessionIns=ProgramSession.findById(Long.parseLong(params.sessionId))
        def semesterList=Semester.findAllByProgramSession(programSessionIns)
        def courseSubjectObj=CourseSubject.findBySubjectAndProgramSession(Subject.findById(Long.parseLong(it)),sessionObj)

    }

    def getMarksFoilData(params, excelPath) {
        println("inside service class...111.................")
        if(params.btn=="excel"){
            File file = new File(''+excelPath);
            WorkbookSettings  wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
            println("inside service class..222..................")

        def course = ProgramDetail.findById(params.programId).courseName
        def subject = ProgramDetail.findById(params.programId)
        def semester = Semester.findById(params.programTerm).semesterNo
        def session =ProgramSession.findBySessionOfProgram(params.session)
        def regYear =(ProgramSession.findBySessionOfProgram(params.session).sessionOfProgram).substring(0,4)

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

            println('Student List :: '+ stuList)
            int count = 0
            def status=  MarksFoilExcelService.excelReport(params,stuList,subject,count, workbook)
println("status--"+status)
            workbook.write();
            workbook.close();
            return status
        }
    }
}// MAIN CLOSING TAG