package postExamination

import com.university.Role
import examinationproject.CourseSubject
import examinationproject.ProgramDetail
import examinationproject.ProgramGroup
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


    def getDetailForMisMatch(params, courseList, semesterIns,groupSubList) {

        def finalList = [], counter = 1
        def stuList = studentList(params, semesterIns)
        if (stuList && courseList) {
            def marksTypeObj=MarksType.list()
            for (def i = 0; i < stuList.size(); i++) {
                def resultList = [], checkFlag = false
                resultList << counter
                resultList << stuList[i].rollNo
                for (def j = 0; j < courseList.size(); j++) {
                    marksTypeObj.each{
                        def stuMarks1 = StudentMarks.findBySubjectIdAndStudentAndRoleIdAndMarksTypeId(courseList[j].subjectId, stuList[i], Role.get(9),it)
                        def stuMarks2 = StudentMarks.findBySubjectIdAndStudentAndRoleIdAndMarksTypeId(courseList[j].subjectId, stuList[i], Role.get(10),it)

                         if (stuMarks1 == null || stuMarks2 == null) {
                        resultList << "X"
                        } else if (stuMarks1?.marksObtained != stuMarks2?.marksObtained) {
                        resultList << "?"
                        checkFlag = true
                         } else {
                        resultList << ""
                        }
                    }
                }
                for (def j = 0; j < groupSubList.size(); j++) {
                    for (def k = 0; k < groupSubList[j].size(); k++) {
                        marksTypeObj{
                            def stuMarks1 = StudentMarks.findBySubjectIdAndStudentAndRoleIdAndMarksTypeId(courseList[j].subjectId, stuList[i], Role.get(9),it)
                            def stuMarks2 = StudentMarks.findBySubjectIdAndStudentAndRoleIdAndMarksTypeId(courseList[j].subjectId, stuList[i], Role.get(10),it)

                    if (stuMarks1 == null || stuMarks2 == null) {
                        resultList << "X"
                    } else if (stuMarks1?.marksObtained != stuMarks2?.marksObtained) {
                        resultList << "?"
                        checkFlag = true
                    } else {
                        resultList << ""
                    }

                    }

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
        marks.tab1Marks=tab1MarksInst[0].marksObtained
        marks.tab2Marks=tab2MarksInst[0].marksObtained
        return marks
    }
    def saveMisMatchMarks(params){
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
        tab1MarksInst.each {
            it.marksObtained=Integer.parseInt(params.updatedMarks)
        }
        tab2MarksInst.each {
            it.marksObtained=Integer.parseInt(params.updatedMarks)
        }
        marks.status=true
        return marks
    }
    def generateProgramResults(params){
        def returnMap = [:]
        def passInAll=[],partiallyPass=[]
        def progSessionInst=ProgramSession.findById(Long.parseLong(params.sessionId))
        def semesterInst=Semester.findById(Long.parseLong(params.semesterId))
        def studentList=Student.findAllByProgramSessionAndSemester(progSessionInst,semesterInst.semesterNo)
        def subjetList=CourseSubject.findAllByProgramSessionAndSemester(progSessionInst,semesterInst)
        def subjetGroupList=ProgramGroup.findAllByProgramSessionAndSemester(progSessionInst,semesterInst)
        def marksTypeList=MarksType.list()
        for (def i=0;i<studentList.size();i++){
            def pass=true
            if(subjetList.size()>0){
                for (def j=0;j<subjetList.size();j++){
                    marksTypeList.each {
//                        if(StudentMarks.findByStudentAndMarksTypeIdAndSubjectIdAndSemesterNo(studentList[i],))
                    }
                }
            }
        }

    }
}// MAIN CLOSING TAG