package examinationproject

//import javax.enterprise.inject.Default

class FeeDetails {
    String challanNo
    int paidAmount
    Boolean isAdmission=false
    PaymentMode paymentModeId
    Date paymentDate
    Date challanDate
    Integer paymentReferenceNumber
    Bank bankId
    Branch branchId



    static constraints = {
        challanNo(nullable: false)
        paidAmount(nullable: true)
        paymentModeId(nullable: false)
        paymentReferenceNumber(nullable: true)
        bankId(nullable: false)
        branchId(nullable: false)
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

    }
}
