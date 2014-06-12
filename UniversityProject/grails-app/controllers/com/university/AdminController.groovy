package com.university

import examinationproject.AdmissionFee
import examinationproject.Bank
import examinationproject.Branch
import examinationproject.City

import examinationproject.FeeType
import examinationproject.MiscellaneousFee
import examinationproject.MiscellaneousFeeChallan
import examinationproject.PaymentMode
import examinationproject.ProgramDetail
import examinationproject.ProgramType
import examinationproject.RollNoGenerationFixture
import examinationproject.Student
import examinationproject.Status
import examinationproject.StudentController
import examinationproject.StudyCenter
import grails.converters.JSON
import grails.util.Holders

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
            println("Start    "+new Date())
            def studentIdList = params.studentList.split(",")
//            studentIdList.each { i ->
//                rollNumber = studentRegistrationService.getStudentRollNumber(params)
//                stuObj = Student.findById(i)
//                stuObj.rollNo = rollNumber
//                stuObj.status = Status.findById(Long.parseLong("3"))
//                stuObj.save(flush: true, failOnError: true)
//            }
            rollNumber = studentRegistrationService.getUpdatedStudentRollNumber(params)

            println("End    "+new Date())
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

    def getStudentByRollNo ={
        def studentMap=[:]
          def student = Student.findByRollNo(params.rollNo)
          if(student){
             studentMap.student = student
              render studentMap as JSON
          }
        else{
              studentMap.noStudent= 'No Student Found'
              render studentMap as JSON
          }
    }

    def checkFeeByRollNo = {
        def response
        try {
            def student = Student.findByRollNo(params.rollNo)
            def program = student.programDetail[0]
            def feeType
            def programName = program.courseName
            boolean status
            def admissionFee = AdmissionFee.findByProgramDetail(program)
            def mFee

            if (Integer.parseInt(params.feeType) > 0) {

                feeType = FeeType.findById(params.feeType)
                mFee = MiscellaneousFee.findByFeeTypeAndProgramDetailAndProgramSession(feeType, program, student.programSession)
                if (mFee)
                    status = true
                else
                    status = false
            } else {
                if (admissionFee)
                    status = true
                else
                    status = false
            }
            response = [id: student.id, feeStatus: status, program: programName, feeType: feeType]
        } catch (Exception ex) {
            println("problem in checking the existence of roll number" + ex)
//            flash.message="Roll No not available in database."
//            redirect(action: "feeVoucher", params: [abc :"error"])
              response = [error:'error']
        }

        render response as JSON
    }

    @Secured(["ROLE_ADMIN", "ROLE_IDOL_USER"])
    def generateFeeVoucher = {
        println(">>>>>>>>????????>>" + params)
        def student = Student.findByRollNo(params.rollNo)
        println("program"+student.programDetail)
        def program = student.programDetail
        def feeTypeId
        def feeType = null
        def args
        def lateFee=0
        def programFeeAmount = 0
        def programFee = AdmissionFee.findByProgramDetailAndProgramSession(program, student.programSession)
            try{
            def lateFeeDate=student.programDetail.lateFeeDate[0]
            def today=new Date()
                if(lateFeeDate!=null) {
                    if (today.compareTo(lateFeeDate) > 0) {
                        lateFee = AdmissionFee.findByProgramDetail(student.programDetail).lateFeeAmount
                    }
                }
            feeType = null
            programFeeAmount = programFee.feeAmountAtIDOL+lateFee
        }catch(NullPointerException e){
        flash.message="Late Fee Date is not asigned! "
                if (params.idol == "idol") {
                    redirect(controller: student, action:enrollmentAtIdol)
                }
                else{
                    redirect(controller: params.controller, action: feeVoucher)
                }
        }

        if (params.idol == "idol")
            args = [template: "feeVoucherAtIdol", model: [student: student, programFee: programFee,lateFee:lateFee, programFeeAmount: programFeeAmount, feeType: feeType]]
        else
            args = [template: "feeVoucher", model: [student: student,lateFee:lateFee, programFee: programFee, programFeeAmount: programFeeAmount, feeType: feeType]]
        pdfRenderingService.render(args + [controller: this], response)


    }

    @Secured("ROLE_ADMIN")
    def assignExaminationDate = {
        def programList = ProgramDetail.list(sort: 'courseCode')
        [programList: programList]
    }
    @Secured("ROLE_ADMIN")
    def assignExaminationVenue={
        def programList = ProgramDetail.list(sort:'courseCode')
        def obj=City.createCriteria()
        def examCenterList=obj.list {
            eq('isExamCentre',1)
            order('cityName','asc')
        }
            [programList: programList,examinationCenterList:examCenterList]
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
            try{
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
            catch(Exception e){
                println("This is the exception "+ e)
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
        try {
            def programIns = ProgramDetail.findById(Integer.parseInt(params.program))
            def lateFeeDate = programIns.lateFeeDate
            def today = new Date()
            if(lateFeeDate!=null) {
                if (today.compareTo(lateFeeDate) > 0) {
                    lateFee = AdmissionFee.findByProgramDetail(programIns).lateFeeAmount
                }
            }
            def feeAmount = AdmissionFee.findByProgramDetail(ProgramDetail.findById(Integer.parseInt(params.program)));
            payableFee = feeAmount.feeAmountAtIDOL + lateFee
        }
        catch(NullPointerException e){
            payableFee=0
        }
        resultMap.feeAmount = payableFee
        render resultMap as JSON
    }
    def searchListStudentByChallanNo(){
        def returnMap=[:]
        def lateFee=0
        def courseNameList=[],courseFee=[]
        def stuList=  Student.findAllByChallanNoAndStatus(params.challanNo,Status.findById(2))
        def currentUser = springSecurityService.currentUser
//        println("username = :"+StudyCenter.findAllById(currentUser.studyCentreId).centerCode)
        stuList.each{
            lateFee=0
            def lateFeeDate=it.programDetail.lateFeeDate[0]
            def today=new Date()
//            def amount=AdmissionFee.findByProgramDetail(it.programDetail[0])
            if(lateFeeDate!=null) {
                if (today.compareTo(lateFeeDate) > 0) {
                    lateFee = AdmissionFee.findByProgramDetail(it.programDetail[0]).lateFeeAmount
                }
            }
            courseNameList<<it.programDetail[0].courseName
            //if(StudyCenter.findAllById(currentUser.studyCentreId).centerCode[0]=="11111") {
            if(it.studyCentre.centerCode[0]=="11111"){
                courseFee << AdmissionFee.findByProgramDetail(it.programDetail[0]).feeAmountAtIDOL
//                +lateFee  -----------If required late fee remove this
            }else{
                courseFee << AdmissionFee.findByProgramDetail(it.programDetail[0]).feeAmountAtSC
//                +lateFee
            }
//            courseFee=courseFee
        }
        returnMap.stuList = stuList
        returnMap.courseNameList = courseNameList
        returnMap.courseFee = courseFee
        render returnMap as JSON
    }

    def searchMiscFeeListByChallanNo(){
        def returnMap=[:]
        def courseNameList=[],courseFee=[],stuList=[]
        def miscFeeChallanList=  MiscellaneousFeeChallan.findAllByChallanNo(params.challanNo)
        miscFeeChallanList.each{
//            println("==="+it.student.programDetail)
            stuList<<it.student
            courseNameList<<it.student.programDetail[0].courseName
            courseFee<<MiscellaneousFee.findByProgramDetailAndFeeType(it.student.programDetail[0],it.feeType).amount
        }
        returnMap.stuList=stuList
        returnMap.courseNameList=courseNameList
        returnMap.courseFee=courseFee
        render  returnMap as JSON
    }
    def searchByChallanNo(){
        def returnMap = [:]
//        println("???????/" + params)
        returnMap = feeDetailService.studentDetailByChallanNumber(params)
        render returnMap as JSON
    }

    def approveFeeForStudents = {
//        println(">>>>>>>>>>>>>" + params.studentListId)
        def student
        def studentListId=[]
        studentListId.addAll(params.studentListId)
      studentListId.each{
          student = Student.findById(it)
          def status = Status.findById(4)
          student.status = status
          student.save(flush: true)
    }
        if(student){
            flash.message = "Approved Successfully"
            redirect(action: "approvePayInSlip")
        }

    }

    //ADDED BY DIGVIJAY ON 19 May 2014
    @Secured(["ROLE_ADMIN"])
    def addCourses = {
        def programTypeList = ProgramType.list()
//        println("AdminController-->addCourses"+programTypeList);
        [programTypeList:programTypeList]
    }

    def updateCourses = {
//        println("AdminController-->updateCourses Action" + params)
        def programDetail = ProgramDetail.findById(Integer.parseInt(params.CourseId))
//        println("Inside Admin Controller Action "+programDetail)
        [programDetail:programDetail]
    }
    @Secured(["ROLE_ADMIN"])
    def assignRollNoGenerationDate={
        def rollDateInst = RollNoGenerationFixture.findById(1)
        [rollDateInst:rollDateInst]
    }
    def saveRollNoGenerationDate={
            def status=adminInfoService.saveRollNoGenDate(params)
        if(status) {
            flash.message = "Roll Number Generation Date Assigned Successfully"
        }
        else {
            flash.message = "Unable to Assign Date Successfully"
        }
            redirect(action: "assignRollNoGenerationDate")

    }
    def generateRollIsAllow={
        def returnMap = [:]
        Boolean status=false

        def genRollNoIns=RollNoGenerationFixture.findById(1)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       try {
           Date min = genRollNoIns.startD
           Date max = genRollNoIns.endD
           Date cdate = new Date()
           if (cdate.compareTo(min) >= 0 && cdate.compareTo(max) <= 0) {
               status = true
           }
       }
       catch(NullPointerException e){
           flash.message="Please Assign Roll No Generation Date."
           redirect(action: "viewProvisionalStudents")
       }
        returnMap.status=status
        render  returnMap as JSON
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
    def deleteLateFeeDate={
//        println(params)
        def status=adminInfoService.removeDateLateFee(params)
        if(status) {
            flash.message = "Late Fee Date Removed Successfully"
        }
        else {
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
                if(it.lateFeeDate){
                dateList << it.lateFeeDate.getDateString()
                }
                else{
                    dateList <<""
                }
            }

        def response = [programList: programs, dateList: dateList]
        render response as JSON
    }

    def saveLateFeeDate = {

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy")
        def date = df.parse(params.lateFeeDate)
        def programList=[]
        programList.addAll( params.programs)
        programList.each {
//            println("############################3 "+it )
            def program = ProgramDetail.findById(Integer.parseInt(it))
            program.lateFeeDate = date
            if(program.save(flush: true, failOnError: true)){
                flash.message="Succesfully Assigned Late Fee Date."
            }
        }
        redirect(action: "assignLateFeeDate")
    }

    @Secured(["ROLE_ADMIN"])
    def studyMaterial={

    }

    def getStudentForStudyMaterial(){
//        println("???????????"+params)
        def returnMap=[:]
        returnMap= adminInfoService.studentForStudyMaterial(params)
//        println('this is the returning map '+returnMap)
        render returnMap as JSON
    }

    def saveStudyMaterial(){
//        println("inn"+params)
        def returnMap=[:]
        def resultMap= adminInfoService.saveStudentForStudyMaterial(params)
//        println("********"+resultMap)
        if(resultMap){
          returnMap.status="true"
        }
        else{
            returnMap.status="false"
        }
        render returnMap as JSON
    }
    @Secured("ROLE_ADMIN")
    def assignAdmissionPeriod(){
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
    def saveAdmissionFeePeriod(){
        def status=adminInfoService.saveAdmissionPeriod(params)
        if(status) {
            flash.message = "Admission Period Saved Successfully"
        }
        else {
            flash.message = "Unable Save Successfully"
        }
        redirect(action: "assignAdmissionPeriod")
    }
    def getAdmissionDate={
        def returnMap=[:]
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy")
        def progmInst=ProgramDetail.findById(params.programCode)
        returnMap.startDate=df.format(progmInst.startAdmission_D)
        returnMap.endDate=df.format(progmInst.endAdmission_D)
        render returnMap as JSON
    }
    @Secured("ROLE_ADMIN")
    def individualStudentUpdate={
//        def grailsApplication = Holders.getGrailsApplication()
//        def rootImageFolder =  grailsApplication.config.my.global.variable;
//        grailsApplication.config.my.global.variable=grailsApplication.config.my.global.variable+1
//        [rootImageFolder:rootImageFolder]
    }
    def loadPayInSlipDetail={
        def returnMap=[:]
        def status=false
        def payMode=PaymentMode.findById(Integer.parseInt(params.pMode))
        def studentInst
        if(payMode.paymentModeName=='Pay In Slip'){
            studentInst=Student.findByChallanNo(params.challanNo)
           if(StudyCenter.findById(studentInst.studyCentre[0].id).centerCode=='11111'){
               status=true
           }
        }
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy")
        if(status) {
            returnMap.admissionDate = df.format(studentInst.admissionDate)
            returnMap.refNo = studentInst.challanNo
            returnMap.bank = Bank.findByBankName('State Bank Of India').id
            returnMap.bankName = Bank.findByBankName('State Bank Of India').bankName
            returnMap.branch = Branch.findByBranchLocation('Gauhati University').id
            returnMap.branchName = Branch.findByBranchLocation('Gauhati University').branchLocation
        }
        render returnMap as JSON
    }
}
