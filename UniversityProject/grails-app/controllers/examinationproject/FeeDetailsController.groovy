package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import java.text.DateFormat
import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FeeDetailsController {
    def feeDetailService
    def studentRegistrationService
    def pdfRenderingService
    def springSecurityService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]


    @Secured("ROLE_ADMIN")
    def createFeeDetails() {
        def paymentModeList = PaymentMode.list(sort:'paymentModeName')
        respond new FeeDetails(params)
    }

    @Transactional
    def saveFeeDetails(FeeDetails feeDetailsInstance) {


        if (feeDetailsInstance == null) {
           return
        }

        feeDetailsInstance = feeDetailService.saveFeeDetails(params)

        if (feeDetailsInstance.hasErrors()) {
            respond feeDetailsInstance.errors, view: 'createFeeDetails'
            return
        }

        if(feeDetailsInstance){
            flash.message = "Fee Details Entered Successfully."
            redirect(action: "createFeeDetails")
        }
    }

    def editFeeDetails(FeeDetails feeDetailsInstance) {
        respond feeDetailsInstance
    }

    @Transactional
    def updateFeeDetails(FeeDetails feeDetailsInstance) {


        if (feeDetailsInstance.hasErrors()) {
            respond feeDetailsInstance.errors, view: 'editFeeDetails'
            return
        }

        if(feeDetailsInstance.save(flush: true)){
            redirect(action: "createFeeDetails")
        }
    }
    @Secured("ROLE_ADMIN")
     def bulkFeeEntry   = {

         def filterType = []
         def programList = ProgramDetail.list()
         def studyCentre = StudyCenter.list();
         filterType.add("By Program")
         filterType.add("By Study Centre")
//         filterType.add("By Admission Date")

         [filterType:filterType,programList:programList,studyCentre:studyCentre]
     }
    @Secured("ROLE_ADMIN")
    def getStudentList(){
        def responseMap=[:]
        def stuList= feeDetailService.provisionalStudentList(params)
        responseMap.status="referenceNo"
        responseMap.label=params.pageType
        responseMap.stuList=stuList
        render responseMap as JSON

    }

    def checkStudentByRollNo = {
        def student = Student.findByRollNo(params.rollNo)
        def response =[id:student.id,feeStatus:true]
        render response as JSON
    }

    def saveBulkFeeDetails ={
//        println("hello kuldeep now save your data" + params)
        def feeDetailsInstance = feeDetailService.saveFeeDetails(params)

        if (feeDetailsInstance.hasErrors()) {
            render "<h5>Fee Details for this student cannot be saved</h5>"
        }

        if(feeDetailsInstance){
            render "<h5>Fee Details saved</h5>"
        }

    }
    @Secured("ROLE_STUDY_CENTRE")
    def payAdmissionFee={
        def bankName=Bank.list(sort:'bankName')
        def paymentMode=PaymentMode.list(sort:'paymentModeName')
        [bankName:bankName, paymentMode:paymentMode]
    }

    @Secured("ROLE_STUDY_CENTRE")
    def generateChallanSCAdmissionFee={
        def programList = ProgramDetail.list(sort:'courseName')
        def programCategory=ProgramType.list(sort:'type')
        [programList:programList, programCategory:programCategory]
    }

    def loadProgram={
        println("params"+params)
        def programType=ProgramType.findById(Long.parseLong(params.type))
        def programList = ProgramDetail.findAllByProgramType(programType)
        def response =[programList:programList]
        println(response.programList[0].courseName)
        render response as JSON
    }

    def challanForMiscellaneousFee={
        def programList = ProgramDetail.list(sort:'courseName')
        def programCategory=ProgramType.list(sort:'type')
        def miscFeeType=FeeType.list(sort:'type')
        [programList:programList, programCategory:programCategory,miscFeeType:miscFeeType]
    }

    @Secured("ROLE_STUDY_CENTRE")
    def payMiscellaneousFee={
        def bankName=Bank.list(sort:'bankName')
        def paymentMode=PaymentMode.list(sort:'paymentModeName')
        [bankName:bankName, paymentMode:paymentMode]
    }
    def populateStudents={
        def resultMap=[:]
        resultMap= feeDetailService.StudentList(params)
        render resultMap as JSON
    }
    def populateStudentsForMFee={
        def resultMap=[:]
        resultMap= feeDetailService.StudentListForMFee(params)
        render resultMap as JSON
    }

    def populateStudentsForAllProgram={
        println("in method");
        def resultMap=[:]
        resultMap= feeDetailService.AllProgramStudentList()
        println("in method"+resultMap);
        render resultMap as JSON
    }

    def getBankBranch={

        def bankIns=Bank.findById(Long.parseLong(params.bankId))
        def branchName=bankIns.branch
        render branchName as JSON

    }
    def saveFeeData={

         feeDetailService.saveFeeDetails(params)
        def resultMap=[:]
        resultMap= feeDetailService.StudentList(params)
        render resultMap as JSON

    }
    def getFeeAmount={
//        println("################################################ ====>"+params);
        def resultMap=[:]
        def student = Student.findById(params.studentId)
        def programFee = AdmissionFee.findByProgramDetail(student.programDetail)
//        println(student.programDetail)
        def programFeeAmount
//        println("type --->"+params.feeType)
        def feeType=params.feeType
        if(feeType==1) {
            programFeeAmount = programFee.feeAmountAtSC
//            println("1")
        }
        else if(feeType==2) {
            programFeeAmount = programFee.examinationFee
//            println("2")
        }
        else{
                programFeeAmount = programFee.certificateFee
//                println("3")
        }
        resultMap.programFeeAmount=programFeeAmount;
//        println(programFee.feeAmountAtSC)
//        println(resultMap)
        render resultMap as JSON
    }


    def challanForStudyCenterStu={
         println("***************"+params)
        List<Student> studList =[]
        List<AdmissionFee> addmissionFee = []
        studList.clear()
        addmissionFee.clear()
        def stuList=[]
        stuList.clear()
        def totalFee=0;
        def lateFee=0
        def challanNo=studentRegistrationService.getChallanNumber()
        def today=new Date()
        if(params.rollNoSearch){
            lateFee=0
           def stuIns= Student.findByRollNo(params.rollNoSearch)
            stuIns.challanNo=challanNo
            stuIns.save(failOnError: true)
            studList.add(stuIns)
            def lateFeeDate=stuIns.programDetail.lateFeeDate[0]

            Set<ProgramDetail> programDetails = ProgramDetail.findAllById(stuIns.programDetail[0].id)
            def feeForStudent=AdmissionFee.findByProgramDetailAndProgramSession(programDetails[0],stuIns.programSession).feeAmountAtSC
            if(today.compareTo(lateFeeDate) > 0){
                lateFee=AdmissionFee.findByProgramDetailAndProgramSession(programDetails[0],stuIns.programSession).lateFeeAmount
            }
//            println("@@@@@@@@@@@@@@@@@"+lateFee)
            feeForStudent=feeForStudent+lateFee
            totalFee=totalFee+ feeForStudent
            addmissionFee.add(feeForStudent)
        }else{
        stuList=params.studentListId.split(",")
            for(def i=0;i<stuList.size();i++){
               lateFee=0
                println("**********"+stuList[i]);
                def stuIns=Student.findById(Long.parseLong(stuList[i]))
                stuIns.rollNo
                stuIns.challanNo=challanNo
                stuIns.save(failOnError: true)
                studList.add(stuIns)
                def lateFeeDate=stuIns.programDetail.lateFeeDate[0]


                Set<ProgramDetail> programDetails = ProgramDetail.findAllById(stuIns.programDetail[0].id)
                def feeForStudent=AdmissionFee.findByProgramDetailAndProgramSession(programDetails[0],stuIns.programSession).feeAmountAtIDOL
                if(today.compareTo(lateFeeDate) > 0){
                    lateFee=AdmissionFee.findByProgramDetailAndProgramSession(programDetails[0],stuIns.programSession).lateFeeAmount
                }
                feeForStudent=feeForStudent+lateFee
//                println("@@@@@@@@@@@@@@@@@"+lateFee)
                totalFee=totalFee+ feeForStudent
                addmissionFee.add(feeForStudent)
            }

        }
        println(studList)
        def args = [template: "printChallan", model: [studList: studList,addmissionFee:addmissionFee,totalFee:totalFee,lateFee:lateFee],filename:challanNo+".pdf"]
        pdfRenderingService.render(args + [controller: this], response)
    }
    def generateChallanForMiscellaneousFee={
        println("***************"+params)
        List<Student> studList =[]
        List<MiscellaneousFee> miscellaneousFee = []
        studList.clear()
        miscellaneousFee.clear()
        def stuList=[]
        def totalFee=0;
        def challanNo=studentRegistrationService.getChallanNumber()
        if(params.rollNoSearch){
            def feeType=FeeType.findById(params.feeCategory)
            def stuIns= Student.findByRollNo(params.rollNoSearch)
            def mFeeInstance = new MiscellaneousFeeChallan()
            mFeeInstance.challanNo=challanNo
            mFeeInstance.feeType=feeType
            mFeeInstance.student=stuIns
//            stuIns.challanNo=challanNo
            mFeeInstance.save(failOnError: true)
            studList.add(stuIns)

            Set<ProgramDetail> programDetails = ProgramDetail.findAllById(stuIns.programDetail[0].id)
            def feeForStudent=MiscellaneousFee.findByProgramDetailAndProgramSessionAndFeeType(programDetails[0],stuIns.programSession,feeType).amount

            totalFee=totalFee+ feeForStudent
            miscellaneousFee.add(feeForStudent)
        }
      else{
            stuList=params.studentListId.split(",")
            for(def i=0;i<stuList.size();i++){
                println("**********"+stuList[i]);
                def mFeeInstance = new MiscellaneousFeeChallan()
                def stuIns=Student.findById(Long.parseLong(stuList[i]))
                def feeType=FeeType.findById(params.feeCategory)
                mFeeInstance.challanNo=challanNo
                mFeeInstance.feeType=feeType
                mFeeInstance.student=stuIns
//                stuIns.challanNo=challanNo
                mFeeInstance.save(failOnError: true)
                studList.add(stuIns)
                Set<ProgramDetail> programDetails = ProgramDetail.findAllById(stuIns.programDetail[0].id)
                def feeForStudent=MiscellaneousFee.findByProgramDetailAndProgramSessionAndFeeType(programDetails[0],stuIns.programSession,feeType).amount
                totalFee=totalFee+ feeForStudent
                miscellaneousFee.add(feeForStudent)
            }
            println(studList)
        }
        def args = [template: "printMiscFeeChallan", model: [studList: studList,challanNo:challanNo,miscellaneousFee:miscellaneousFee,totalFee:totalFee],filename:challanNo+".pdf"]
        pdfRenderingService.render(args + [controller: this], response)
    }

    def printChallan={

    }
    def populateStudentsByChallan={
        def resultMap=[:]
        resultMap= feeDetailService.StudentListByChallan(params)
        println(resultMap)
        render resultMap as JSON
    }


    def payChallanForStudyCenterStu={
       println("***************"+params)
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        def courseNameList=[],courseFee=[]
        def stuList=  Student.findAllByChallanNo(params.searchChallanNo)
        def currentUser = springSecurityService.currentUser
        def totalFee=0;
        def lateFee=0
        stuList.each{
            lateFee=0
            println("==="+it.programDetail[0])
            def lateFeeDate=it.programDetail.lateFeeDate[0]
            def today=new Date()
            if(today.compareTo(lateFeeDate) > 0){
                lateFee=AdmissionFee.findByProgramDetail(it.programDetail[0]).lateFeeAmount
            }
            courseNameList<<it.programDetail[0].courseName

            if(it.studyCentre.centerCode[0]=="11111"){
                courseFee << AdmissionFee.findByProgramDetail(it.programDetail[0]).feeAmountAtIDOL+lateFee
     }else{
                courseFee << AdmissionFee.findByProgramDetail(it.programDetail[0]).feeAmountAtSC+lateFee
            }
        }
        for(def k=0;k<courseFee.size();k++){
            totalFee=totalFee+courseFee[k]
        }
        def paymentModeName=PaymentMode.findById(params.paymentMode)
        def bank=Bank.findById(params.bankName)
        def branch=Branch.findById(params.branchLocation)
        def feeDetailsInstance=FeeDetails.findAllByChallanNo(params.searchChallanNo)
        if(feeDetailsInstance){
            flash.message = "Pay Challan Already Created For this Challan"
            redirect(action: "payAdmissionFee")

        }
        else {
            feeDetailsInstance=new FeeDetails()
            feeDetailsInstance.challanNo=params.searchChallanNo
            feeDetailsInstance.paymentModeId = PaymentMode.findById(params.paymentMode)
            feeDetailsInstance.paymentReferenceNumber = PaymentMode.findById(params.paymentReferenceNumber)
            feeDetailsInstance.bankId = Bank.findById(params.bankName)
            feeDetailsInstance.isAdmission= true
            feeDetailsInstance.branchId = Branch.findById(params.branchLocation)
            feeDetailsInstance.challanDate = new Date()
            feeDetailsInstance.paymentDate = df.parse(params.paymentDate)
            if (feeDetailsInstance.save(flush: true, failOnError: true)) {
                for (int j = 0; j < stuList.size(); j++) {
                    stuList[j].status = Status.findById(3)
                    stuList[j].save(flush: true, failOnError: true)
                }
            }
            def challanNo = params.searchChallanNo
            def paymentDate = params.paymentDate
            def paymentReferenceNumber = params.paymentReferenceNumber
//            println("size" + stuList.size())
//            println("std" + stuList)
//            println("std" + stuList[0])
//            println("fee" + courseFee.size() + " >>>>>>>>>>>> " + courseFee)

            def args = [template: "printPayChallan", model: [bank: bank, lateFee: lateFee, branch: branch,paymentReferenceNumber:paymentReferenceNumber, paymentModeName: paymentModeName, paymentDate: paymentDate, stuList: stuList, courseFee: courseFee, totalFee: totalFee, courseNameList: courseNameList, challanNo: challanNo,], filename: challanNo + ".pdf"]
            pdfRenderingService.render(args + [controller: this], response)
        }
    }

    def payMiscFeeChallan={
        println("***************"+params)
        def courseNameList=[],courseFee=[]
        def stuList= []
        def miscFeeChallanList=  MiscellaneousFeeChallan.findAllByChallanNo(params.searchChallanNo)
        miscFeeChallanList.each{
//            println("==="+it.student.programDetail)
            stuList<<it.student
            courseNameList<<it.student.programDetail[0].courseName
            courseFee<<MiscellaneousFee.findByProgramDetailAndFeeType(it.student.programDetail[0],it.feeType).amount
        }
        def totalFee=0;
        for(def k=0;k<courseFee.size();k++){
            totalFee=totalFee+courseFee[k]
        }
        def paymentModeName=PaymentMode.findById(params.paymentMode)
        def bank=Bank.findById(params.bankName)
        def branch=Branch.findById(params.branchLocation)
        def feeDetailsInstance = new FeeDetails()
        feeDetailsInstance.challanNo=params.searchChallanNo
        feeDetailsInstance.paymentModeId=PaymentMode.findById(params.paymentMode)
        feeDetailsInstance.bankId=Bank.findById(params.bankName)
        feeDetailsInstance.branchId=Branch.findById(params.branchLocation)
        feeDetailsInstance.paymentReferenceNumber=Branch.findById(params.paymentReferenceNumber)
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        feeDetailsInstance.challanDate=new Date()
        feeDetailsInstance.paymentDate=df.parse(params.paymentDate)
        def status=false
        if (feeDetailsInstance.save(flush: true, failOnError: true)) {
            status=true
        }
        def challanNo=params.searchChallanNo
        def paymentDate=params.paymentDate
        def paymentReferenceNumber=params.paymentReferenceNumber

        def args = [template: "printPayMiscFeeChallan", model: [bank:bank,branch:branch,paymentReferenceNumber:paymentReferenceNumber,paymentModeName:paymentModeName,paymentDate:paymentDate,stuList:stuList,courseFee:courseFee,totalFee:totalFee,courseNameList:courseNameList,challanNo:challanNo,],filename:challanNo+".pdf"]
        pdfRenderingService.render(args + [controller: this], response)
    }
    def gerStudentId={
        def resultMap=[:]
        def studentId=Student.findByRollNo(params.rollNo).id
        resultMap.studentId=studentId
        render resultMap as JSON
    }
}
