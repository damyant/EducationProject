package com.university

import examinationproject.AdmissionFee
import examinationproject.Bank
import examinationproject.Branch
import examinationproject.City
import examinationproject.FeeDetails
import examinationproject.FeeSession
import examinationproject.FeeType
import examinationproject.MiscellaneousFee
import groovy.sql.Sql
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import examinationproject.PaymentMode
import examinationproject.ProgramDetail
import examinationproject.ProgramExamVenue
import examinationproject.ProgramType
import examinationproject.RollNoGenerationFixture
import examinationproject.Student
import examinationproject.Status
import examinationproject.StudyCenter
import examinationproject.Subject
import examinationproject.SubjectSession
import grails.converters.JSON

import grails.util.Holders
import postexamination.MarksType
import postexamination.SubjectMarksDetail

import javax.activation.MimetypesFileTypeMap
import grails.plugins.springsecurity.Secured

import java.text.DateFormat
import java.text.SimpleDateFormat

class AdminController {

    def adminInfoService
    def pdfRenderingService
    def studentRegistrationService
    def springSecurityService
    def feeDetailService
    def attendanceService
    def programFeeService

    @Secured(["ROLE_GENERATE_ROLL_NO"])
    def viewListGenerateRollNumber() {

        def studyCenterList = StudyCenter.list(sort: 'name')
        def programList = ProgramDetail.list(sort: 'courseCode')
        [studyCenterList: studyCenterList, programList: programList]
    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def viewApprovedStudents() {
        def studyCenterList = StudyCenter.list(sort: 'name')
        def programList = ProgramDetail.list(sort: 'courseCode')
        [studyCenterList: studyCenterList, programList: programList]
    }

    def getStudentList() {
        def responseMap = [:]
        def stuList = adminInfoService.provisionalStudentList(params)
        responseMap.status = "referenceNo"
        responseMap.label = params.pageType
        responseMap.stuList = stuList
        render responseMap as JSON

    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def generateRollNo() {
        String rollNumber = null
        def stuObj
        def stuList = []
        def responseMap = [:]
        def status
        if (params.pageType == "Approve RollNo") {
            status = studentRegistrationService.approvedStudents(params)
        } else {
//            println("Start    " + new Date())
            def studentIdList = params.studentList.split(",")
            rollNumber = studentRegistrationService.getUpdatedStudentRollNumber(params)
        }
        if (stuObj) {
            stuList = adminInfoService.provisionalStudentList(params)
        }
        responseMap.status = 'rollNo'
        responseMap.stuList = stuList
        render responseMap as JSON
        render stuList as JSON
    }

    @Secured(["ROLE_ADMIN", "ROLE_IDOL_USER", "ROLE_ACCOUNT"])
    def feeVoucher = {
        def feeType = []
        feeType = FeeType.list(sort: 'type')
        [feeType: feeType]
    }


    def examFeeVoucher = {
        def feeType = FeeType.list(sort: 'type')

        [feeType: feeType]
    }

    def getStudentByRollNo = {
        def studentMap = [:]
        def student = Student.findByRollNo(params.rollNo)
        if (student) {
            studentMap.student = student
            render studentMap as JSON
        } else {
            studentMap.noStudent = 'No Student Found'
            render studentMap as JSON
        }
    }

    def checkFeeByRollNo = {
        def response
        try {
            def student = Student.findByRollNo(params.rollNo)
            def program = student.programDetail[0]
            def feeType, admissionFee
            def programName = program.courseName
            boolean status

            Calendar cal = Calendar.getInstance();
            int year = cal.get(cal.YEAR);
            def sessionVal = year + 1
            sessionVal = year + '-' + sessionVal

            def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(student.programDetail[0].id), sessionVal)
            admissionFee = AdmissionFee.findAllByFeeSession(feeSessionObj)

            if (admissionFee)
                status = true
            else
                status = false
//            }
            response = [id: student.id, feeStatus: status, program: programName, feeType: feeType]
        } catch (Exception ex) {
            println("problem in checking the existence of roll number" + ex)
//            flash.message="Roll No not available in database."
//            redirect(action: "feeVoucher", params: [abc :"error"])
            response = [error: 'error']
        }

        render response as JSON
    }

    @Secured(["ROLE_ADMIN", "ROLE_IDOL_USER", "ROLE_ACCOUNT"])
    def generateFeeVoucher = {
        def response = [:]
        def student = Student.findByRollNo(params.rollNo)
        def program = student.programDetail
        def feeTypeId
        def feeType = FeeType.findById(params.feeType)
        def challanNo
        def lateFee = 0
        def programFeeAmount = 0
        Calendar cal = Calendar.getInstance();
        int year = cal.get(cal.YEAR);
        def sessionVal = year + 1
        sessionVal = year + '-' + sessionVal
        def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(student.programDetail[0].id), sessionVal)
        def feeDetailInst = FeeDetails.findByStudentAndSemesterValueAndFeeTypeAndIsApproved(student, Integer.parseInt(params.term), FeeType.findById(params.feeType), Status.findById(1))
        def programFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj, Integer.parseInt(params.term))
        try {
            def lateFeeDate = student.programDetail.lateFeeDate[0]
            def today = new Date()
            if (lateFeeDate != null) {
                if (today.compareTo(lateFeeDate) > 0) {
                    lateFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj, Integer.parseInt(params.term)).lateFeeAmount
                }
            }
//            feeType = null
            programFeeAmount = programFee.feeAmountAtIDOL + lateFee

        } catch (NullPointerException e) {
            flash.message = "Late Fee Date is not asigned! "
            redirect(controller: admin, action: feeVoucher)
        }
        if (feeDetailInst) {
            response.student = feeDetailInst.student
            response.lateFee = lateFee
            response.term = feeDetailInst.semesterValue
            response.challanNo = feeDetailInst.challanNo
            response.courseName = student.programDetail.courseName
            response.programFeeAmount = feeDetailInst.paidAmount
            response.feeType = feeType
        } else {
            def currentUser = springSecurityService.getCurrentUser()
            def studyCenterId = currentUser.studyCentreId
            if (studyCenterId != student.studyCentre.id) {
                def feeObj = FeeDetails.createCriteria()
                def studentsPaidTill = feeObj.list {
                    eq('feeType', FeeType.findById(params.feeType))
                    eq('student', student)
                    eq('isApproved', Status.findById(4))
                    maxResults(1)
                    order("semesterValue", "desc")
                }
                println("---------------"+studentsPaidTill)
                if(Integer.parseInt(params.term)>1){
                if (studentsPaidTill[0].semesterValue + 1 == Integer.parseInt(params.term)) {

                    challanNo = studentRegistrationService.getChallanNumber()
//            println(params.term)
                    student.migratingStudyCentre = studyCenterId
                    if (student.save(failOnError: true)) {
                        def feeInst = new FeeDetails()
                        feeInst.student = student
                        feeInst.feeType = FeeType.findById(params.feeType)
                        feeInst.isApproved = Status.findById(1)
                        feeInst.challanNo = challanNo
                        feeInst.paidAmount = programFeeAmount
                        feeInst.semesterValue = Integer.parseInt(params.term)
                        feeInst.save(failOnError: true)

                        feeInst.challanDate = new Date()

                    }
                    response.student = student
                    response.lateFee = lateFee
                    response.term = params.term
                    response.challanNo = challanNo
                    response.courseName = student.programDetail.courseName
                    response.programFeeAmount = programFeeAmount
                    response.feeType = feeType
                } else {
                    response.statusError = "Invalid Semester or Fees Already Paid."
                }
                }
                else{
                    challanNo = studentRegistrationService.getChallanNumber()
//            println(params.term)
                    student.migratingStudyCentre = studyCenterId
                    if (student.save(failOnError: true)) {
                        def feeInst = new FeeDetails()
                        feeInst.student = student
                        feeInst.feeType = FeeType.findById(params.feeType)
                        feeInst.isApproved = Status.findById(1)
                        feeInst.challanNo = challanNo
                        feeInst.paidAmount = programFeeAmount
                        feeInst.semesterValue = Integer.parseInt(params.term)
                        feeInst.save(failOnError: true)

                        feeInst.challanDate = new Date()

                    }
                    response.student = student
                    response.lateFee = lateFee
                    response.term = params.term
                    response.challanNo = challanNo
                    response.courseName = student.programDetail.courseName
                    response.programFeeAmount = programFeeAmount
                    response.feeType = feeType
                }
            }
        }

        render response as JSON

    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def assignExaminationDate = {
        def programList = ProgramDetail.list(sort: 'courseCode')
        [programList: programList]
    }
    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def assignExaminationVenue = {
        def programList = ProgramDetail.list(sort: 'courseCode')
        def obj = City.createCriteria()
        def examCenterList = obj.list {
            eq('isExamCentre', 1)
            order('cityName', 'asc')
        }
        [programList: programList, examinationCenterList: examCenterList]
    }

    def getSubjectList = {
        def subMap = [:]
        subMap = adminInfoService.subjectList(params)
        if (subMap.allSubjects.size() < 1) {
            subMap.noSubjects = true
            render subMap as JSON
        } else {
            render subMap as JSON
        }
    }

    def saveExamDate = {
        def checkStatus = [:]
//        println(params)
        def status = adminInfoService.saveExamDate(params)
        if (status.size() > 1) {
            checkStatus.saveFlag = true
        } else {
            checkStatus.saveFlag = false
        }
        render checkStatus as JSON
    }

    def saveExamVenue = {

        def status = adminInfoService.saveExamVenue(params)
        if (status) {
            render status
        } else {
            render "<h5>Error In Assigning Examination Venue</h5>"
        }

    }
    def generateStudentList = {
        def studList = adminInfoService.updateStudentList(params)
        render studList as JSON
    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def downloadAttendanceSheet = {
        if (params.programSession) {
            def webRootDir = servletContext.getRealPath("/")
            def userDir = new File(webRootDir, '/Attendance')
            userDir.mkdirs()
            def excelPath = servletContext.getRealPath("/") + 'Attendance' + System.getProperty('file.separator') + 'Output' + '.xls'
            try {
                def status = attendanceService.getStudentList(params, excelPath)
                if (status) {
                    File myFile = new File(servletContext.getRealPath("/") + 'Attendance' + System.getProperty('file.separator') + 'Output' + '.xls')
                    response.setHeader "Content-disposition", "attachment; filename=" + 'Output' + ".xls"
                    response.contentType = new MimetypesFileTypeMap().getContentType(myFile)
                    response.outputStream << myFile.bytes
                    response.outputStream.flush()
                    myFile.delete()
                    redirect(action: 'downloadAttendanceSheet')
                } else {
                    flash.message = "${message(code: 'student.not.found.message')}"
                    redirect(action: 'downloadAttendanceSheet')
                }
            }
            catch (Exception e) {
//                println("This is the exception "+ e)
                flash.message = "${message(code: 'student.exception.found.message')}"
                redirect(action: 'downloadAttendanceSheet')
            }
        } else {
//            println("there is no parameters")
        }

    }

    def uploadInternalMarks = {
        def studyCentreList = StudyCenter.list(sort: 'name')
        def programList = ProgramDetail.list(sort: 'courseCode')
        [programList: programList, studyCentreList: studyCentreList]
    }
    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def approvePayInSlip = {
        def bankList = Bank.list(sort: 'bankName');
        def feeTypeList = FeeType.list(sort: 'type');
        [bankList: bankList, feeTypeList: feeTypeList]
    }
    def getBranchList = {
        def list = Bank.findAllById(Integer.parseInt(params.bank));
        render list.branch[0] as JSON
    }


    def studyCentreFeeApproval = {
        def studyCenterList = StudyCenter.list(sort: 'name');
        def programList = ProgramDetail.list(sort: 'courseCode')
        [studyCenterList: studyCenterList, programList: programList]
    }
    def getChallanDetailsforStudent = {
        def resultMap = [:]
        resultMap = feeDetailService.studentDetailByChallanNumber(params)
        render resultMap as JSON
    }
    def saveApprovePayInSlip = {
        def returnMap = [:]
        Boolean result = adminInfoService.savePayInSlip(params);
        returnMap.flag = result
//        println(result);

        render returnMap as JSON
    }
    def getFeeAmount = {
        def resultMap = [:]

        def lateFee = 0
        def payableFee = 0

        Calendar cal = Calendar.getInstance();
        int year = cal.get(cal.YEAR);
        def sessionVal = year + 1
        sessionVal = year + '-' + sessionVal
        try {
            def programIns = ProgramDetail.findById(Integer.parseInt(params.program))
            def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(programIns, sessionVal)
            def lateFeeDate = programIns.lateFeeDate
            if(feeSessionObj==null){
                def feeObj=FeeSession.createCriteria()
            feeSessionObj=feeObj.list{
                eq('programDetailId',programIns)
                order('id','desc')
                maxResults(1)
            }
        }
            def today = new Date()
            if (lateFeeDate != null) {
                if (today.compareTo(lateFeeDate) > 0) {
                    lateFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj, 1).lateFeeAmount
                }
            }
            def feeAmount = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj, 1);
            payableFee = feeAmount.feeAmountAtIDOL + lateFee
        }
        catch (NullPointerException e) {
            payableFee = 0
        }
        resultMap.feeAmount = payableFee
        render resultMap as JSON
    }

    def searchListStudentByChallanNo() {
        def returnMap = [:]
        def courseNameList = [], courseFee = [], stuList = [], semester = []
        def feeDetailsInstance = FeeDetails.findAllByChallanNoAndIsApprovedAndFeeType(params.challanNo, Status.findById(1),FeeType.findById(3))
        feeDetailsInstance.each {
            if (it.student.rollNo != null) {
                stuList << Student.findById(it.student.id)
                courseNameList << it.student.programDetail.courseName
                courseFee << it.paidAmount
                semester << it.semesterValue
            }
        }
        returnMap.stuList = stuList
        returnMap.semester = semester
        returnMap.courseNameList = courseNameList
        returnMap.courseFee = courseFee
        render returnMap as JSON
    }

    def searchMiscFeeListByChallanNo() {
        def returnMap = [:]
        def courseNameList = [], courseFee = [], stuList = [], feeType = []
//        println(params.challanNo)
        def miscFeeChallanList = FeeDetails.findAllByChallanNoAndIsApprovedAndFeeTypeNotEqual(params.challanNo, Status.findById(1),FeeType.findById(3))
//       println(miscFeeChallanList)
        miscFeeChallanList.each {
            int year = it.student.registrationYear
            def sessionVal = year + 1
            feeType << it.feeType.type
            sessionVal = year + '-' + sessionVal
            def feeSessionObj = FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.get(it.student.programDetail[0].id), sessionVal.toString())
            stuList << it.student
            courseNameList << it.student.programDetail[0].courseName
            courseFee << MiscellaneousFee.findByFeeSessionAndFeeType(feeSessionObj, it.feeType).amount
        }
        returnMap.stuList = stuList
        returnMap.feeType = feeType
        returnMap.courseNameList = courseNameList
        returnMap.courseFee = courseFee
        render returnMap as JSON
    }
    def getCourseCodeLength(){
        def returnMap = [:]
        def progmInst=ProgramDetail.findById(Long.parseLong(params.program))
        if(progmInst.courseCode.length()==2){
            returnMap.status=true
        }
        else{
            returnMap.status=false
        }
        render returnMap as JSON
    }
    def searchByChallanNo() {
        def returnMap = [:]
//        println("???????/" + params)
        returnMap = feeDetailService.studentDetailByChallanNumber(params)
        render returnMap as JSON
    }

    def approveFeeForStudents = {
        Boolean status = false
        def feeDetailList = FeeDetails.findAllByChallanNo(params.payInSlipNo)
        feeDetailList.each {
            it.isApproved = Status.findById(4)
            if (it.save(flush: true)) {
                status = true
                def student = Student.findById(it.student.id)
                if (student.migratingStudyCentre != 0) {
                    Set<StudyCenter> studyCentre = StudyCenter.findAllById(student.migratingStudyCentre)
                    student.studyCentre = studyCentre
                    student.migratingStudyCentre = 0
                }
                if (it.semesterValue == 1 && it.feeType.id == 3) {
                    student.status = Status.findById(4)
                }
                if (student.save(flush: true)) {
                    status = true
                } else {
                    status = false
                }
            }
        }
        if (status) {
            flash.message = "Approved Successfully"
            redirect(action: "approvePayInSlip")
        }

    }

    //ADDED BY DIGVIJAY ON 19 May 2014
    @Secured(["ROLE_ADMIN"])
    def addCourses = {
        def updateMode=false
        def courseList,courseSession
        def programTypeList = ProgramType.list()
        if(params.id){
//         courseList = Subject.findById(params.id)
         courseSession=SubjectSession.findById(params.id)
          courseList=courseSession.subjectId
//            println("<<<<"+courseSession.)
            updateMode=true
        }
        def marksTypeList=MarksType.list()
        def subjectSessions = programFeeService.getProgramSessions(params)
        def subjectMarksList=SubjectMarksDetail.findAllBySubjectSession(courseSession)
        def marksMap=[:]
        subjectMarksList.each {
            marksMap["key"+it.marksTypeId.id]=it

        }

        [programTypeList: programTypeList, courseList: courseList,courseSession:courseSession,updateMode:updateMode,
                marksTypeList:marksTypeList,marksMap:marksMap,subjectSessions:subjectSessions]
    }
    @Secured(["ROLE_ADMIN"])
    def listOfCourses = {
        def programTypeList = ProgramType.list()
        [programTypeList: programTypeList]
    }
    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def assignRollNoGenerationDate = {
        def rollDateInst = RollNoGenerationFixture.findById(1)
        [rollDateInst: rollDateInst]
    }
    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def saveRollNoGenerationDate = {
        def status = adminInfoService.saveRollNoGenDate(params)
        if (status) {
            flash.message = "Roll Number Generation Date Assigned Successfully"
        } else {
            flash.message = "Unable to Assign Date Successfully"
        }
        redirect(action: "assignRollNoGenerationDate")

    }

    def generateRollIsAllow = {
        def returnMap = [:]
        Boolean status = false

        def genRollNoIns = RollNoGenerationFixture.findById(1)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date min = genRollNoIns.startD
            Date max = genRollNoIns.endD
            Date cdate = new Date()
            if (cdate.compareTo(min) >= 0 && cdate.compareTo(max) <= 0) {
                status = true
            }
        }
        catch (NullPointerException e) {
            flash.message = "Please Assign Roll No Generation Date."
            redirect(action: "viewProvisionalStudents")
        }
        returnMap.status = status
        render returnMap as JSON
    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def assignLateFeeDate = {

        def programList = []
        def programs = ProgramDetail.list(sort: 'courseCode')
        programs.each {

            if (it.lateFeeDate == null) {
                programList.add(it)
            }
        }

        def programCategory = ProgramType.list(sort: 'type')
        [programList: programList, programCategory: programCategory]

    }
    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def removeLateFeeDate = {

        def programList = []
        def programs = ProgramDetail.list(sort: 'courseCode')
        programs.each {

            if (it.lateFeeDate == null) {
                programList.add(it)
            }
        }

        def programCategory = ProgramType.list(sort: 'type')
        [programList: programList, programCategory: programCategory]

    }
    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def deleteLateFeeDate = {
//        println(params)
        def status = adminInfoService.removeDateLateFee(params)
        if (status) {
            flash.message = "Late Fee Date Removed Successfully"
        } else {
            flash.message = "Unable Remove Late Fee Date"
        }
        redirect(action: "removeLateFeeDate")
    }

    def loadProgram = {
//        println("params" + params)
        def programList = [], dateList = []
        def programType = ProgramType.findById(Long.parseLong(params.type))
        def programs = ProgramDetail.findAllByProgramType(programType)
        programs.each {
            if (it.lateFeeDate) {
                dateList << it.lateFeeDate.getDateString()
            } else {
                dateList << ""
            }
        }

        def response = [programList: programs, dateList: dateList]
        render response as JSON
    }
    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def saveLateFeeDate = {

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy")
        def date = df.parse(params.lateFeeDate)
        def programList = []
        programList.addAll(params.programs)
        programList.each {
//            println("############################3 "+it )
            def program = ProgramDetail.findById(Integer.parseInt(it))
            program.lateFeeDate = date
            if (program.save(flush: true, failOnError: true)) {
                flash.message = "Succesfully Assigned Late Fee Date."
            }
        }
        redirect(action: "assignLateFeeDate")
    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def studyMaterial = {

    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def getStudentForStudyMaterial() {
//        println("???????????"+params)
        def returnMap = [:]
        returnMap = adminInfoService.studentForStudyMaterial(params)
//        println('this is the returning map '+returnMap)
        render returnMap as JSON
    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def saveStudyMaterial() {
//        println("inn"+params)
        def returnMap = [:]
        def resultMap = adminInfoService.saveStudentForStudyMaterial(params)
//        println("********"+resultMap)
        if (resultMap) {
            returnMap.status = "true"
        } else {
            returnMap.status = "false"
        }
        render returnMap as JSON
    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def assignAdmissionPeriod() {
        def programList = []
        def programs = ProgramDetail.list(sort: 'courseCode')
        programs.each {

            if (it.lateFeeDate == null) {
                programList.add(it)
            }
        }
        def sessionList = Student.createCriteria().list {
            projections {
                distinct("registrationYear")
            }
        }

        def year = new Date().format("yyyy")
        if (sessionList.size() == 0) {
            sessionList << Integer.parseInt(year)
        }
        sessionList << Integer.parseInt(year) + 1
        def programCategory = ProgramType.list(sort: 'type')
        [programList: programList, programCategory: programCategory, sessionList: sessionList]
    }

    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def saveAdmissionFeePeriod() {
        def status = adminInfoService.saveAdmissionPeriod(params)
        if (status) {
            flash.message = "Admission Period Saved Successfully"
        } else {
            flash.message = "Unable Save Successfully. "
        }
        redirect(action: "assignAdmissionPeriod")
    }

    def getAdmissionDate = {
        def returnMap = [:]
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

        if (params.programCode) {
            def progmInst = ProgramDetail.findById(params.programCode)
            if (progmInst.admissionYear) {
                returnMap.year = progmInst.admissionYear
                returnMap.startDate = sdf.format(progmInst.startAdmission_D)
                returnMap.endDate = sdf.format(progmInst.endAdmission_D)
            } else {
                returnMap.year = ""
                returnMap.startDate = ""
                returnMap.endDate = ""
            }
            render returnMap as JSON
        }
    }
    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT","ROLE_IDOL_USER","ROLE_STUDY_CENTRE"])
    def individualStudentUpdate = {
//        def grailsApplication = Holders.getGrailsApplication()
//        def rootImageFolder =  grailsApplication.config.my.global.variable;
//        grailsApplication.config.my.global.variable=grailsApplication.config.my.global.variable+1
//        [rootImageFolder:rootImageFolder]
    }
    def loadPayInSlipDetail = {
        def returnMap = [:]
        def status = false
        def payMode = PaymentMode.findById(Integer.parseInt(params.pMode))
        def studentInst
        if (payMode == PaymentMode.findById(1)) {
            studentInst = FeeDetails.findByChallanNo(params.challanNo).student
        }
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy")
            def bank=[]
            def branch=Branch.findAllByBranchLocation("Gauhati University")
            branch.each {
                bank<<it.bank
            }
//            println(bank)

            returnMap.refNo = studentInst.challanNo
            returnMap.payDate = df.format(new Date())
            returnMap.bank = bank
            returnMap.branch = Branch.findByBranchLocation('Gauhati University').id
            returnMap.branchName = Branch.findByBranchLocation('Gauhati University').branchLocation
//        }
        render returnMap as JSON
    }
    @Secured("ROLE_ADMIN")
    def deleteCourse = {

        try {
            def status = adminInfoService.deleteTheCourse(params)

            if (status) {
                flash.message = "Course Removed Successfully"

            }
        }
        catch (Exception e) {
            flash.message = "Unable To Remove Course"
        }
        redirect( controller: "admin" ,action: "listOfCourses")
    }
    @Secured(["ROLE_ADMIN", "ROLE_ACCOUNT"])
    def loadSubject = {
        def returnMap=[:]
        def subSessionList=[],subjectNameList=[]
        def programType = ProgramType.findById(Long.parseLong(params.type))
        def subjectList = Subject.findAllByProgramTypeId(programType)

        subjectList.each{
         subSessionList<< SubjectSession.findAllBySubjectId(it)
         subjectNameList<<SubjectSession.findAllBySubjectId(it).subjectId
        }

        returnMap.subSession=subSessionList
        returnMap.subject=subjectNameList
        def response = [subjectList: subSessionList]
            println(subjectNameList)
        render returnMap as JSON
    }

    @Secured(["ROLE_IDOL_USER", "ROLE_ADMIN", "ROLE_ACCOUNT"])
    def searchStudentName = {
        def sessionList = Student.createCriteria().list {
            projections {
                distinct("registrationYear")
            }
        }
//        println(sessionList)
        [sessionList: sessionList]
    }
    @Secured(["ROLE_IDOL_USER", "ROLE_ADMIN", "ROLE_ACCOUNT"])
    def searchStudentList = {
        def returnMap = [:]
        def studyOfFName = []
        def courseOfFName = []
        def year = Integer.parseInt(params.session)
        def searchobj = Student.createCriteria()
        def searchList = searchobj.list {
            or {
                like('firstName', '%' + params.student + '%')
                and {
                    like('middleName', '%' + params.student + '%')
                }
                and {
                    like('lastName', '%' + params.student + '%')
                }
            }
            and {
                eq('registrationYear', year)
            }
            order('rollNo')
        }.unique()
        searchList.each {
            studyOfFName << it.studyCentre[0].name
            courseOfFName << it.programDetail[0].courseName
        }

        returnMap.studentListByFName = searchList
        returnMap.studyOfFName = studyOfFName
        returnMap.courseOfFName = courseOfFName

        render returnMap as JSON
    }
    @Secured(["ROLE_IDOL_USER", "ROLE_ADMIN", "ROLE_ACCOUNT"])
    def generateCustomChallan = {

    }
    def loadTermFromRollNo = {
        def returnMap = [:]
        def stuInst = Student.findByRollNo(params.data)

        def catgId = stuInst.programDetail.programType.id
        def term
        if (catgId == '1') {
            term = stuInst.programDetail.noOfAcademicYears
        } else {
            term = stuInst.programDetail.noOfTerms
        }
        returnMap.term = term
        render returnMap as JSON
    }
    def loadTermAndVenueFromRollNo = {
        def returnMap = [:]
        def stuInst = Student.findByRollNo(params.data)
        def examVenue = ProgramExamVenue.findAllByCourseDetailAndExamCenter(stuInst.programDetail, stuInst.city)
        def catgId = stuInst.programDetail.programType.id
        def term
//        if (catgId == '1') {
//            term = stuInst.programDetail.noOfAcademicYears
//        } else {
//            term = stuInst.programDetail.noOfTerms
//        }
        term = stuInst.programDetail.noOfTerms
        returnMap.term = term
        returnMap.examVenue = examVenue.examVenue
        render returnMap as JSON
    }

    def getStudentRollNo(){
        def returnMap = [:]
        returnMap.roll = Student.findByRollNo(params.data).rollNo

        render returnMap as JSON
    }
//    def dbdump(){
//        def initialSize = 4096
//        def outStream = new ByteArrayOutputStream(initialSize)
//        def errStream = new ByteArrayOutputStream(initialSize)
//        def proc = "ls".execute()
//        proc.consumeProcessOutput(outStream, errStream)
//        proc.waitFor()
//        println 'out:\n' + outStream
//        println 'err:\n' + errStream
//    }

}
