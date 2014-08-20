package postExamination

import com.university.Role
import examinationproject.CourseSubject
import examinationproject.ProgramDetail
import examinationproject.ProgramGroup
import examinationproject.ProgramGroupDetail
import examinationproject.ProgramSession
import examinationproject.Semester
import examinationproject.Status
import examinationproject.Student
import examinationproject.Subject
import grails.converters.JSON
import grails.transaction.Transactional
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.write.WritableWorkbook
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.*;
import postexamination.MarksType
import postexamination.StudentMarks
import postexamination.SubjectMarksDetail

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

@Transactional
class PostExaminationService {
    //def writeExcelService
    def marksFoilExcelService

    def serviceMethod() {

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

    def generateProgramResults(params,xmlNodes) {
        def returnMap = [:]
        println("1111"+xmlNodes)
        def passInAll = [], partiallyPass = [],  finalStuList=[]
//        def misMatchStatus = StudentMarks.findAllByCorrectMarkIsNull()
//        if (misMatchStatus.size() == 0) {
            def progSessionInst = ProgramSession.findById(Long.parseLong(params.sessionId))
            def semesterInst = Semester.findById(Long.parseLong(params.semesterId))
            def studentList = Student.findAllByProgramSessionAndSemesterAndAdmitCardGeneratedAndStatus(progSessionInst, semesterInst.semesterNo,true,Status.get(4))
            def subjectList = CourseSubject.findAllByProgramSessionAndSemester(progSessionInst, semesterInst).subjectSessionId.subjectId
            def subjectGroupList = ProgramGroup.findAllByProgramSessionAndSemester(progSessionInst, semesterInst)
            if(subjectGroupList){
                subjectGroupList.each {
                    def groupSubjectList=ProgramGroupDetail.findAllByProgramGroupId(it).subjectSessionId.subjectId
                         groupSubjectList.each{itr ->
                            subjectList<<itr
                        }
                }

            }

       def subjectMarksList= minMarksForSubjectsFromXML(subjectList,xmlNodes)
        def totalMarksList=[],totalStudentList=[]
        def tempMarksVariable=0
            for (def i = 0; i < studentList.size(); i++) {
                def status = true
                def pass = true
                tempMarksVariable=0

                if (subjectList.size() > 0) {

                    for (def j = 0; j < subjectList.size(); j++) {

                        if (!pass || !status)
                        {
                            break
                        }
                        else {
                                if (pass) {
                                    def tab1Marks = StudentMarks.findByStudentAndSubjectIdAndSemesterNoAndRoleId(studentList[i], subjectList[j], Integer.parseInt(semesterInst.id.toString()), Role.get(9))

                                    if(tab1Marks){
                                    tempMarksVariable+=Integer.parseInt(tab1Marks.totalMarks)

                                    if (tab1Marks != null) {
                                        subjectMarksList.each{

                                            it.eachWithIndex{itr,index ->

                                             if(Integer.parseInt(tab1Marks.theoryMarks)<Integer.parseInt(itr) && index==0){
                                                 pass = false
                                                 partiallyPass << studentList[i]
                                              }
                                            if(Integer.parseInt(tab1Marks.homeAssignmentMarks)<Integer.parseInt(itr) && index==1){
                                                    pass = false
                                                    partiallyPass << studentList[i]
                                                }
                                             if(Integer.parseInt(tab1Marks.practicalMarks)<Integer.parseInt(itr) && index==2){
                                                   pass = false
                                                    partiallyPass << studentList[i]
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        status = false
                                    }
                                }
                             }
                        }

                    }
                    if (pass) {
                          passInAll << studentList[i]
                          finalStuList <<" "
                    }
                }

                totalMarksList<<tempMarksVariable
                totalStudentList<<studentList[i]
            }
println(passInAll)
        println(totalStudentList)
        def ab=["2","3","4","5","6"]
        def ab1=["4","5","6"]

        ab1.each { it
            if(it){
            def abc=ab.findIndexOf{ it in ab1 }
            println("???"+abc)
            }
//                    stuList.remove(rollNoIndex)
        }
        totalStudentList.eachWithIndex{it,index ->

            if(it.totalMarks){
                def getBackJsonObj = grails.converters.JSON.parse( it.totalMarks)
                getBackJsonObj.putAt(it.semester,totalMarksList[index])
                it.totalMarks= getBackJsonObj.toString()
             }
            else{
                def tempMap=[:]
                    tempMap[it.semester]=totalMarksList[index]
                    def newJsonObj=tempMap as JSON
                    it.totalMarks=newJsonObj.toString()
            }
            it.save(failOnError:true)
        }

        def tempMap=[:]
        passInAll.each{
            def getBackJsonObj = grails.converters.JSON.parse( it.totalMarks)
          println(getBackJsonObj)
            tempMap[it.id]=getBackJsonObj[it.semester.toString()]

        }
        Map sorted = tempMap.sort { a, b -> a.value <=> b.value }

        def keyList=sorted.keySet()
        for(int i=0;i<passInAll.size();i++){
            for(int j=0;j<keyList.size();j++){
                if(passInAll[i].id==keyList[j]){
                    finalStuList.remove(j)
                    finalStuList.add(j,passInAll[i])
                }

            }
        }

                returnMap.studentPartialPassList = partiallyPass
                returnMap.studentPassList = finalStuList
                returnMap.status = true

        return returnMap
    }

    def minMarksForSubjectsFromXML(subjectList,xmlNodes){
        def finalMarksList=[]
        subjectList.each{
            def marksList=[]
            xmlNodes.subject.each{itr ->

                if(it.subjectCode.contains(itr?.@code)){
                   if(marksList.size()>0){
                        marksList=[]
                    }
                    marksList<< itr?.theory?.@minMarks[0]
                    marksList<< itr?.home?.@minMarks[0]
                    marksList<< itr?.practical?.@minMarks[0]

                }

            }
           finalMarksList<<marksList
        }
    return  finalMarksList

    }

    def finalResult(params){
        def progSessionInst = ProgramSession.findById(Long.parseLong(params.sessionId))
        def semesterInst = Semester.findById(Long.parseLong(params.semesterId))

        def studentList = Student.findAllByProgramSessionAndSemesterAndAdmitCardGeneratedAndStatus(progSessionInst, semesterInst.semesterNo,true,Status.get(4))


        def tempMap=[:]
        studentList.each{
            def getBackJsonObj = grails.converters.JSON.parse( it.totalMarks)
            println(getBackJsonObj)
            tempMap[it.id]=getBackJsonObj[it.semester.toString()]

        }
        Map sorted = tempMap.sort { a, b -> a.value <=> b.value }

        def keyList=sorted.keySet()
        for(int i=0;i<passInAll.size();i++){
            for(int j=0;j<keyList.size();j++){
                if(passInAll[i].id==keyList[j]){
                    finalStuList.remove(j)
                    finalStuList.add(j,passInAll[i])
                }

            }
        }

        returnMap.studentPartialPassList = partiallyPass

    }
}// MAIN CLOSING TAG