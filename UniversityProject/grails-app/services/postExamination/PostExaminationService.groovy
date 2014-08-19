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
import postexamination.SubjectMarksDetail

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
                def stuMarks1 = StudentMarks.findBySubjectIdAndStudentAndRoleId(Subject.findById(params.subjectId), stuList[i], Role.get(9), MarksType.findById(params.marksType))
                def stuMarks2 = StudentMarks.findBySubjectIdAndStudentAndRoleId(Subject.findById(params.subjectId), stuList[i], Role.get(10), MarksType.findById(params.marksType))
                def firstMarks,secondMarks
                if(params.marksType=='1'){
                    firstMarks=stuMarks1?.theoryMarks
                    secondMarks=stuMarks2?.theoryMarks
                }
                else if(params.marksType=='2'){
                    firstMarks=stuMarks1?.practicalMarks
                    secondMarks=stuMarks2?.practicalMarks
                }
                else if(params.marksType=='3'){
                    firstMarks=stuMarks1?.homeAssignmentMarks
                    secondMarks=stuMarks2?.homeAssignmentMarks
                }
                else if(params.marksType=='4'){
                    firstMarks=stuMarks1?.project
                    secondMarks=stuMarks2?.project
                }
                if (firstMarks != secondMarks) {
                    finalList << stuList[i]
                }
            }
        }
        return finalList
    }


    def getDetailForMisMatch(params, courseList, semesterIns, groupSubList) {
        def returnMap = [:]
        def finalList = [], headerList = [], marksType = [], counter = 1
        def stuList = studentList(params, semesterIns)
        if (stuList && courseList) {

            for (def i = 0; i < stuList.size(); i++) {
                def resultList = [], checkFlag = false
                resultList << counter
                resultList << stuList[i].rollNo
                for (def j = 0; j < courseList.size(); j++) {
                    def marksTypeObj = SubjectMarksDetail.findAllBySubjectSession(courseList[j]).marksTypeId
                    marksTypeObj.each {
                        def stuMarks1 = StudentMarks.findBySubjectIdAndStudentAndRoleId(courseList[j].subjectId, stuList[i], Role.get(9))
                        def stuMarks2 = StudentMarks.findBySubjectIdAndStudentAndRoleId(courseList[j].subjectId, stuList[i], Role.get(10))
                        if (i == 0) {
                            headerList << courseList[j].subjectId.subjectName
                            marksType << it.marksTypeName
                        }
                        if (stuMarks1 == null && stuMarks2 == null) {
                            resultList << "X"
                        } else if (stuMarks1?.theoryMarks != stuMarks2?.theoryMarks && it.id==1) {
                            resultList << "?"
                            checkFlag = true
                        }
                        else if (stuMarks1?.practicalMarks != stuMarks2?.practicalMarks && it.id==2) {
                            resultList << "?"
                            checkFlag = true
                        }
                        else if (stuMarks1?.homeAssignmentMarks != stuMarks2?.homeAssignmentMarks && it.id==3) {
                            resultList << "?"
                            checkFlag = true
                        }
                        else if (stuMarks1?.project != stuMarks2?.project && it.id==4) {
                            resultList << "?"
                            checkFlag = true
                        }
                        else if (stuMarks1 != null && stuMarks2 == null) {
                            resultList << "?"
                            checkFlag = true
                        } else if (stuMarks1 == null && stuMarks2 != null) {
                            resultList << "?"
                            checkFlag = true
                        } else {
                            resultList << ""
                        }
                    }
                }
                if (groupSubList.size() > 0) {
                    for (def j = 0; j < groupSubList.size(); j++) {
                        for (def k = 0; k < groupSubList[j].size(); k++) {
                            def marksTypeObj = SubjectMarksDetail.findAllBySubjectSession(groupSubList[j]).marksTypeId
                            marksTypeObj.each {
                                def stuMarks1 = StudentMarks.findBySubjectIdAndStudentAndRoleId(groupSubList[j].subjectId, stuList[i], Role.get(9))
                                def stuMarks2 = StudentMarks.findBySubjectIdAndStudentAndRoleId(groupSubList[j].subjectId, stuList[i], Role.get(10))
                                if (j == 0) {
                                    headerList << groupSubList[j].subjectId.subjectName
                                    marksType << it.marksTypeName
                                }
                                if (stuMarks1 == null && stuMarks2 == null) {
                                    resultList << "X"
                                }
                                else if (stuMarks1?.theoryMarks != stuMarks2?.theoryMarks && it.id==1) {
                                    resultList << "?"
                                    checkFlag = true
                                }
                                else if (stuMarks1?.practicalMarks != stuMarks2?.practicalMarks && it.id==2) {
                                    resultList << "?"
                                    checkFlag = true
                                }
                                else if (stuMarks1?.homeAssignmentMarks != stuMarks2?.homeAssignmentMarks && it.id==3) {
                                    resultList << "?"
                                    checkFlag = true
                                }
                                else if (stuMarks1?.project != stuMarks2?.project && it.id==4) {
                                    resultList << "?"
                                    checkFlag = true
                                }
                                else if (stuMarks1 != null && stuMarks2 == null) {
                                    resultList << "?"
                                    checkFlag = true
                                } else if (stuMarks1 == null && stuMarks2 != null) {
                                    resultList << "?"
                                    checkFlag = true
                                } else {
                                    resultList << ""
                                }

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
        returnMap.finalList = finalList
        returnMap.headerList = headerList
        returnMap.marksType = marksType

        return returnMap


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

    def getTabulatorMarks(params) {
        def marks = [:]
        def tab1MarksInst = [], tab2MarksInst = []
        def tab1Obj = StudentMarks.createCriteria()
        def tab2Obj = StudentMarks.createCriteria()
        tab1MarksInst = tab1Obj.list {
            student {
                eq('id', Long.parseLong(params.studentId))
            }

            and {
                eq('semesterNo', Semester.findById(Long.parseLong(params.semester)).semesterNo)
                eq("subjectId", Subject.get(Integer.parseInt(params.subjectId)))
                eq('roleId', Role.get(9))

            }
        }
        tab2MarksInst = tab2Obj.list {
            student {
                eq('id', Long.parseLong(params.studentId))
            }
            and {
                eq('marksTypeId', MarksType.findById(Long.parseLong(params.marksType)))
                eq('semesterNo', Semester.findById(Long.parseLong(params.semester)).semesterNo)
                eq("subjectId", Subject.get(Integer.parseInt(params.subjectId)))
                eq('roleId', Role.get(10))
            }
        }
        if(params.marksType=='1'){
            marks.tab1Marks = tab1MarksInst[0].theoryMarks
            marks.tab2Marks = tab2MarksInst[0].theoryMarks
        }
        else if(params.marksType=='2'){
            marks.tab1Marks = tab1MarksInst[0].practicalMarks
            marks.tab2Marks = tab2MarksInst[0].practicalMarks
        }
        else if(params.marksType=='3'){
            marks.tab1Marks = tab1MarksInst[0].homeAssignmentMarks
            marks.tab2Marks = tab2MarksInst[0].homeAssignmentMarks
        }
        else if(params.marksType=='4'){
            marks.tab1Marks = tab1MarksInst[0].project
            marks.tab2Marks = tab2MarksInst[0].project
        }
        return marks
    }

    def saveMisMatchMarks(params) {
        def marks = [:]
        def tab1MarksInst = [], tab2MarksInst = []
        def tab1Obj = StudentMarks.createCriteria()
        def tab2Obj = StudentMarks.createCriteria()
        tab1MarksInst = tab1Obj.list {
            student {
                eq('id', Long.parseLong(params.studentId))
            }
            and {
                eq('semesterNo', Semester.findById(Long.parseLong(params.semester)).semesterNo)
                eq("subjectId", Subject.get(Integer.parseInt(params.subjectId)))
                eq('roleId', Role.get(9))
            }
        }
        tab2MarksInst = tab2Obj.list {
            student {
                eq('id', Long.parseLong(params.studentId))
            }
            and {
                eq('semesterNo', Semester.findById(Long.parseLong(params.semester)).semesterNo)
                eq("subjectId", Subject.get(Integer.parseInt(params.subjectId)))
                eq('roleId', Role.get(10))
            }
        }
        tab1MarksInst.each {
            def prevMarks
            if(params.marksType=='1'){
                prevMarks=it.theoryMarks
                it.theoryMarks= params.updatedMarks
            }
            else if(params.marksType=='2'){
                prevMarks=it.practicalMarks
                it.practicalMarks = params.updatedMarks
            }
            else if(params.marksType=='3'){
                prevMarks=it.homeAssignmentMarks
                it.homeAssignmentMarks = params.updatedMarks
            }
            else if(params.marksType=='4'){
                prevMarks=it.project
                it.project = params.updatedMarks
            }
            it.totalMarks=(Integer.parseInt(it.totalMarks)-Integer.parseInt(prevMarks))+Integer.parseInt(params.updatedMarks)
        }
        tab2MarksInst.each {
            def prevMarks
            if(params.marksType=='1'){
                prevMarks=it.theoryMarks
                it.theoryMarks= params.updatedMarks
            }
            else if(params.marksType=='2'){
                prevMarks=it.practicalMarks
                it.practicalMarks = params.updatedMarks
            }
            else if(params.marksType=='3'){
                prevMarks=it.homeAssignmentMarks
                it.homeAssignmentMarks = params.updatedMarks
            }
            else if(params.marksType=='4'){
                prevMarks=it.project
                it.project = params.updatedMarks
            }
            it.totalMarks=(Integer.parseInt(it.totalMarks)-Integer.parseInt(prevMarks))+Integer.parseInt(params.updatedMarks)

        }
        marks.status = true
        return marks
    }

    def generateProgramResults(params) {
        def returnMap = [:]

        def passInAll = [], partiallyPass = []
//        def misMatchStatus = StudentMarks.findAllByCorrectMarkIsNull()
//        if (misMatchStatus.size() == 0) {
            def progSessionInst = ProgramSession.findById(Long.parseLong(params.sessionId))
            def semesterInst = Semester.findById(Long.parseLong(params.semesterId))
            def studentList = Student.findAllByProgramSessionAndSemester(progSessionInst, semesterInst.semesterNo)
            def subjetList = CourseSubject.findAllByProgramSessionAndSemester(progSessionInst, semesterInst)
            def subjetGroupList = ProgramGroup.findAllByProgramSessionAndSemester(progSessionInst, semesterInst)

            def marksTypeList = MarksType.list()
            for (def i = 0; i < studentList.size(); i++) {
                def status = true
                def pass = true
                if (subjetList.size() > 0) {
                    for (def j = 0; j < subjetList.size(); j++) {
                        if (!pass || !status) {
                            break
                        } else {
                            marksTypeList.each {
                                if (pass) {
                                    def tab1Marks = StudentMarks.findByStudentAndMarksTypeIdAndSubjectIdAndSemesterNoAndRoleId(studentList[i], it, subjetList[j].subjectSessionId.subjectId, semesterInst.semesterNo, Role.get(9))
                                    def tab2Marks = StudentMarks.findByStudentAndMarksTypeIdAndSubjectIdAndSemesterNoAndRoleId(studentList[i], it, subjetList[j].subjectSessionId.subjectId, semesterInst.semesterNo, Role.get(10))
                                    def passMarks = SubjectMarksDetail.findBySubjectSessionAndMarksTypeId(subjetList[j].subjectSessionId, it)
                                    if (tab1Marks != null && tab2Marks != null) {
                                        println("#########################"+tab1Marks.marksObtained +"---------------------"+ passMarks.minPassingMarks)
                                        if (tab1Marks.marksObtained < passMarks.minPassingMarks) {
                                            pass = false
                                            partiallyPass << studentList[i]
                                        }
                                    } else {
                                        status = false
                                    }
                                }
                            }
                        }
                    }
                        if (pass) {
                            passInAll << studentList[i]
                        }
                }
            }
            println("partiallyPass________________________"+partiallyPass)
            println("passInAll________________________"+passInAll)
                returnMap.studentPartialPassList = partiallyPass
                returnMap.studentPassList = passInAll
                returnMap.status = true
//        } else {
//            returnMap.status = false
//            returnMap.msg = "Mismatch Exist in Marks For this Programme"
//        }
        return returnMap
    }
}// MAIN CLOSING TAG