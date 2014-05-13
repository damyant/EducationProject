package examinationproject

class AdmissionFee {
    ProgramDetail programDetail
    ProgramSession programSession
    int feeAmountAtIDOL
    int feeAmountAtSC
    int lateFeeAmount


    static constraints = {
        programDetail(nullable: false,unique: true)
        programSession (nullable:true)
        feeAmountAtIDOL(nullable:true)
        feeAmountAtSC(nullable:true)
        lateFeeAmount(nullable:true)

    }

    static mapping = {
        programDetail column:"ProgramDetailId"
        programSession column: "ProgramSessionId"
        feeAmountAtIDOL column:"FeeAmountAtIDOL"
        feeAmountAtSC column: "FeeAmountAtSC"
        lateFeeAmount column: "LateFeeAmount"

    }
}
