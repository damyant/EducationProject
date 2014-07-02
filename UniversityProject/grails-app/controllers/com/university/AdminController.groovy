package com.university

import examinationproject.AdmissionFee
import examinationproject.Bank
import examinationproject.Branch
import examinationproject.City
import examinationproject.FeeDetails
import examinationproject.FeeSession
import examinationproject.FeeType
import examinationproject.MiscellaneousFee

import examinationproject.PaymentMode
import examinationproject.ProgramDetail
import examinationproject.ProgramType
import examinationproject.RollNoGenerationFixture
import examinationproject.Student
import examinationproject.Status
import examinationproject.StudyCenter
import examinationproject.Subject
import grails.converters.JSON

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

    @Secured(["ROLE_GENERATE_ROLL_NO"])
    def viewListGenerateRollNumber() {

        def studyCenterList = StudyCenter.list(sort: 'name')
        def programList = ProgramDetail.list(sort: 'courseCode')
        [studyCenterList: studyCenterList, programList: programList]
    }

    @Secured("ROLE_ADMIN")
    def viewApprovedStudents() {
        def studyCenterList = StudyCenter.list(sort: 'name')
        def programList = ProgramDetail.list(sort: 'courseName')
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

    @Secured("ROLE_ADMIN")
    def generateRollNo() {
        String rollNumber = null
        def stuObj
        def stuList = []
        def responseMap = [:]
        def status
        if (params.pageType == "Approve RollNo") {
            status = studentRegistrationService.approvedStudents(params)
        } else {
            println("Start    " + new Date())
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

    @Secured(["ROLE_ADMIN", "ROLE_IDOL_USER"])
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
            def feeType,admissionFee
            def programName = program.courseName
            boolean status

                Calendar cal = Calendar.getInstance();
                int year = cal.get(cal.YEAR);
                def sessionVal= year+1
                sessionVal= year+'-'+sessionVal

                def feeSessionObj=FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(student.programDetail[0].id),sessionVal)
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

    @Secured(["ROLE_ADMIN", "ROLE_IDOL_USER"])
    def generateFeeVoucher = {
        def response=[:]
        def student = Student.findByRollNo(params.rollNo)
        def program = student.programDetail
        def feeTypeId
        def feeType = FeeType.findById(params.feeType).type
//        println("FFFFFFFFFFFFFFFFFFFFFF"+feeType)
        def challanNo
        def lateFee = 0
        def programFeeAmount = 0

        Calendar cal = Calendar.getInstance();
        int year = cal.get(cal.YEAR);
        def sessionVal= year+1
        sessionVal= year+'-'+sessionVal

        def feeSessionObj=FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(student.programDetail[0].id),sessionVal)
        def currentUser = springSecurityService.getCurrentUser()
        def studyCenterId = currentUser.studyCentreId
        if(studyCenterId!=student.studyCentre.id){
            def feeObj = FeeDetails.createCriteria()
            def studentsPaidTill= feeObj.list {
                eq('feeType', FeeType.findById(params.feeType))
                eq('student', student)
                eq('isApproved', Status.findById(4))
                maxResults(1)
                order("semesterValue", "desc")
            }
           println(">>>>>>>>>>>>studentsPaidTill"+studentsPaidTill[0].semesterValue)
            if(studentsPaidTill[0].semesterValue+1==Integer.parseInt(params.term)){
        def programFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj,Integer.parseInt(params.term))
            try{
            def lateFeeDate=student.programDetail.lateFeeDate[0]
            def today=new Date()
                if(lateFeeDate!=null) {
                    if (today.compareTo(lateFeeDate) > 0) {
                        lateFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj,Integer.parseInt(params.term)).lateFeeAmount
                    }
            }
//            feeType = null
            programFeeAmount = programFee.feeAmountAtIDOL + lateFee

        } catch (NullPointerException e) {
            flash.message = "Late Fee Date is not asigned! "
            redirect(controller: admin, action: feeVoucher)
        }
            challanNo=studentRegistrationService.getChallanNumber()
            println(params.term)
            student.migratingStudyCentre=studyCenterId
            if(student.save(failOnError: true)){
                def feeInst=new FeeDetails()
                feeInst.student=student
                feeInst.feeType=FeeType.findById(params.feeType)
                feeInst.isApproved=Status.findById(1)
                feeInst.challanNo=challanNo
                feeInst.paidAmount=programFeeAmount
                feeInst.semesterValue=Integer.parseInt(params.term)
                feeInst.save(failOnError: true)

                feeInst.challanDate=new Date()

            }
                response.student=student
                response.lateFee=lateFee
                response.term=params.term
                response.challanNo=challanNo
                response.courseName=student.programDetail.courseName
                response.programFeeAmount=programFeeAmount
                response.feeType=feeType
            }
            else{
                response.statusError="Invalid Semester or Fees Already Paid."
            }
        }

        render response as JSON

    }

    @Secured("ROLE_ADMIN")
    def assignExaminationDate = {
        def programList = ProgramDetail.list(sort: 'courseCode')
        [programList: programList]
    }
    @Secured("ROLE_ADMIN")
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

    @Secured(["ROLE_ADMIN"])
    def downloadAttendanceSheet = {
        if (params.programSession) {
            def webRootDir = servletContext.getRealPath("/")
            def userDir = new File(webRootDir, '/Attendance')
            userDir.mkdirs()
            def excelPath = servletContext.getRealPath("/") + 'Attendance' + System.getProperty('file.separator') + 'Output' + '.xls'
            try {
                def status = attendanceService.getStudentList(params, excelPath)
//            println("hello kuldeep u r back in controller " + status)
                if (status) {
//                println("hello kuldeep u r back in controller " + status)
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
        def programList = ProgramDetail.list(sort: 'courseName')
        [programList: programList, studyCentreList: studyCentreList]
    }
    @Secured(["ROLE_ADMIN"])
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
        def programList = ProgramDetail.list(sort: 'courseName')
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

        def lateFee=0
        def payableFee=0

        Calendar cal = Calendar.getInstance();
        int year = cal.get(cal.YEAR);
        def sessionVal= year+1
        sessionVal= year+'-'+sessionVal
        try {
            def programIns = ProgramDetail.findById(Integer.parseInt(params.program))
            def feeSessionObj=FeeSession.findByProgramDetailIdAndSessionOfFee(programIns,sessionVal)
            def lateFeeDate = programIns.lateFeeDate
            def today = new Date()
            if (lateFeeDate != null) {
                if (today.compareTo(lateFeeDate) > 0) {
                    lateFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj,1).lateFeeAmount
                }
            }
            def feeAmount = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj,1);
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
        def courseNameList = [], courseFee = [],stuList=[],semester=[]
        def feeDetailsInstance = FeeDetails.findAllByChallanNoAndIsApproved(params.challanNo,Status.findById(1))
        feeDetailsInstance.each{
            if(it.student.rollNo!=null){
                stuList<<Student.findById(it.student.id)
                courseNameList<<it.student.programDetail.courseName
                courseFee<<it.paidAmount
                semester<<it.semesterValue
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
        def courseNameList = [], courseFee = [], stuList = [],feeType=[]
//        println(params.challanNo)
        def miscFeeChallanList = FeeDetails.findAllByChallanNo(params.challanNo)
        miscFeeChallanList.each {
            int year=it.student.registrationYear
            def sessionVal= year+1
            feeType<<it.feeType.type
            sessionVal= year+'-'+sessionVal
            def feeSessionObj=FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.get(it.student.programDetail[0].id),sessionVal.toString())
            stuList<<it.student
            courseNameList<<it.student.programDetail[0].courseName
            courseFee<<MiscellaneousFee.findByFeeSessionAndFeeType(feeSessionObj,it.feeType).amount
        }
        returnMap.stuList = stuList
        returnMap.feeType = feeType
        returnMap.courseNameList = courseNameList
        returnMap.courseFee = courseFee
        render returnMap as JSON
    }

    def searchByChallanNo() {
        def returnMap = [:]
//        println("???????/" + params)
        returnMap = feeDetailService.studentDetailByChallanNumber(params)
        render returnMap as JSON
    }

    def approveFeeForStudents = {
        Boolean status=false
        def feeDetailList=FeeDetails.findAllByChallanNo(params.payInSlipNo)
        feeDetailList.each {
            it.isApproved=Status.findById(4)
            if(it.save(flush: true)){
                status=true
                def student = Student.findById(it.student.id)
                if(student.migratingStudyCentre!=0){
                    Set<StudyCenter> studyCentre = StudyCenter.findAllById(student.migratingStudyCentre)
                    student.studyCentre=studyCentre
                    student.migratingStudyCentre=0
                }
                if(it.semesterValue==1 && it.feeType.id==3){
                    student.status=Status.findById(4)
                }
                if(student.save(flush: true)){
                    status=true
                }
                else{
                    status=false
                }
            }
        }
        if(status){
            flash.message = "Approved Successfully"
            redirect(action: "approvePayInSlip")
        }

    }

    //ADDED BY DIGVIJAY ON 19 May 2014
    @Secured(["ROLE_ADMIN"])
    def addCourses = {
        def programTypeList = ProgramType.list()
        def courseList = Subject.findById(params.id)
//        println("AdminController-->addCourses"+programTypeList);
        [programTypeList: programTypeList, courseList: courseList]
    }

    def listOfCourses = {
        def programTypeList = ProgramType.list()
        [programTypeList: programTypeList]
    }
    @Secured(["ROLE_ADMIN"])
    def assignRollNoGenerationDate = {
        def rollDateInst = RollNoGenerationFixture.findById(1)
        [rollDateInst: rollDateInst]
    }
    @Secured("ROLE_ADMIN")
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

    @Secured(["ROLE_ADMIN"])
    def assignLateFeeDate = {

        def programList = []
        def programs = ProgramDetail.list(sort: 'courseName')
        programs.each {

            if (it.lateFeeDate == null) {
                programList.add(it)
            }
        }

        def programCategory = ProgramType.list(sort: 'type')
        [programList: programList, programCategory: programCategory]

    }
    @Secured("ROLE_ADMIN")
    def removeLateFeeDate = {

        def programList = []
        def programs = ProgramDetail.list(sort: 'courseName')
        programs.each {

            if (it.lateFeeDate == null) {
                programList.add(it)
            }
        }

        def programCategory = ProgramType.list(sort: 'type')
        [programList: programList, programCategory: programCategory]

    }
    @Secured("ROLE_ADMIN")
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
    @Secured("ROLE_ADMIN")
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

    @Secured(["ROLE_ADMIN"])
    def studyMaterial = {

    }
    @Secured("ROLE_ADMIN")
    def getStudentForStudyMaterial() {
//        println("???????????"+params)
        def returnMap = [:]
        returnMap = adminInfoService.studentForStudyMaterial(params)
//        println('this is the returning map '+returnMap)
        render returnMap as JSON
    }
    @Secured("ROLE_ADMIN")
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

    @Secured("ROLE_ADMIN")
    def assignAdmissionPeriod() {
        def programList = []
        def programs = ProgramDetail.list(sort: 'courseName')
        programs.each {

            if (it.lateFeeDate == null) {
                programList.add(it)
            }
        }

        def programCategory = ProgramType.list(sort: 'type')
        [programList: programList, programCategory: programCategory]
    }
    @Secured("ROLE_ADMIN")
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
            returnMap.startDate = sdf.format(progmInst.startAdmission_D)
            returnMap.endDate = sdf.format(progmInst.endAdmission_D)
            render returnMap as JSON
        }
    }
    @Secured(["ROLE_IDOL_USER","ROLE_ADMIN"])
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
        if (payMode.paymentModeName == 'Pay In Slip') {
            studentInst = FeeDetails.findByChallanNo(params.challanNo).student
            if (StudyCenter.findById(studentInst.studyCentre[0].id).centerCode == '11111'||StudyCenter.findById(studentInst.migratingStudyCentre).centerCode == '11111') {
                status = true
            }
        }
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy")
        if (status) {
            returnMap.admissionDate = df.format(studentInst.admissionDate)
            returnMap.refNo = studentInst.challanNo
            returnMap.bank = Bank.findByBankName('State Bank Of India').id
            returnMap.bankName = Bank.findByBankName('State Bank Of India').bankName
            returnMap.branch = Branch.findByBranchLocation('Gauhati University').id
            returnMap.branchName = Branch.findByBranchLocation('Gauhati University').branchLocation
        }
        render returnMap as JSON
    }
    @Secured("ROLE_ADMIN")
    def deleteCourse = {
//        println("dsdsdsds"+params)
        try {
            def status = adminInfoService.deleteTheCourse(params)
            println(status)
            if (status) {
                flash.message = "Course Removed Successfully"

            }
        }
        catch (Exception e) {
            flash.message = "Unable To Remove Course"
        }
        redirect(action: "listOfCourse")
    }
    @Secured("ROLE_ADMIN")
    def loadSubject = {
        def programType = ProgramType.findById(Long.parseLong(params.type))
        def subjectList = Subject.findAllByProgramTypeId(programType)
        def response = [subjectList: subjectList]
//        println(response.programList[0].courseName)
        render response as JSON
    }

    @Secured(["ROLE_IDOL_USER","ROLE_ADMIN"])
    def searchStudentName = {
        def sessionList = Student.createCriteria().list {
            projections {
                distinct("registrationYear")
            }
        }
        println(sessionList)
        [sessionList: sessionList]
    }
    @Secured(["ROLE_IDOL_USER","ROLE_ADMIN"])
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
    @Secured(["ROLE_IDOL_USER","ROLE_ADMIN"])
    def generateCustomChallan={

    }
    def loadTermFromRollNo={
        def returnMap=[:]
        def stuInst=Student.findByRollNo(params.data)
        println(stuInst.programDetail)
        def catgId=stuInst.programDetail.programType.id
        def term
        if(catgId=='1'){
            term=stuInst.programDetail.noOfAcademicYears
        }
        else{
            term=stuInst.programDetail.noOfTerms
        }
        returnMap.term=term
        render returnMap as JSON
    }

}
