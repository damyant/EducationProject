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
import org.apache.tools.ant.taskdefs.Exit
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
                    def missMatchBlank=false,missMatch=false
                    def marksTypeObj=[]
                    def subMarksInst= SubjectMarksDetail.findAllBySubjectSession(courseList[j])
                    if(subMarksInst.theory){
                        marksTypeObj<<MarksType.findById(1)
                    }
                    if(subMarksInst.home){
                        marksTypeObj<<MarksType.findById(2)
                    }
                    if(subMarksInst.practical){
                        marksTypeObj<<MarksType.findById(3)
                    }
                    if(subMarksInst.project){
                        marksTypeObj<<MarksType.findById(4)
                    }
                    if (i == 0) {
                        headerList << courseList[j].subjectId.subjectName
                    }
                    marksTypeObj.each {
                        def stuMarks1 = StudentMarks.findBySubjectIdAndStudentAndRoleId(courseList[j].subjectId, stuList[i], Role.get(9))
                        def stuMarks2 = StudentMarks.findBySubjectIdAndStudentAndRoleId(courseList[j].subjectId, stuList[i], Role.get(10))
                        if(!missMatch) {
                            if (stuMarks1 == null && stuMarks2 == null) {
                                missMatchBlank = true
                            } else if (stuMarks1?.theoryMarks != stuMarks2?.theoryMarks && it.id == 1) {
                                missMatch = true
                                missMatchBlank = false
                            } else if (stuMarks1?.practicalMarks != stuMarks2?.practicalMarks && it.id == 2) {
                                missMatch = true
                                missMatchBlank = false
                            } else if (stuMarks1?.homeAssignmentMarks != stuMarks2?.homeAssignmentMarks && it.id == 3) {
                                missMatch = true
                                missMatchBlank = false
                            } else if (stuMarks1?.project != stuMarks2?.project && it.id == 4) {
                                missMatch = true
                                missMatchBlank = false
                            } else if (stuMarks1 != null && stuMarks2 == null) {
                                missMatch = true
                                missMatchBlank = false
                            } else if (stuMarks1 == null && stuMarks2 != null) {
                                missMatch = true
                                missMatchBlank = false
                            }
                        }
                    }
                    if(!missMatchBlank && !missMatch){
                        resultList << ""
                    }
                    else if(missMatchBlank){
                        resultList << "X"
                    }
                    else if(missMatch){
                        resultList << "?"
                    }
                }
                if (groupSubList.size() > 0) {
                    for (def j = 0; j < groupSubList.size(); j++) {
                        for (def k = 0; k < groupSubList[j].size(); k++) {
                            def missMatchBlank=false,missMatch=false
                            def marksTypeObj = []
                            def subMarksInst= SubjectMarksDetail.findAllBySubjectSession(groupSubList[j])
                            if(subMarksInst.theory){
                                marksTypeObj<<MarksType.findById(1)
                            }
                            if(subMarksInst.home){
                                marksTypeObj<<MarksType.findById(2)
                            }
                            if(subMarksInst.practical){
                                marksTypeObj<<MarksType.findById(3)
                            }
                            if(subMarksInst.project){
                                marksTypeObj<<MarksType.findById(4)
                            }
                            if (j == 0) {
                                headerList << groupSubList[j].subjectId.subjectName
                            }
                            marksTypeObj.each {
                                def stuMarks1 = StudentMarks.findBySubjectIdAndStudentAndRoleId(groupSubList[j].subjectId, stuList[i], Role.get(9))
                                def stuMarks2 = StudentMarks.findBySubjectIdAndStudentAndRoleId(groupSubList[j].subjectId, stuList[i], Role.get(10))

                                if(!missMatch) {
                                    if (stuMarks1 == null && stuMarks2 == null) {
                                        missMatchBlank = true
                                    } else if (stuMarks1?.theoryMarks != stuMarks2?.theoryMarks && it.id == 1) {
                                        missMatch = true
                                        missMatchBlank = false
                                    } else if (stuMarks1?.practicalMarks != stuMarks2?.practicalMarks && it.id == 2) {
                                        missMatch = true
                                        missMatchBlank = false
                                    } else if (stuMarks1?.homeAssignmentMarks != stuMarks2?.homeAssignmentMarks && it.id == 3) {
                                        missMatch = true
                                        missMatchBlank = false
                                    } else if (stuMarks1?.project != stuMarks2?.project && it.id == 4) {
                                        missMatch = true
                                        missMatchBlank = false
                                    } else if (stuMarks1 != null && stuMarks2 == null) {
                                        missMatch = true
                                        missMatchBlank = false
                                    } else if (stuMarks1 == null && stuMarks2 != null) {
                                        missMatch = true
                                        missMatchBlank = false
                                    }
                                }
                            }
                            if(!missMatchBlank && !missMatch){
                                resultList << ""
                            }
                            else if(missMatchBlank){
                                resultList << "X"
                            }
                            else if(missMatch){
                                resultList << "?"
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
                def checkFlag=false
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
                                              }else{
                                                 checkFlag=true
                                             }

                                            if(Integer.parseInt(tab1Marks.homeAssignmentMarks)<Integer.parseInt(itr) && index==1){
                                                    pass = false
                                                    partiallyPass << studentList[i]
                                                }
                                            else{
                                                checkFlag=true
                                            }
                                             if(Integer.parseInt(tab1Marks.practicalMarks)<Integer.parseInt(itr) && index==2){
                                                   pass = false
                                                    partiallyPass << studentList[i]
                                                }
                                             else{
                                                 checkFlag=true
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
                    if (pass && checkFlag ) {
                          passInAll << studentList[i]
                          finalStuList <<" "
                    }
                }

                totalMarksList<<tempMarksVariable
                totalStudentList<<studentList[i]
            }

       finalStuList=  totalMarksAndResultUpdation(totalStudentList,totalMarksList,partiallyPass,passInAll,finalStuList)

                returnMap.studentPartialPassList = partiallyPass.unique()
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


    def totalMarksAndResultUpdation(totalStudentList,totalMarksList,partiallyPass,passInAll,finalStuList){

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
            tempMap[it.id]=getBackJsonObj[it.semester.toString()]
            if(it.result){
                def getBackJsonObj1 = grails.converters.JSON.parse( it.result)
                getBackJsonObj1.putAt(it.semester,'Pass')
                it.result= getBackJsonObj1.toString()
            }
            else{
                def tempMap1=[:]
                tempMap1[it.semester]='Pass'
                def newJsonObj=tempMap1 as JSON
                it.result=newJsonObj.toString()
            }
            it.save(failOnError:true)
        }

        partiallyPass.unique().each {
            if(it.result){
                def getBackJsonObj1 = grails.converters.JSON.parse( it.result)
                getBackJsonObj1.putAt(it.semester,'Fail')
                it.result= getBackJsonObj1.toString()
            }
            else{
                def tempMap1=[:]
                tempMap1[it.semester]='Fail'
                def newJsonObj=tempMap1 as JSON
                it.result=newJsonObj.toString()
            }
            it.save(failOnError:true)

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

        return finalStuList

    }

    def finalResult(params){
        def returnMap=[:]
        def tempMap=[:]
        def newStuList=[],finalStuList=[]
        def progSessionInst = ProgramSession.findById(Long.parseLong(params.sessionId))
        def semesterInst = Semester.findById(Long.parseLong(params.semesterId))

        def studentList = Student.findAllByProgramSessionAndSemesterAndAdmitCardGeneratedAndStatus(progSessionInst, semesterInst.semesterNo,true,Status.get(4))


        studentList.each{
            def getBackJsonObj = grails.converters.JSON.parse(it.result)
            def getBackJsonObj1 = grails.converters.JSON.parse(it.totalMarks)
            def result= getBackJsonObj[it.semester.toString()]

            if(result.toString().equalsIgnoreCase("Pass")){
                newStuList<<it
                finalStuList<<""
                tempMap[it.id]=getBackJsonObj1[it.semester.toString()]
            }
        }
        Map sorted = tempMap.sort { a, b -> a.value <=> b.value }


        def keyList=sorted.keySet()
        for(int i=0;i<newStuList.size();i++){
            for(int j=0;j<keyList.size();j++){
                if(newStuList[i].id==keyList[j]){
                    finalStuList.remove(j)
                    finalStuList.add(j,newStuList[i])
                }

            }
        }

        returnMap.studentMeritList = finalStuList
        returnMap.totalStudentsAppeared=studentList.size()
        returnMap.status = true

        return  returnMap

    }

    def studentMeritRegisterData(params, excelPath,studentList,xmlNodes,subjectList,semesterInst) {
        println("in merit register service")

        def stuList = [],marksList=[],subjectMarksList=[],finalList=[],passingSubjectNoList=[]
//        def status = false

            studentList.each{it ->
                subjectMarksList=[]
                def counter=0
                subjectList.each{ itr ->
                     ++counter

//                    for(def i=0;i<xmlNodes.childNodes.length;i++){
//                        marksList=[]
//                        def status=false
//
//                        if(xmlNodes.item(i).getNodeName()!="#text" && xmlNodes.item(i).getAttribute("code").contains(itr.subjectCode) ){
//                            def stuMarksObject = StudentMarks.findByStudentAndSubjectIdAndSemesterNoAndRoleId(it, itr, Integer.parseInt(semesterInst.id.toString()), Role.get(9))
//
//
//                            for(def j=0;j<xmlNodes.item(i).getChildNodes().getLength();j++){
//
//                                 if(xmlNodes.item(i).item(j).getNodeName()!="#text" ){
//                                     if(Integer.parseInt(xmlNodes.item(i).item(j).getAttribute("minMarks"))>0){
//
//                                     if(xmlNodes.item(i).item(j).getNodeName()=="theory"){
//                                        marksList<<stuMarksObject.theoryMarks
//                                         status=true
//                                     }
//                                     if(xmlNodes.item(i).item(j).getNodeName()=="home"){
//                                         marksList<<stuMarksObject.homeAssignmentMarks
//                                         status=true
//                                     }
//                                     if(xmlNodes.item(i).item(j).getNodeName()=="practical"){
//                                         marksList<<stuMarksObject.practicalMarks
//                                         status=true
//                                        }
//                                     }
//                                     else{
//                                         marksList<<"no"
//                                     }
//
//                                 }
//
//                            }
//                            println("br"+i)
//                            subjectMarksList<<marksList
//
//
//                        }
//
//                    }
                    marksList=[]
                    def subMinPassingMarks=0
                    def stuMarksObject = StudentMarks.findByStudentAndSubjectIdAndSemesterNoAndRoleId(it, itr, Integer.parseInt(semesterInst.id.toString()), Role.get(9))

                    xmlNodes.subject.each{itr1 ->


                        if(itr.subjectCode.contains(itr1?.@code)){

                            subMinPassingMarks=Integer.parseInt(itr1?.@minMarks)

                            if(Integer.parseInt(itr1?.theory?.@minMarks[0])>0){

                               marksList<< stuMarksObject.theoryMarks
                            }
                            else{
                                marksList<<"no"
                            }
                            if(Integer.parseInt(itr1?.home?.@minMarks[0])>0){
                                marksList<<stuMarksObject.homeAssignmentMarks
                            }
                            else{
                                marksList<<"no"
                            }
                            if(Integer.parseInt(itr1?.practical?.@minMarks[0])>0){
                                marksList<<stuMarksObject.practicalMarks
                            }
                            else{
                                marksList<<"no"
                            }

                        }
//                        if(Integer.parseInt(stuMarksObject.totalMarks)<Integer.parseInt(itr1?.@minMarks)){
//                            passingSubjectNoList<<"0"
//
//                        }else{
//                            passingSubjectNoList<<counter
//                        }

                    }
                    if(stuMarksObject.totalMarks && Integer.parseInt(stuMarksObject.totalMarks)< subMinPassingMarks){
                        marksList<<stuMarksObject.totalMarks
                        passingSubjectNoList<<counter

                    }
                    else{
                        marksList<<"NC"
                        passingSubjectNoList<<"No"
                    }

                    subjectMarksList<<marksList

                }
                finalList<<subjectMarksList
//                finalList
            }


        println("sub==="+finalList)



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


            int count = 0
            if (stuList) {
                status = marksFoilExcelService.excelReport(params, stuList, course, count, workbook, currentYear, semester.semesterNo)
                workbook.write();
                workbook.close();
            }


            return status

    }
}// MAIN CLOSING TAG