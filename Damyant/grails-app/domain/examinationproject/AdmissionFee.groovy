package examinationproject

class AdmissionFee {
    int feeAmountAtIDOL
    int feeAmountAtSC
    int lateFeeAmount
    int term

    static belongsTo = [feeSession: FeeSession]
    static constraints = {
        feeAmountAtIDOL(nullable:true)
        feeAmountAtSC(nullable:true)
        lateFeeAmount(nullable:true)
        term(nullable:true)
    }
    static mapping = {
        feeAmountAtIDOL column:"FeeAmountAtIDOL"
        feeAmountAtSC column: "FeeAmountAtSC"
        lateFeeAmount column: "LateFeeAmount"
        term column: "Term"
    }
}
