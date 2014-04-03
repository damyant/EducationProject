package examinationproject

import grails.plugins.springsecurity.Secured


class ProgramFee {

    ProgramDetail programDetail
    int feeAmountAtIDOL
    int feeAmountAtSC
    int lateFeeAmount

    static constraints = {
        programDetail(nullable: false,unique: true)
        feeAmountAtIDOL(nullable:true)
        feeAmountAtSC(nullable:true)
        lateFeeAmount(nullable:true)
    }

    static mapping = {
        programDetail column:"programDetail"
        feeAmountAtIDOL column:"feeAmountAtIDOL"
        feeAmountAtSC column: "feeAmountAtSC"
        lateFeeAmount column: "lateFeeAmount"
    }
}
