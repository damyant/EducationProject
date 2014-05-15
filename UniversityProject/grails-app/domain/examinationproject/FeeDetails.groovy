package examinationproject

class FeeDetails {

    String challanNo
    FeeType feeTypeId
    PaymentMode paymentModeId
    Date paymentDate
    Integer paymentReferenceNumber
    Bank bankId
    Branch branchId



    static constraints = {
        challanNo(nullable: false)
        feeTypeId(nullable: true)
        paymentModeId(nullable: false)
        paymentReferenceNumber(nullable: true)
        bankId(nullable: false)
        branchId(nullable: false)
    }

    static mapping = {
        challanNo column: "challanNo"
        feeTypeId column: "FeeTypeId"
        paymentModeId column: "PaymentModeId"
        paymentDate column: "PaymentDate"
        paymentReferenceNumber column: "ReferenceNumber"
        bankId column: "BankId"
        branchId column: "BranchId"

    }
}
