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
    def adminInfoService
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
        println("hello kuldeep now save your data" + params)
        def feeDetailsInstance = feeDetailService.saveFeeDetails(params)

        if (feeDetailsInstance.hasErrors()) {
            render "<h5>Fee Details for this student cannot be saved</h5>"
        }

        if(feeDetailsInstance){
            render "<h5>Fee Details saved</h5>"
        }

    }
    def studyCentreAdmissionFee={
        def programList = ProgramDetail.list(sort:'courseName')
//        def paymentModeList = PaymentMode.list(sort:'paymentModeName')
//        def bankList = Bank.list(sort:'bankName')
        [programList:programList]
//        [programList:programList, paymentModeList:paymentModeList, bankList:bankList]
    }
    def studyCentrePostAdmissionFee={

    }
    def populateStudents={
        def resultMap=[:]
        def stuList= feeDetailService.StudentList(params.programId)
        def bankName=Bank.list(sort:'bankName')
        def paymentMode=PaymentMode.list(sort:'paymentModeName')
        def programDetail=ProgramDetail.findAllById(params.programId)
        def feeAmount=ProgramFee.findAllByProgramDetail(programDetail)
        resultMap.studentList=stuList
        resultMap.bankName=bankName
        resultMap.paymentMode=paymentMode
        resultMap.feeAmount=feeAmount.feeAmountAtSC
        render resultMap as JSON
    }

    def getBankBranch={

        def bankIns=Bank.findById(Long.parseLong(params.bankId))
        def branchName=bankIns.branch
        render branchName as JSON

    }
    def saveFeeData={
        println("****In save method*****"+params)
    }

}
