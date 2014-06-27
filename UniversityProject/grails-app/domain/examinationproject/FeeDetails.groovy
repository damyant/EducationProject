package examinationproject

//import javax.enterprise.inject.Default

class FeeDetails {
    String challanNo
    int paidAmount
    PaymentMode paymentModeId
    Date paymentDate
    Date challanDate
    Integer paymentReferenceNumber
    Bank bankId
    Branch branchId
    FeeType feeType
    Student student
    int semesterValue
    Status isApproved

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
        isApproved column: "isApproved"
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
