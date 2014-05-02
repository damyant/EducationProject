package examinationproject

class FeeDetails {

    Student studentId
    FeeType feeType
    String paymentMode
    Date paymentDate
    String draftNumber
    Date draftDate
    String issuingBank
    String issuingBranch

    static constraints = {
        studentId(nullable: false)
        feeType(nullable: false)
        paymentMode(nullable: false)
        draftNumber(nullable: false,unique: true)
        issuingBank(nullable: false)
        issuingBranch(nullable: false)
    }

    static mapping = {
        studentId column: "studentId"
        feeType column: "feeType"
        paymentMode column: "paymentMode"
        paymentDate column: "paymentDate"
        draftDate column: "draftDate"
        draftNumber column: "draftNumber"
        issuingBank column: "issuingBank"
        issuingBranch column: "issuingBranch"

    }
}
