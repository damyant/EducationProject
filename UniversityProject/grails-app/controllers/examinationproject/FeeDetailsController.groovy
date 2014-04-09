package examinationproject

import grails.converters.JSON

import java.text.DateFormat
import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FeeDetailsController {
    def feeDetailService
    def adminInfoService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond FeeDetails.list(params), model: [feeDetailsInstanceCount: FeeDetails.count()]
    }

    def show(FeeDetails feeDetailsInstance) {
        respond feeDetailsInstance
    }

    def createFeeDetails() {
        respond new FeeDetails(params)
    }

    @Transactional
    def saveFeeDetails(FeeDetails feeDetailsInstance) {

        //println("The feeDetails is "+feeDetailsInstance.draftDate)
        if (feeDetailsInstance == null) {
            notFound()
            return
        }

        feeDetailsInstance = feeDetailService.saveFeeDetails(params)

        if (feeDetailsInstance.hasErrors()) {
            println("Error in saving the fee details>>>>>>>>>>>>>>>>>>>>>>>>")
            respond feeDetailsInstance.errors, view: 'createFeeDetails'
            return
        }

        if(feeDetailsInstance){
            redirect(action: "createFeeDetails")
        }
    }

    def editFeeDetails(FeeDetails feeDetailsInstance) {
        respond feeDetailsInstance
    }

    @Transactional
    def updateFeeDetails(FeeDetails feeDetailsInstance) {
        if (feeDetailsInstance == null) {
            notFound()
            return
        }

        if (feeDetailsInstance.hasErrors()) {
            respond feeDetailsInstance.errors, view: 'editFeeDetails'
            return
        }

        feeDetailsInstance.save flush: true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'FeeDetails.label', default: 'FeeDetails'), feeDetailsInstance.id])
                redirect feeDetailsInstance
            }
            '*' { respond feeDetailsInstance, [status: OK] }
        }
    }

    @Transactional
    def deleteFeeDetails(FeeDetails feeDetailsInstance) {

        if (feeDetailsInstance == null) {
            notFound()
            return
        }

        feeDetailsInstance.delete flush: true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'FeeDetails.label', default: 'FeeDetails'), feeDetailsInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }


     def bulkFeeEntry   = {

         def filterType = []
         def programList = ProgramDetail.list()
         def studyCentre = StudyCenter.list();
         filterType.add("By Program")
         filterType.add("By Study Centre")
         filterType.add("By Admission Date")

         [filterType:filterType,programList:programList,studyCentre:studyCentre]
     }

    def getStudentList(){
        println("<<<<<<<"+params)
        def responseMap=[:]
        def stuList= feeDetailService.provisionalStudentList(params)
        responseMap.status="referenceNo"
        responseMap.label=params.pageType
        responseMap.stuList=stuList
        println("StudentList"+stuList)
        render responseMap as JSON

    }



    def checkStudentByRollNo = {
        println("Roll Number entered by user")
        def student = Student.findByRollNo(params.rollNo)
        println("Student id Is ++++++++++++++"+student.id)

        def response =[id:student.id,feeStatus:true]


        render response as JSON
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'feeDetailsInstance.label', default: 'FeeDetails'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
