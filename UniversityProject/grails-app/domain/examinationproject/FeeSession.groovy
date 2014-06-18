package examinationproject

class FeeSession {
    ProgramDetail programDetailId
    String sessionOfFee

    static hasMany = [admissionFee: AdmissionFee,miscellaneousFee:MiscellaneousFee]
    static constraints = {
        sessionOfFee(nullable: true)
    }


    static mapping = {
        sessionOfFee column: "sessionOfFee"
        programDetailId column: "ProgramDetailId"
        admissionFee column: "AdmissionFeeId"
        miscellaneousFee column: "MiscellaneousFeeId"
        admissionFee cascade:"all,delete-orphan"
        miscellaneousFee cascade:"all,delete-orphan"
    }
}
