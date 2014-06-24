package examinationproject

//import javax.enterprise.inject.Default

class FeeDetails {
    String challanNo
    int paidAmount
//    Boolean isAdmission=false
    PaymentMode paymentModeId
    Date paymentDate
    Date challanDate
    Integer paymentReferenceNumber
    Bank bankId
    Branch branchId
    FeeType feeType
    Student student
    int semesterValue
    Boolean isApproved =false



    static constraints = {
        challanNo(nullable: false)
        paidAmount(nullable: false)
        paymentModeId(nullable: true)
        paymentReferenceNumber(nullable: true)
        bankId(nullable: true)
        branchId(nullable: true)
        feeType(nullable: false)
        semesterValue(nullable:true)
        student(nullable:true)
        challanDate(nullable: true)
        paymentDate(nullable: true)
    }

    static mapping = {
        challanNo column: "challanNo"
//        feeTypeId column: "FeeTypeId",defaultValue: "0"
        paymentModeId column: "PaymentModeId"
         paymentDate column: "PaymentDate"
        challanDate column: "ChallanDate"
        paymentReferenceNumber column: "ReferenceNumber"
        bankId column: "BankId"
        branchId column: "BranchId"
        feeType column:"FeeType"
        semesterValue column: 'SemesterValue'
        student column: 'Student'

    }
}
