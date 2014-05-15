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
    def studyCentreAdmissionFee={
        def programList = ProgramDetail.list(sort:'courseName')
//        def paymentModeList = PaymentMode.list(sort:'paymentModeName')
//        def bankList = Bank.list(sort:'bankName')
        [programList:programList]
//        [programList:programList, paymentModeList:paymentModeList, bankList:bankList]
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

    def studyCentrePostAdmissionFee={

    }
    def populateStudents={
        def resultMap=[:]
        resultMap= feeDetailService.StudentList(params)
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
        def programFee = ProgramFee.findByProgramDetail(student.programDetail)
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
}
