package universityproject

import examinationproject.FeeDetails
import examinationproject.FeeType
import examinationproject.Student
import grails.transaction.Transactional

import java.text.DateFormat
import java.text.SimpleDateFormat

@Transactional
class FeeDetailService {

    def serviceMethod() {

    }

    def saveFeeDetails (params){
        println("params  >>>>>>>>>>>>"+params)
        def feeDetailsInstance = new FeeDetails()
        def student = Student.findById(params.studentId)
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        println("params  >>>>>>>>>>>>1")
        feeDetailsInstance.studentId = student
        println("params  >>>>>>>>>>>>2")
        feeDetailsInstance.draftDate=df.parse(params.draftDate)
        feeDetailsInstance.paymentDate=df.parse(params.paymentDate)
        println("params  >>>>>>>>>>>>3")
        feeDetailsInstance.draftNumber= params.draftNumber
        feeDetailsInstance.feeType = FeeType.findById(params.feeType)
        println("params  >>>>>>>>>>>>4")
        feeDetailsInstance.issuingBank = params.issuingBank
        feeDetailsInstance.issuingBranch = params.issuingBranch
        println("params  >>>>>>>>>>>>5")
        feeDetailsInstance.paymentMode = params.paymentMode

        feeDetailsInstance.save(flush: true)

        return feeDetailsInstance
    }
}
