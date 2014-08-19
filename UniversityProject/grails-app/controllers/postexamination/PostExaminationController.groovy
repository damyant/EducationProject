package postexamination

import com.university.Role
import com.university.TabulatorProgram
import com.university.TabulatorSemester
import com.university.User
import com.university.UserRole
import examinationproject.CourseSubject
import examinationproject.ProgramDetail
import examinationproject.ProgramGroup
import examinationproject.ProgramGroupDetail
import examinationproject.ProgramSession
import examinationproject.Semester
import examinationproject.Status
import examinationproject.Student
import examinationproject.Subject
import examinationproject.SubjectSession
import grails.converters.JSON
import grails.converters.XML
import groovy.xml.MarkupBuilder

import javax.activation.MimetypesFileTypeMap

/**
 * Created by Digvijay on 3/6/14.
 */
class PostExaminationController {
    def pdfRenderingService
    def marksEnteringService
    def postExaminationService
    def springSecurityService

    def createMarksFoil = {
        def programList = ProgramSession.list()
        [programList: programList]
    }
    def markMismatchReport = {


    }
    def marksUpdation = {
            def programList=ProgramDetail.list(sort: 'courseCode')
            def marksTypeList = MarksType.list()
            [programList: programList, marksTypeList: marksTypeList]
    }


    def getCourseData = {

        def subjectIns = []
        try {
            subjectIns = marksEnteringService.getCourseDetail(params)
        }
        catch (Exception e) {
            println("<<<<< Problem in getting subjects" + e)
        }
        render subjectIns as JSON
    }

    def getSemesterOfProgram = {

        println("*******")

        def semesterIns = []
        try {
            semesterIns = Semester.findAllByProgramSession(ProgramSession.get(Integer.parseInt(params.program)))
        }
        catch (Exception e) {
            println("<<<<<<<<< Problem in getting semester of Program" + e)
        }
        render semesterIns as JSON
    }

    def semesterForMarksMisMatch={
        println("?????"+params)
        def semesterIns = []
        try {
            semesterIns = Semester.findAllByProgramSession(ProgramSession.get(Integer.parseInt(params.session)))
        }
        catch (Exception e) {
            println("<<<<<<<<< Problem in getting semester of Program" + e)
        }
        render semesterIns as JSON

    }

    def getGroup = {

        def groupIns = []
        try {
            groupIns = marksEnteringService.getGroupDetail(params)
        }
        catch (Exception e) {
            println("<<<<< Problem in getting groups on semester" + e)
        }
        render groupIns as JSON
    }

    def generateMarksFoilSheet = {

        try {
            if (params.btn == "pdf") {
                def course = ProgramDetail.findById(Integer.parseInt(params.programId)).courseName
                def programSession = ProgramSession.get(Integer.parseInt(params.programSessionId))
                def semester = Semester.findByProgramSessionAndId(programSession, Integer.parseInt(params.semester))
                def subjectName = Subject.get(Integer.parseInt(params.courseCode)).subjectName
                def studentList = postExaminationService.dataForMarksFoilSheetPdf(params, semester)
                if (studentList) {
                    def args = [template: "generateMarksFoil", model: [program: course, semester: semester.semesterNo, subjectName: subjectName, stuList: studentList], filename: "MarksFoilSheet.pdf"]
                    pdfRenderingService.render(args + [controller: this], response)
                } else {
                    flash.message = "No Roll Number Found"
                    redirect(controller: 'postExamination', action: 'createMarksFoil')
                }

            } else {
                def webRootDir = servletContext.getRealPath("/")
                def userDir = new File(webRootDir, '/Report')
                userDir.mkdirs()
                println("userDir--" + userDir)
                def excelPath = servletContext.getRealPath("/") + 'Report' + System.getProperty('file.separator') + 'MarksFoilSheet.xls'
                def status = postExaminationService.getMarksFoilData(params, excelPath)

                if (status) {
                    println("back in controller " + status)
                    File myFile = new File(servletContext.getRealPath("/") + 'Report' + System.getProperty('file.separator') + 'MarksFoilSheet.xls')
                    //response.setHeader "Content-disposition", "attachment; filename="+'Student_List_'+params.session+".xls"
                    response.setHeader "Content-disposition", "attachment; filename=" + 'MarksFoilSheet' + ".xls"
                    response.contentType = new MimetypesFileTypeMap().getContentType(myFile)
                    response.outputStream << myFile.bytes
                    response.outputStream.flush()
                    myFile.delete()
                } else {
                    flash.message = "No Roll Number Found"
                    redirect(controller: 'postExamination', action: 'createMarksFoil')
                }

            }
        }
        catch (Exception e) {
            println("<<<<<<<< Problem in generating marks foil sheet" + e)
        }

    }


    def marksEntering = {
        def currentUser = springSecurityService.currentUser
        def criteria = TabulatorProgram.createCriteria()
        def progList = criteria.listDistinct() {
            projections {
                distinct("program")
            }
            eq('user',currentUser)
        }
        def programList=[]
        progList.each {
            programList<<ProgramDetail.findById(it.id)
        }
        def marksTypeList = MarksType.list()
        [programList: programList, marksTypeList: marksTypeList]
    }


    def getRollNoList = {
        def finalList = []
        try {
            finalList = marksEnteringService.getRollNumbers(params)
        }
        catch (Exception e) {
            println("Exception in getting roll number list for marks" + e)
        }

        render finalList as JSON
    }


    def resultProcessing = {
        def programList=ProgramDetail.list(sort: 'courseCode')
        [programList:programList]
    }

    def finalResult = {
        println("Inside finalResult Action")
    }

    def absenteeProcessing = {
        println("Inside absenteeProcessing Action..")
    }
    def bulkMarksSheet = {
        println("Inside bulkMarksSheet Action ..")
    }

    def singleMarksSheet = {
        println("Inside singleMarksSheet Action..")
    }


    def saveMarks = {
        println("saving marks=====" + params)
        def result = marksEnteringService.saveMarks(params)
//        println("??????????????????"+status)
        if (result.status) {
            render result as JSON
        }

    }
    def xmlCreateData = {
        def xmlObj = new StringWriter()
        def xml = new MarkupBuilder(xmlObj)
        xml.rule() {
            program(code:'01') {
                semester(id:1) {
                    subject(code: '11001', credit: '8') {
                        theory(total: '64', '24')
                        home(total: '12', '0')
                    }
                    subject(code: '11002', credit: '8') {
                        theory(total: '64', '24')
                        home(total: '12', '0')
                    }
                    subject(code: '11003', credit: '8') {
                        theory(total: '64', '24')
                        home(total: '12', '0')
                    }
                    subject(code: '11004', credit: '8') {
                        theory(total: '64', '24')
                        home(total: '12', '0')
                    }
                    subject(code: '11005', credit: '8') {
                        theory(total: '64', '24')
                        home(total: '12', '0')
                    }
                }
            }
        }
        def xmlString = xmlObj.toString()
        println ("........................."+xmlString)

    }
    def xmlParseData = {
        XmlParser parser = new XmlParser()
        def xmldata1 = parser.parse (new FileInputStream("web-app/ruleXml/marksRule.xml"))
        println(""+xmldata1)
        def allRecords = xmldata1.program.semester.size()
        println(allRecords)
    }
    def loadMarksType(){
        def returnMap = [:]
        def  marksTypeList=SubjectMarksDetail.findAllBySubjectSession(SubjectSession.findBySubjectId(Subject.findById(params.subjectID))).marksTypeId
        returnMap.marksTypeList=marksTypeList
        render returnMap as JSON
    }
    def getTabulatorSession={
        def returnMap = [:]
        def programSession = ProgramSession.findAllByProgramDetailId(ProgramDetail.findById(Integer.parseInt(params.program)))
        returnMap.session=programSession
        render returnMap as JSON
    }
    def getTabulatorSemester={
        def returnMap = [:]
        def tabSemesterList=[]
        def currentUser = springSecurityService.currentUser
        def tabProgramInstList=TabulatorProgram.findAllByProgramAndUser(ProgramDetail.findById(Integer.parseInt(params.program)),currentUser)

        def programSession = ProgramSession.findAllByProgramDetailId(ProgramDetail.findById(Integer.parseInt(params.program)))
        tabProgramInstList.each{
            tabSemesterList<<TabulatorSemester.findAllByTabulatorProgram(it).semesterId
        }

        returnMap.tabSemesterList=tabSemesterList
        returnMap.session=programSession

        render returnMap as JSON
    }
def getSemesterForMarksUpdate={
        def returnMap = [:]
        def semesterList=[]
        def session=ProgramSession.findById(params.session).sessionOfProgram
        def programSession = ProgramSession.findByProgramDetailIdAndSessionOfProgram(ProgramDetail.findById(Integer.parseInt(params.program)),session)
        semesterList=Semester.findAllByProgramSession(programSession)

        returnMap.semesterList=semesterList
        render returnMap as JSON
    }

    def checkMarks = {
        def returnMap = [:]
        def programSession = ProgramSession.get(Integer.parseInt(params.session))
        def semester = Semester.findByProgramSessionAndId(programSession, Integer.parseInt(params.semester))
        def currentUser = springSecurityService.currentUser
        def role=UserRole.findAllByUser(currentUser).role
        def role_id
        role.each {
            def tabSemIns=TabulatorSemester.findBySemesterIdAndTabulatorProgram(semester, TabulatorProgram.findByProgramAndRole(ProgramDetail.findById(params.program),it))
            if(tabSemIns){
                role_id = TabulatorSemester.findBySemesterIdAndTabulatorProgram(semester, TabulatorProgram.findByProgramAndRole(ProgramDetail.findById(params.program),it)).tabulatorProgram.role.id
            }
        }
        def otherRoll_Id
        if(role_id==9){
            otherRoll_Id=10
        }
        else{
            otherRoll_Id=9
        }
        def stuIns = StudentMarks.findBySubjectIdAndSemesterNoAndRoleId(Subject.get(Integer.parseInt(params.subjectId)),
                Integer.parseInt(params.semester), Role.get(otherRoll_Id), MarksType.get(Integer.parseInt(params.marksType)))
        if (stuIns) {
            if(params.marksType=='1'){
                if (stuIns.theoryMarks== params.marksValue) {
                    returnMap.status = true

                } else {
                    returnMap.status = false
                    returnMap.tabulator =  Role.get(otherRoll_Id).authority
                }
            }
            else if (params.marksType=='2'){
                if (stuIns.practicalMarks == params.marksValue) {
                    returnMap.status = true

                } else {
                    returnMap.status = false
                    returnMap.tabulator =  Role.get(otherRoll_Id).authority
                }
            }
            else if (params.marksType=='3'){
                if (stuIns.homeAssignmentMarks== params.marksValue) {
                    returnMap.status = true

                } else {
                    returnMap.status = false
                    returnMap.tabulator =  Role.get(otherRoll_Id).authority
                }
            }
            else if (params.marksType=='4'){
                if (stuIns.project == params.marksValue) {
                    returnMap.status = true

                } else {
                    returnMap.status = false
                    returnMap.tabulator =  Role.get(otherRoll_Id).authority
                }
            }
        } else {
            returnMap.status = true
        }
        render returnMap as JSON
    }

    def getStudentSession = {

        def criteria = Student.createCriteria()
        def stuSessionList = criteria.list() {
            projections {
                distinct("registrationYear")
            }
        }
        println(stuSessionList)

        render stuSessionList as JSON

    }

    def marksMissMatchData = {

        def groupSubList = [], groupSubjectCount = []
        def count = 0

        def programSessionIns = ProgramSession.findById(Long.parseLong(params.programId))
        def semesterIns = Semester.get(params.programTerm)
        println("*********************"+params.programTerm)
        def courseList = CourseSubject.findAllByProgramSessionAndSemester(programSessionIns, semesterIns).subjectSessionId
        def groupIns = ProgramGroup.findAllByProgramSessionAndSemester(programSessionIns, semesterIns)
        println("???????????????????????"+groupIns)
        if (groupIns) {
            groupIns.each {
                groupSubList << ProgramGroupDetail.findAllByProgramGroupId(it).subjectSessionId
                groupSubjectCount << groupSubList[0].size()
                ++count
            }
        }


        def result = postExaminationService.getDetailForMisMatch(params, courseList, semesterIns,groupSubList)

        if (result) {
            def args = [template: "missMatchReportTemplate", model: [programName: programSessionIns.programDetailId.courseName, semester: semesterIns.semesterNo,
                    subjectList: courseList,marksType:result.marksType,headerList:result.headerList, finalList: result.finalList, groupIns: groupIns, groupSubList: groupSubList, groupSubjectCount: groupSubjectCount], filename: "Mis-Match Report.pdf"]
            pdfRenderingService.render(args + [controller: this], response)
        } else {
            flash.message = "No Roll Number Found"
            redirect(controller: 'postExamination', action: 'markMismatchReport')
        }

    }
    def marksMissMatchUpdate = {
        def finalList = postExaminationService.getRollNoForMisMatchUpdate(params)
        render finalList as JSON
    }
    def loadTabulatorMarks={
        println("++++++++++++++++++"+params)
        def finalList = postExaminationService.getTabulatorMarks(params)
        render finalList as JSON
    }
    def updateMisMatchMarks={
        println("++++++++++++++++++"+params)
        def result = postExaminationService.saveMisMatchMarks(params)
        render result as JSON
    }
    def generateResults={
        println("++++++++++++++++++"+params)
        def progSessionInst = ProgramSession.findById(Long.parseLong(params.sessionId))
        def semesterInst = Semester.findById(Long.parseLong(params.semesterId))
        def studentList = Student.findAllByProgramSessionAndSemester(progSessionInst, semesterInst.semesterNo)
        def result = postExaminationService.generateProgramResults(params)
        if (result.status) {
            def args = [template: "programResultSheet", model: [studPartialList: result.studentPartialPassList, studPassList: result.studentPassList,
                                                                     courseName: progSessionInst.programDetailId.courseName,semester:semesterInst.semesterNo], filename: progSessionInst.programDetailId.courseName+".pdf"]
            pdfRenderingService.render(args + [controller: this], response)
        } else {
            flash.message = result.msg
            redirect(controller: 'postExamination', action: 'resultProcessing')
        }
    }
}// CLOSING BRACKETS
