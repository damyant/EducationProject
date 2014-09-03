package postexamination

import com.university.Role
import com.university.TabulatorProgram
import com.university.TabulatorSemester
import com.university.UserRole
import examinationproject.*
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import jxl.Cell

//import grails.plugin.jxl.Cell
import jxl.Sheet
import jxl.Workbook
import org.codehaus.groovy.grails.web.context.ServletContextHolder

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
    @Secured(["ROLE_EXAM_ADMIN"])
    def markMismatchReport = {


    }
    @Secured(["ROLE_EXAM_ADMIN"])
    def marksUpdation = {
        def programList = ProgramDetail.list(sort: 'courseCode')
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

    def semesterForMarksMisMatch = {
        println("?????" + params)
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
            eq('user', currentUser)
        }
        def programList = []
        progList.each {
            programList << ProgramDetail.findById(it.id)
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

    @Secured(["ROLE_EXAM_ADMIN"])
    def resultProcessing = {
        def programList = ProgramDetail.list(sort: 'courseCode')
        [programList: programList]
    }

    def finalResult = {
        def programList = ProgramDetail.list(sort: 'courseCode')
        [programList: programList]
    }

    def absenteeProcessing = {
        def examVenueList = ExaminationVenue.list(sort: 'name')
        [examVenueList: examVenueList]
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

    def xmlCreateData() {
        def result = marksEnteringService.createXMLFile(params)

    }
    def xmlParseData = {
        XmlParser parser = new XmlParser()
        def xmlData = parser.parse(new FileInputStream("web-app/subjectPassMarks/subjectMarksRule.xml"))
//        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//        Document document = documentBuilder.parse("web-app/subjectPassMarks/subjectMarksRule.xml");
//        Node mainNode = document.getElementsByTagName("rule").item(0);
//        NodeList nodes = mainNode.getChildNodes();
        return xmlData

    }

    def loadMarksType() {

        def returnMap = [:]
        def marksTypeObj = []
        def subMarksInst = SubjectMarksDetail.findAllBySubjectSession(SubjectSession.findBySubjectId(Subject.findById(params.subjectID)))
        if (subMarksInst.theory) {
            marksTypeObj << MarksType.findById(1)
        }
        if (subMarksInst.home) {
            marksTypeObj << MarksType.findById(2)
        }
        if (subMarksInst.practical) {
            marksTypeObj << MarksType.findById(3)
        }
        if (subMarksInst.project) {
            marksTypeObj << MarksType.findById(4)
        }
        returnMap.marksTypeList = marksTypeObj
        render returnMap as JSON
    }
    def getTabulatorSession = {
        def returnMap = [:]
        def programSession = ProgramSession.findAllByProgramDetailId(ProgramDetail.findById(Integer.parseInt(params.program)))
        returnMap.session = programSession
        render returnMap as JSON
    }
    def getTabulatorSemester = {
        def returnMap = [:]
        def tabSemesterList = []
        def currentUser = springSecurityService.currentUser
        def tabProgramInstList = TabulatorProgram.findAllByProgramAndUser(ProgramDetail.findById(Integer.parseInt(params.program)), currentUser)

        def programSession = ProgramSession.findAllByProgramDetailId(ProgramDetail.findById(Integer.parseInt(params.program)))
        tabProgramInstList.each {
            tabSemesterList << TabulatorSemester.findAllByTabulatorProgram(it).semesterId
        }

        returnMap.tabSemesterList = tabSemesterList
        returnMap.session = programSession

        render returnMap as JSON
    }
    def getSemesterForMarksUpdate = {
        def returnMap = [:]
        def semesterList = []
        def session = ProgramSession.findById(params.session).sessionOfProgram
        def programSession = ProgramSession.findByProgramDetailIdAndSessionOfProgram(ProgramDetail.findById(Integer.parseInt(params.program)), session)
        semesterList = Semester.findAllByProgramSession(programSession)

        returnMap.semesterList = semesterList
        render returnMap as JSON
    }

    def checkMarks = {
        def returnMap = [:]
        def programSession = ProgramSession.get(Integer.parseInt(params.session))
        def semester = Semester.findByProgramSessionAndId(programSession, Integer.parseInt(params.semester))
        def currentUser = springSecurityService.currentUser
        def role = UserRole.findAllByUser(currentUser).role
        def role_id
        role.each {
            def tabSemIns = TabulatorSemester.findBySemesterIdAndTabulatorProgram(semester, TabulatorProgram.findByProgramAndRoleAndUser(ProgramDetail.findById(params.program), it, currentUser))
            if (tabSemIns) {
                role_id = tabSemIns.tabulatorProgram.role.id

            }
        }
        def otherRoll_Id
        if (role_id == 9) {
            otherRoll_Id = 10
        } else {
            otherRoll_Id = 9
        }
        def stuIns = StudentMarks.findBySubjectIdAndSemesterNoAndRoleIdAndStudent(Subject.get(Integer.parseInt(params.subjectId)),
                Integer.parseInt(params.semester), Role.get(otherRoll_Id), Student.findByRollNo(params.rollNoId))
        if (stuIns) {
            if (params.marksType == '1') {
                if (stuIns.theoryMarks == params.marksValue) {
                    returnMap.status = true

                } else {
                    returnMap.status = false
                    returnMap.tabulator = Role.get(otherRoll_Id).authority
                }
            } else if (params.marksType == '2') {
                if (stuIns.practicalMarks == params.marksValue) {
                    returnMap.status = true
                } else {
                    returnMap.status = false
                    returnMap.tabulator = Role.get(otherRoll_Id).authority
                }
            } else if (params.marksType == '3') {
                if (stuIns.homeAssignmentMarks == params.marksValue) {
                    returnMap.status = true

                } else {
                    returnMap.status = false
                    returnMap.tabulator = Role.get(otherRoll_Id).authority
                }
            } else if (params.marksType == '4') {
                if (stuIns.project == params.marksValue) {
                    returnMap.status = true

                } else {
                    returnMap.status = false
                    returnMap.tabulator = Role.get(otherRoll_Id).authority
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

        render stuSessionList as JSON
    }

    def marksMissMatchData = {
        def groupSubList = [], groupSubjectCount = []
        def count = 0
        def programSessionIns = ProgramSession.findById(Long.parseLong(params.programId))
        def semesterIns = Semester.get(params.programTerm)
        def courseList = CourseSubject.findAllByProgramSessionAndSemester(programSessionIns, semesterIns).subjectSessionId
        def groupIns = ProgramGroup.findAllByProgramSessionAndSemester(programSessionIns, semesterIns)
        if (groupIns) {
            groupIns.each {
                groupSubList << ProgramGroupDetail.findAllByProgramGroupId(it).subjectSessionId
                groupSubjectCount << groupSubList[0].size()
                ++count
            }
        }
        def result = postExaminationService.getDetailForMisMatch(params, courseList, semesterIns, groupSubList)
        if (result) {
            def args = [template: "missMatchReportTemplate", model: [programName: programSessionIns.programDetailId.courseName, semester: semesterIns.semesterNo,
                    subjectList: courseList, marksType: result.marksType, headerList: result.headerList, finalList: result.finalList, groupIns: groupIns, groupSubList: groupSubList, groupSubjectCount: groupSubjectCount], filename: "Mis-Match Report.pdf"]
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

    def loadTabulatorMarks = {
        def finalList = postExaminationService.getTabulatorMarks(params)
        render finalList as JSON
    }
    def updateMisMatchMarks = {

        def result = postExaminationService.saveMisMatchMarks(params)
        render result as JSON
    }
    def generateResults = {
        def progSessionInst = ProgramSession.findById(Long.parseLong(params.sessionId))
        def semesterInst = Semester.findById(Long.parseLong(params.semesterId))
        def studentList = Student.findAllByProgramSessionAndSemester(progSessionInst, semesterInst.semesterNo)
        def xmlNodes = xmlParseData()

        def result = postExaminationService.generateProgramResults(params, xmlNodes)
        if (result.status) {

            def args = [template: "programResultSheet", model: [studPartialList: result.studentPartialPassList, studPassList: result.studentPassList,
                    courseName: progSessionInst.programDetailId.courseName, semester: semesterInst.semesterNo], filename: progSessionInst.programDetailId.courseName + "Result.pdf"]
            pdfRenderingService.render(args + [controller: this], response)
        } else {
            flash.message = result.msg
            redirect(controller: 'postExamination', action: 'resultProcessing')
        }
    }


    def generateFinalResult = {
        def progSessionInst = ProgramSession.findById(Long.parseLong(params.sessionId))
        def semesterInst = Semester.findById(Long.parseLong(params.semesterId))
//        def studentList = Student.findAllByProgramSessionAndSemester(progSessionInst, semesterInst.semesterNo)

        def result = postExaminationService.finalResult(params)
        if (result.status) {
            def args = [template: "finalMeritList", model: [studentMeritList: result.studentMeritList, totalStudentsAppeared: result.totalStudentsAppeared, courseName: progSessionInst.programDetailId.courseName, semester: semesterInst.semesterNo],
                    filename: progSessionInst.programDetailId.courseName + ".pdf"]
            pdfRenderingService.render(args + [controller: this], response)
        } else {
            flash.message = result.msg
            redirect(controller: 'postExamination', action: 'generateFinalResult')
        }

    }


    def loadVenueProgram() {
        def programList = ProgramExamVenue.findAllByExamVenue(ExaminationVenue.findById(Integer.parseInt(params.venue))).courseDetail
        render programList as JSON
    }

    def loadAbsenteeRollNo() {
        def programSessionIns = ProgramSession.findById(Long.parseLong(params.session))
        def semesterIns = Semester.findById(params.semester)
        def examVenue = ExaminationVenue.findById(Long.parseLong(params.examVenue))

        def studentList = Student.findAllByProgramSessionAndSemesterAndExaminationVenueAndAdmitCardGenerated(programSessionIns, semesterIns.semesterNo, examVenue, true)
        render studentList as JSON
    }

    def saveAbsentee() {
        def result = [:]
        println("===========================" + params)
        for (def i = 0; i < params.isselect_code.size(); i++) {

            def studentMarksIns
            def programSession = ProgramSession.get(Integer.parseInt(params.session))
            def semester = Semester.findByProgramSessionAndId(programSession, Integer.parseInt(params.semester))

            studentMarksIns = new StudentMarks()
            studentMarksIns.subjectId = Subject.get(Integer.parseInt(params.subjectId))
            studentMarksIns.semesterNo = Integer.parseInt(params.semester)
            if (Integer.parseInt(params.marksType) == 1) {
                studentMarksIns.theoryMarks = "Abs"
            } else if (Integer.parseInt(params.marksType) == 2) {
                studentMarksIns.practicalMarks = "Abs"
            } else if (Integer.parseInt(params.marksType) == 3) {
                studentMarksIns.homeAssignmentMarks = "Abs"
            } else if (Integer.parseInt(params.marksType) == 4) {
                studentMarksIns.project = "Abs"
            }
            studentMarksIns.totalMarks = params.marksValue
            studentMarksIns.student = Student.findByRollNo(params.isselect_code[i])
            if (studentMarksIns.save(flush: true, failOnError: true)) {
                result.status = true
            }
        }
        redirect(controller: 'postExamination', action: 'absenteeProcessing', params: [result: result])
    }

    def meritRegister = {
        def programList = ProgramDetail.list(sort: 'courseCode')
        [programList: programList]

    }
    def generateMeritRegister = {

        def webRootDir = servletContext.getRealPath("/")
        def userDir = new File(webRootDir, '/Report')
        userDir.mkdirs()
        println("userDir--" + userDir)
        def excelPath = servletContext.getRealPath("/") + 'Report' + System.getProperty('file.separator') + 'StudentMeritRegister.xls'
        def progSessionInst = ProgramSession.findById(Long.parseLong(params.sessionId))
        def semesterInst = Semester.findById(Long.parseLong(params.semesterId))
        def subjectList = CourseSubject.findAllByProgramSessionAndSemester(progSessionInst, semesterInst).subjectSessionId.subjectId
        def studentList = Student.findAllByProgramSessionAndSemesterAndAdmitCardGeneratedAndStatus(progSessionInst, semesterInst.semesterNo, true, Status.get(4))
        def xmlNodes = xmlParseData()
        def status = postExaminationService.studentMeritRegisterData(params, excelPath, studentList, xmlNodes, subjectList, semesterInst)

        if (status) {
            println("back in controller " + status)
            File myFile = new File(servletContext.getRealPath("/") + 'Report' + System.getProperty('file.separator') + 'StudentMeritRegister.xls')
            //response.setHeader "Content-disposition", "attachment; filename="+'Student_List_'+params.session+".xls"
            response.setHeader "Content-disposition", "attachment; filename=" + 'StudentMeritRegister' + ".xls"
            response.contentType = new MimetypesFileTypeMap().getContentType(myFile)
            response.outputStream << myFile.bytes
            response.outputStream.flush()
            myFile.delete()
        } else {
            flash.message = "No Roll Number Found"
            redirect(controller: 'postExamination', action: 'generateMeritRegister')
        }


    }

    def homeAssignmentExcelUpload() {
        def programList = ProgramDetail.list(sort: 'courseCode')
        [programList: programList]
    }

    def uploadHomeAssignmentMarks() {
        def fileToBeUploaded = request.getFile('homeAssignment')
        def result=postExaminationService.homeAssignmentExcelUpload(fileToBeUploaded, params)
        redirect(action: 'homeAssignmentExcelUpload')
    }


}

