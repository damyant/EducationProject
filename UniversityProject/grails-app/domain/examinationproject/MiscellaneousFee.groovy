package examinationproject

class MiscellaneousFee {

    ProgramDetail programDetail
    FeeType feeType
    int amount
    ProgramSession programSession

    static constraints = {
        programDetail(nullable: false)
        feeType(nullable:true)
        amount(nullable:true)
        programSession(nullable:true)

    }

    static mapping = {
        programDetail column:"ProgramDetailId"
        feeType column:"FeeTypeId"
        amount column: "Amount"
        programSession column: "ProgramSessionId"

    }
}
