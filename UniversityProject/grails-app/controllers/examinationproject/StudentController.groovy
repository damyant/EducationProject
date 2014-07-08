package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.apache.commons.lang.ObjectUtils.Null

import java.text.DateFormat
import java.text.SimpleDateFormat

//@Secured("ROLE_STUDYCENTRE")
class StudentController {
    def studentRegistrationService
    def pdfRenderingService
    def springSecurityService
//    @Secured('ROLE_STUDYCENTRE')

    def registration= {
        def studyCentreList
        def studyCentre
        def programList=[]
        def count=0
        if (springSecurityService.isLoggedIn()) {
            def currentUser = springSecurityService.currentUser.username
            if (springSecurityService.currentUser.studyCentreId != 0) {
                studyCentre= StudyCenter.findById(springSecurityService.currentUser.studyCentreId)
            } else {
                studyCentre = StudyCenter.findByCenterCode('11111')
            }
        } else {
            studyCentre = StudyCenter.findByCenterCode('11111')
        }
        def studInstance = Student.get(params.studentId)

        def allProgramList = ProgramDetail.list(sort: 'courseCode')
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy")
            def today =df.parse(df.format(new Date()))
            allProgramList.each {
                if(it.startAdmission_D!=null && it.endAdmission_D !=null) {
                    def start =df.parse(df.format(it.startAdmission_D))
                    def end =df.parse(df.format(it.endAdmission_D))
                    if (start.compareTo(today) <= 0 && end.compareTo(today) >=0) {
                        programList.add(it)
                        count++
                    }
                }
            }
//            println("total "+count)
            if(count==0){
//                flash.message="Admission Period Not Started Yet"
            }
        }
        catch(NullPointerException e){
            flash.message="Please Assign Admission Date"
        }
        if(params.studentId){
            programList = ProgramDetail.list(sort: 'courseCode')
             studyCentreList = StudyCenter.list()
        }
        def districtList=District.list(sort: 'districtName')
        def bankName=Bank.list(sort:'bankName')
        def paymentMode=PaymentMode.findById(2)
        def centreList =  City.findAllByIsExamCentre(1)
//        println("sss"+studInstance.status)
        [studyCentre: studyCentre,studInstance:studInstance, studyCenterList: studyCentreList, programList: programList,centreList:centreList,districtList:districtList,registered:params.registered,studentID:params.studentID,bankName:bankName,paymentMode:paymentMode,fee:params.fee]

    }
    def viewResult = {

    }
    def submitRegistration = {
            def signature = request.getFile('signature')
            def photographe = request.getFile("photograph")
           def studentRegistration = studentRegistrationService.saveNewStudentRegistration(params, signature, photographe )
        if (studentRegistration) {
            if(params.studentId){
                if(springSecurityService.isLoggedIn()){
                    flash.message = "${message(code: 'register.updated.message')}"
//                    redirect(action: "registration", params: [ studentID: studentRegistration.id,registered:"reg"])
                    redirect(controller: 'admin', action: "individualStudentUpdate")

                }else{
                    flash.message = "${message(code: 'register.updated.message')}"
                    redirect(action: "registration", params: [ fee:fee,studentID: studentRegistration.id,registered:"registered"])
                }
            }
            else{
                if(springSecurityService.isLoggedIn()){
                    flash.message = "${message(code: 'register.created.message')} & Roll Number is "+studentRegistration.rollNo
                    redirect(action: "registration", params: [ studentID: studentRegistration.id,registered:"reg"])
                }else{
                    def student = studentRegistration
                    def lateFee=0
                    def payableFee=0
                    try {
                        def lateFeeDate = student.programDetail.lateFeeDate[0]
                        def today = new Date()
                        if(lateFeeDate!=null) {
                            if (today.compareTo(lateFeeDate) > 0) {
                                lateFee = AdmissionFee.findByFeeSessionAndTerm(FeeSession.findByProgramDetailId(student.programDetail[0]),1).lateFeeAmount
                            }
                        }
//                        println(student.programDetail)
                        def feeAmount = AdmissionFee.findByFeeSessionAndTerm(FeeSession.findByProgramDetailId(student.programDetail[0]),1);
                        payableFee = feeAmount.feeAmountAtIDOL + lateFee
                    }
                    catch(NullPointerException e){
                        payableFee=0
                    }
                    def feeDetails=[:]
                    feeDetails.challanNo=student.challanNo
                    if(params.bankCheckBox){
                        feeDetails.bankName=params.bankName
                        feeDetails.branchName=params.branchName
                    }
                    else{
                        feeDetails.bankName=Bank.findById(params.bankName).bankName
                        feeDetails.branchName=Branch.findById(params.branchName).branchLocation
                    }

                    feeDetails.paymentMode=PaymentMode.findById(params.paymentMode).paymentModeName
                    feeDetails.admissionFeeAmount=params.admissionFeeAmount
                    feeDetails.feeReferenceNumber=params.feeReferenceNumber
                    feeDetails.paymentDate=params.paymentDate
//                    def feeDetails = FeeDetails.findByChallanNo(student.challanNo)
                    def args = [template: "applicationPrintPreview", model: [studentInstance: studentRegistration,feeDetails:feeDetails,payableFee:payableFee],filename:studentRegistration.firstName+".pdf"]
                    pdfRenderingService.render(args + [controller: this], response)
                }
            }
        } else {
                flash.message = "${message(code: 'register.notCreated.message')}"
                redirect(action: "registration")
        }
    }


    def applicationPrintPreview = {
        def student = Student.findById(params.studentID)
        def lateFee=0
        def payableFee=0
        try {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(cal.YEAR);
            def sessionVal= year+1
            sessionVal= year+'-'+sessionVal
//            def programIns = ProgramDetail.findById(Integer.parseInt(params.program))
            def lateFeeDate = student.programDetail.lateFeeDate[0]
             def feeSessionObj=FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(student.programDetail[0].id),sessionVal)
            def today = new Date()
            if(lateFeeDate!=null) {
                if (today.compareTo(lateFeeDate) > 0) {
//                    lateFee = AdmissionFee.findByProgramDetail(student.programDetail).lateFeeAmount
                    lateFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj,1).lateFeeAmount
                }
            }
//            def feeAmount = AdmissionFee.findByProgramDetail(student.programDetail);
            def feeAmount = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj,1);
            payableFee = feeAmount.feeAmountAtIDOL + lateFee
        }
        catch(NullPointerException e){
            payableFee=0
        }
        def feeDetails = FeeDetails.findByChallanNo(student.challanNo)
        render template: 'applicationPrintPreview', model: [studentInstance: student,feeDetails:feeDetails,payableFee:payableFee]
//        def args = [template: "applicationPrintPreview", model: [studentInstance: student,feeDetails:feeDetails,payableFee:payableFee]]
//        pdfRenderingService.render(args + [controller: this], response)


    }

    def showStatus() {
        try {
            def response = [:]
//        println("showing data.."+params.data)
            def refNumber = Student.findByReferenceNumber(params.data)
//        println(refNumber)
            def statusName
            if (refNumber != null) {
                def status = refNumber.statusId
                def rollNo = refNumber.rollNo
                def statusIn = Status.findById(status)
//            println(statusIn)
                statusName = statusIn.status
                response.response1 = statusName
                response.response2 = rollNo
                render response as JSON
            } else {
                render response as JSON
            }
        } catch (Exception e) {
//            println("***problem in showing Status of Application***")
        }
    }

    def applicationPreview() {

    }

    @Secured(["ROLE_IDOL_USER","ROLE_ADMIN", "ROLE_ACCOUNT"])
    def studentListView = {
        def studyCenterList=StudyCenter.list(sort: 'name')
        def programList=ProgramDetail.list(sort: 'courseCode')
        [studyCenterList:studyCenterList,programList:programList]
    }
    def show = {
        def id= Integer.parseInt(params.id)
        def something = Student.get(id)
        byte[] image = something.studentImage
        response.setContentType(params.mime)
        response.outputStream << image
    }

    @Secured(["ROLE_IDOL_USER","ROLE_ADMIN", "ROLE_ACCOUNT"])
    def enrollmentAtIdol={
        def studyCentre
        def programList =[]
        def count=0

        if (springSecurityService.isLoggedIn()) {


            def currentUser = springSecurityService.currentUser.username
            if (springSecurityService.currentUser.studyCentreId != 0) {
                studyCentre= StudyCenter.findById(springSecurityService.currentUser.studyCentreId)
            } else {
                studyCentre = StudyCenter.findByCenterCode('11111')
            }

        } else {
            studyCentre = StudyCenter.findByCenterCode('11111')

        }

        def allProgramList = ProgramDetail.list(sort: 'courseCode')
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy")
            def today =df.parse(df.format(new Date()))
            allProgramList.each {

                if(it.startAdmission_D!=null && it.endAdmission_D !=null) {
                    def start =df.parse(df.format(it.startAdmission_D))
                    def end =df.parse(df.format(it.endAdmission_D))
//                    println("start>>>----"+start)
//                    println("End>>>----"+end)
//                    println("today>>>----"+today)
//                    println(it.courseName)
//                    println("start"+start.compareTo(today))
//                    println("end"+today.compareTo(end))
                    if (start.compareTo(today) <= 0 && end.compareTo(today) >=0) {
                        programList.add(it)
                        count++
                    }
                }
            }
//            println("total "+count)
            if(count==0){
//                flash.message="Admission Period Not Started Yet"
            }
        }
        catch(NullPointerException e){
            flash.message="Please Assign Admission Date"
        }

//        def districtList=District.list(sort: 'districtName')
        def districtList=City.list()*.district as Set
        def finalDistrictList= districtList.sort{a,b->
            a.districtName<=>b.districtName
        }
//        println("sss--->>>>>> "+districtList.size())
        [ programList: programList,studyCentre:studyCentre,districtList:finalDistrictList]
    }

    def downloadAdmitCard={

    }
    def checkApplicationNo(){
        def status=[:]
        def year = ProgramDetail.findById(Long.parseLong(params.programme)).admissionYear
        def applicationNo = year+''+params.applicationNo
        def applicationNoIns=Student.findByApplicationNo(applicationNo)
//        println(applicationNoIns)
        if(applicationNoIns){
            status.applicationNo='true'
        }
        else{
            status.applicationNo='false'
        }
        render status as JSON

    }
    def tempRegistration() {
       def studentRegistration = studentRegistrationService.saveNewStudentRegistration(params, "", "")
        if (studentRegistration) {
            def infoMap =[:]
            println("---------------------------------"+studentRegistration.rollNo)
            def student = Student.findByRollNo(studentRegistration.rollNo)
            def program = student.programDetail
            def feeTypeId
            def feeType = null
            def args
            def lateFee=0
            def programFeeAmount = 0
            Calendar cal = Calendar.getInstance();
            int year = cal.get(cal.YEAR);
            def sessionVal= year+1
            sessionVal= year+'-'+sessionVal
            def feeSessionObj=FeeSession.findByProgramDetailIdAndSessionOfFee(ProgramDetail.findById(student.programDetail[0].id),sessionVal)
            def programFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj,1)
//            println('this is the programFee '+programFee)
            try{
                def lateFeeDate=student.programDetail.lateFeeDate[0]
                def today=new Date()
                if(lateFeeDate!=null) {
                    if (today.compareTo(lateFeeDate) > 0) {
                        lateFee = AdmissionFee.findByFeeSessionAndTerm(feeSessionObj,1).lateFeeAmount
                    }
                }
                feeType = null
                programFeeAmount = programFee.feeAmountAtIDOL+lateFee
            }catch(Exception e){
                println("this exception occurred here "+ e)
                flash.message="Late Fee Date is not assigned! "
                redirect(controller: student, action:enrollmentAtIdol)

            }
            def feeDetailInst=new FeeDetails()
            feeDetailInst.student=student
            feeDetailInst.paidAmount=programFeeAmount
            feeDetailInst.semesterValue=1
            feeDetailInst.challanDate=new Date()
            feeDetailInst.isApproved=Status.findById(1)
            feeDetailInst.feeType=FeeType.findById(3)
            feeDetailInst.challanNo = student.challanNo
            feeDetailInst.save(failOnError: true, flush: true)
              println("__________________"+lateFee)
             infoMap.student=student
             infoMap.programFee=programFee
             infoMap.lateFee=lateFee
             infoMap.courseName=programFee.feeSession.programDetailId.courseName
             infoMap.programFeeAmount=programFeeAmount
             infoMap.feeType=feeType
             render infoMap as JSON
        } else {
            flash.message = "${message(code: 'register.notCreated.message')}"
            redirect(action: "enrollmentAtIdol")
        }
    }


    def seedBulkStudents={
        studentRegistrationService.seedStudent(params)
        render "done"
    }

  def  updateStudent={
//      println(params)
      try{
       def student = Student.findByRollNo(params.rollNo)
          if(student){
            redirect(action: "registration", params:[studentId:student.id])
          }else{
              flash.message = "No Record Found"
              redirect(action: "studentListView")
          }
      }catch(NullPointerException ex ){
          println("Problem in searching student by roll number"+ex.printStackTrace())
      }

    }

    def viewStudent = {
//        println("??????????????????????"+params)
        def student = Student.findById(params.studentId)
//        println("Challan Number"+student.addressDistrict)
        def feeDetails = FeeDetails.findByChallanNo(student.challanNo)
        def miscellaneousFeeChallan = FeeDetails.findById(student.id)
        [studInstance:student,feeDetails: feeDetails]
    }
    @Secured(["ROLE_IDOL_USER","ROLE_ADMIN", "ROLE_ACCOUNT"])
    def customChallanSave={
//        println(params)
        def resultMap = studentRegistrationService.saveCChallan(params)
        if(resultMap.status){
            def infoMap =[:]

            infoMap.challanNo=resultMap.challanNo
            infoMap.name=params.challanName
            infoMap.feeAmount=params.amount
            infoMap.feeType=params.typeOfFee
            render infoMap as JSON
        } else {
            flash.message = "${message(code: 'register.notCreated.message')}"
            redirect(controller: 'admin', action: "generateCustomChallan")
        }
    }
    def generateIdentityCard={
        def programList=ProgramDetail.list(sort: 'courseCode')
        def sessionList = Student.createCriteria().list {
            projections {
                distinct("registrationYear")
            }
        }
        [programList:programList,sessionList:sessionList]
    }
    def getStudentsForIdentityCard={
        def prgramInst=ProgramDetail.findById(Long.parseLong(params.programList))
        def status=Status.findById(4)
        def stuObj=Student.createCriteria()
        def studentList=stuObj.list{
            programDetail {
                eq('id', prgramInst.id)
            }
            and {
                eq('status',status)
                eq('registrationYear', Integer.parseInt(params.admissionYear))
                eq('identityCardGenerated', false)
            }
        }

//                Student.findAllByProgramDetailAndRegistrationYearAndIdentityCardGeneratedAndStatus(prgramInst,Integer.parseInt(params.admissionYear),false,Status.findById(4))

        if(studentList){
            render studentList as JSON
        }
        else{
            def resultMap = [:]
            resultMap.status = false
            render resultMap as JSON
        }
    }
    def printIdentityCard={
        def year=Integer.parseInt(params.admissionYear)
        def fileName=year+"-"+(year+1)
        def studentName=[],studentProgram=[],studentRoll=[],studentDOB=[],studentAddress=[],studentPin=[],studentMobNo=[],stuList=[]
        def studentList = params.studentList.split(",")
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy")
        studentList.each {
            def studentInst = Student.findById(Integer.parseInt(it.toString()))
            stuList<<studentInst
            studentName<<studentInst.firstName+" "+(studentInst.middleName?studentInst.middleName:"")+" "+studentInst.lastName
            studentProgram<<studentInst.programDetail[0].courseName
            studentRoll<<studentInst.rollNo
            studentDOB<<df.format(studentInst.dob)
            studentAddress<<studentInst.studentAddress+", "+studentInst.addressTown+", "+studentInst.addressDistrict+", "+studentInst.addressState
            studentPin<<studentInst.addressPinCode
            studentMobNo<<studentInst.mobileNo
        }
        def args = [template: "printIdentityCard", model: [studentInstance: stuList,studentMobNo:studentMobNo,studentPin:studentPin,studentAddress:studentAddress,studentDOB:studentDOB,studentRoll:studentRoll,studentProgram:studentProgram,studentName:studentName], filename: fileName + ".pdf"]
        pdfRenderingService.render(args + [controller: this], response)
    }
}
