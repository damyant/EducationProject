package examinationproject

class MiscellaneousFeeChallan {

    String challanNo
    FeeType feeType
    Student student
    int semesterValue

    static constraints = {
    }
    static mapping = {
        challanNo column: "challanNo"
        feeType column: "feeTypeId"
        student column: "studentId"
        semesterValue column: "semesterValue"
    }
}
