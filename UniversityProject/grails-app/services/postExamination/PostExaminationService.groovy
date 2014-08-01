package postExamination

import com.university.Role
import examinationproject.CourseSubject
import examinationproject.ProgramDetail
import examinationproject.ProgramSession
import examinationproject.Semester
import examinationproject.Status
import examinationproject.Student
import examinationproject.Subject
import grails.transaction.Transactional
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.write.WritableWorkbook
import postexamination.StudentMarks

@Transactional
class PostExaminationService {
    //def writeExcelService
    def marksFoilExcelService

    def serviceMethod() {

    }

    def generateMarksFoilService(params) {
        println("Inside PostExaminationService 1")
        def programIns = ProgramDetail.findById(Integer.parseInt(params.program))
//      def programIns=ProgramDetail.findById(Long.parseLong(params.programList))
        def programSessionIns = ProgramSession.findById(Long.parseLong(params.sessionId))
        def semesterList = Semester.findAllByProgramSession(programSessionIns)
        def courseSubjectObj = CourseSubject.findBySubjectAndProgramSession(Subject.findById(Long.parseLong(it)), sessionObj)

    }

    def dataForMarksFoilSheetPdf(params, semester) {
        def stuList = []

        try {
            def regYear = (ProgramSession.findById(params.programSessionId).sessionOfProgram).substring(0, 4)

            def studentObj = Student.createCriteria()
            stuList = studentObj.list {
                programDetail {
                    eq('id', Long.parseLong(params.programId))
                }
                and
                        {
                            eq('semester', semester.semesterNo)
                            eq('status', Status.findById(4))
                            eq("admitCardGenerated", true)
                            eq('registrationYear', Integer.parseInt(regYear))

                        }
            }.rollNo

        }
        catch (Exception e) {
            println(" Problem in service for generating marks foil sheet for PDF" + e)
        }
        return stuList

    }

    def getMarksFoilData(params, excelPath) {

        def stuList = []
        def status = false
        if (params.btn == "excel") {
            File file = new File('' + excelPath);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);

            def course = ProgramDetail.findById(Integer.parseInt(params.programId)).courseName
            def programSession = ProgramSession.get(Integer.parseInt(params.programSessionId))
            def semester = Semester.findByProgramSessionAndId(programSession, Integer.parseInt(params.semester))
            def subjectName = Subject.get(Integer.parseInt(params.courseCode)).subjectName
            def regYear = (ProgramSession.findById(params.programSessionId).sessionOfProgram).substring(0, 4)
            def currentYear = new Date().format("yyyy")
            def studentObj = Student.createCriteria()
            stuList = studentObj.list {
                programDetail {
                    eq('id', Long.parseLong(params.programId))
                }
                and
                        {
                            eq('semester', semester.semesterNo)
                            eq('status', Status.findById(4))
                            eq("admitCardGenerated", true)
                            eq('registrationYear', Integer.parseInt(regYear))

                        }
            }.rollNo

            int count = 0
            if (stuList) {
                status = marksFoilExcelService.excelReport(params, stuList, course, count, workbook, currentYear, semester.semesterNo)
                workbook.write();
                workbook.close();
            }


            return status
        }
    }


    def getDetailForMisMatch(params,courseList,programSessionIns,semesterIns){

        def finalList=[],counter=1
        def stuList=studentList(params,semesterIns)
        if(stuList && courseList){
        for(def i=0;i<stuList.size();i++){
            def resultList=[],checkFlag=false
            resultList<<counter
            resultList<<stuList[i].rollNo
            for(def j=0;j<courseList.size();j++){

             def stuMarks1=   StudentMarks.findBySubjectIdAndStudentAndRoleId(courseList[j].subjectId,stuList[i], Role.get(8))
             def stuMarks2=   StudentMarks.findBySubjectIdAndStudentAndRoleId(courseList[j].subjectId,stuList[i],Role.get(9))

                if(stuMarks1==null || stuMarks2==null){
                    resultList<<"X"
                }
                else if(stuMarks1?.marksObtained!=stuMarks2?.marksObtained){
                    resultList<<"?"
                    checkFlag=true
                }
                else{
                    resultList<<""
                }

            }
            if(checkFlag){
                resultList<<"R"
            }
            else{
                resultList<<"F"
            }
            finalList<<resultList
            ++counter;
        }

        }

println("endddddd"+finalList)

        return finalList


    }



    def studentList(params, semester) {
        def stuList = []
         try {
            def regYear = (ProgramSession.findById(params.programId).sessionOfProgram).substring(0, 4)

            def studentObj = Student.createCriteria()
            stuList = studentObj.list {
                programDetail {
                    eq('id', Long.parseLong(params.programId))
                }
                and
                        {
                            eq('semester', semester.semesterNo)
                            eq('status', Status.findById(4))
                            eq("admitCardGenerated", true)
                            eq('registrationYear', Integer.parseInt(regYear))

                        }
            }

        }
        catch (Exception e) {
            println(" Problem in service for getting student list for mis-match report" + e)
        }
        return stuList

    }
}// MAIN CLOSING TAG