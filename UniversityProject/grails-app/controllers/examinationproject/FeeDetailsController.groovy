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
        def stuList=[]
        def totalFee=0;
        def challanNo=studentRegistrationService.getChallanNumber()
        if(params.rollNoSearch){
           def stuIns= Student.findByRollNo(params.rollNoSearch)
            stuIns.challanNo=challanNo
            stuIns.save(failOnError: true)
            studList.add(stuIns)
            Set<ProgramDetail> programDetails = ProgramDetail.findAllById(stuIns.programDetail[0].id)
            def feeForStudent=AdmissionFee.findByProgramDetailAndProgramSession(programDetails[0],stuIns.programSession).feeAmountAtIDOL
            totalFee=totalFee+ feeForStudent
            addmissionFee.add(feeForStudent)
        }else{
        stuList=params.studentListId.split(",")
            for(def i=0;i<stuList.size()-1;i++){
                println("**********"+stuList[i]);
                def stuIns=Student.findById(Long.parseLong(stuList[i]))
                stuIns.rollNo
                stuIns.challanNo=challanNo
                stuIns.save(failOnError: true)
                studList.add(stuIns)
                Set<ProgramDetail> programDetails = ProgramDetail.findAllById(stuIns.programDetail[0].id)
                def feeForStudent=AdmissionFee.findByProgramDetailAndProgramSession(programDetails[0],stuIns.programSession).feeAmountAtIDOL
                totalFee=totalFee+ feeForStudent
                addmissionFee.add(feeForStudent)
            }

        }



        def args = [template: "printChallan", model: [studList: studList,addmissionFee:addmissionFee,totalFee:totalFee],filename:challanNo+".pdf"]
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
//        println("***************"+params)
        def courseNameList=[],courseFee=[]
        def stuList=  Student.findAllByChallanNo(params.searchChallanNo)
        def totalFee=0;
        stuList.each{
            println("==="+it.programDetail[0])
            courseNameList<<it.programDetail[0].courseName
            courseFee<<AdmissionFee.findByProgramDetail(it.programDetail[0]).feeAmountAtSC

        }
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
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        feeDetailsInstance.paymentDate=df.parse(params.paymentDate)

        if (feeDetailsInstance.save(flush: true, failOnError: true)) {
            for(int j=0;j<stuList.size();j++){
                stuList[j].status = Status.findById(3)
                stuList[j].save(flush: true, failOnError: true)
            }
        }
        def challanNo=params.searchChallanNo
        def paymentDate=params.paymentDate
        println("size"+stuList.size())
        println("std"+stuList)
        println("std"+stuList[0])
        println("fee"+courseFee.size()+" >>>>>>>>>>>> "+courseFee)

        def args = [template: "printPayChallan", model: [bank:bank,branch:branch,paymentModeName:paymentModeName,paymentDate:paymentDate,stuList:stuList,courseFee:courseFee,totalFee:totalFee,courseNameList:courseNameList,challanNo:challanNo,],filename:challanNo+".pdf"]
        pdfRenderingService.render(args + [controller: this], response)
    }
}
