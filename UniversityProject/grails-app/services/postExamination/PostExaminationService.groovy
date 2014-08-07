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
import postexamination.MarksType
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

    def getRollNoForMisMatchUpdate(params) {
        def finalList = [], counter = 1
        def semesterIns = Semester.findById(params.semester)
        def stuList = studentList(params, semesterIns)
        if (stuList) {
            for (def i = 0; i < stuList.size(); i++) {
                def stuMarks1 = StudentMarks.findBySubjectIdAndStudentAndRoleId(Subject.findById(params.subjectId), stuList[i], Role.get(9))
                def stuMarks2 = StudentMarks.findBySubjectIdAndStudentAndRoleId(Subject.findById(params.subjectId), stuList[i], Role.get(10))
                if (stuMarks1?.marksObtained != stuMarks2?.marksObtained) {
                    finalList<< stuList[i]
                }
            }
        }
        return finalList
    }


    def getDetailForMisMatch(params, courseList, programSessionIns, semesterIns) {

        def finalList = [], counter = 1
        def stuList = studentList(params, semesterIns)
        if (stuList && courseList) {
            for (def i = 0; i < stuList.size(); i++) {
                def resultList = [], checkFlag = false
                resultList << counter
                resultList << stuList[i].rollNo
                for (def j = 0; j < courseList.size(); j++) {

                    def stuMarks1 = StudentMarks.findBySubjectIdAndStudentAndRoleId(courseList[j].subjectId, stuList[i], Role.get(9))
                    def stuMarks2 = StudentMarks.findBySubjectIdAndStudentAndRoleId(courseList[j].subjectId, stuList[i], Role.get(10))

                    if (stuMarks1 == null || stuMarks2 == null) {
                        resultList << "X"
                    } else if (stuMarks1?.marksObtained != stuMarks2?.marksObtained) {
                        resultList << "?"
                        checkFlag = true
                    } else {
                        resultList << ""
                    }

                }
                if (checkFlag) {
                    resultList << "R"
                } else {
                    resultList << "F"
                }
                finalList << resultList
                ++counter;
            }

        }

        println("endddddd" + finalList)

        return finalList


    }


    def studentList(params, semester) {
        def stuList = []
        try {
            def regYear = (ProgramSession.findById(params.session).sessionOfProgram).substring(0, 4)

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
    def getTabulatorMarks(params){
        def marks = [:]
        def tab1MarksInst=[],tab2MarksInst=[]
        def tab1Obj = StudentMarks.createCriteria()
        def tab2Obj = StudentMarks.createCriteria()
        tab1MarksInst = tab1Obj.list {
            student {
                eq('id', Long.parseLong(params.studentId))
            }

            and{
                        eq('marksTypeId', MarksType.findById(Long.parseLong(params.marksType)))
                        eq('semesterNo', Semester.findById(Long.parseLong(params.semester)).semesterNo)
                        eq("subjectId", Subject.get(1))
                        eq('roleId', Role.get(9))

            }
        }
        tab2MarksInst = tab2Obj.list {
            student {
                eq('id', Long.parseLong(params.studentId))
            }
            and{
                        eq('marksTypeId', MarksType.findById(Long.parseLong(params.marksType)))
                        eq('semesterNo', Semester.findById(Long.parseLong(params.semester)).semesterNo)
                        eq("subjectId", Subject.get(Integer.parseInt(params.subjectId)))
                        eq('roleId', Role.get(10))
            }
        }
        println("==========Semester================="+Semester.findById(Long.parseLong(params.semester)).semesterNo)
        println("=============MarksType=============="+MarksType.findById(Long.parseLong(params.marksType)))
        println("============Subject==============="+Subject.get(Integer.parseInt(params.subjectId)))
        println("===========Role================"+Role.get(10))
        println("___________tab1MarksInst________________"+tab1MarksInst)
        println("____________tab2MarksInst_______________"+tab2MarksInst)
        marks.tab1Marks=tab1MarksInst[0].marksObtained
        marks.tab2Marks=tab2MarksInst[0].marksObtained
        println(marks)
        return marks
    }
}// MAIN CLOSING TAG