package examinationproject

class FeeDetails {

    Student studentId
    FeeType feeTypeId
    PaymentMode paymentModeId
    Date paymentDate
    Integer paymentReferenceNumber
    Bank bankId
    Branch branchId

    static constraints = {
        studentId(nullable: false)
        feeTypeId(nullable: false)
        paymentModeId(nullable: false)
        paymentReferenceNumber(nullable: false)
        bankId(nullable: false)
        branchId(nullable: false)
    }

    static mapping = {
        studentId column: "studentId"
        feeTypeId column: "FeeTypeId"
        paymentModeId column: "PaymentModeId"
        paymentDate column: "PaymentDate"
        paymentReferenceNumber column: "ReferenceNumber"
        bankId column: "BankId"
        branchId column: "BranchId"

    }
}
