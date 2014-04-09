package examinationproject

import grails.plugins.springsecurity.Secured


class ProgramFee {

    ProgramDetail programDetail
    int feeAmountAtIDOL
    int feeAmountAtSC
    int lateFeeAmount
    int examinationFee
    int certificateFee

    static constraints = {
        programDetail(nullable: false,unique: true)
        feeAmountAtIDOL(nullable:true)
        feeAmountAtSC(nullable:true)
        lateFeeAmount(nullable:true)
        examinationFee(nullable:true)
        certificateFee(nullable:true)
    }

    static mapping = {
        programDetail column:"programDetail"
        feeAmountAtIDOL column:"feeAmountAtIDOL"
        feeAmountAtSC column: "feeAmountAtSC"
        lateFeeAmount column: "lateFeeAmount"
        examinationFee column: "examinationFee"
        certificateFee column: "certificateFee"
    }
}
