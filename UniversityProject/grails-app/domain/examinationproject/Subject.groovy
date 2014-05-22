package examinationproject

class Subject {

    int subjectCode
    String subjectName
    ProgramType programTypeId

    static mapping = {
        id column: "SubjectId"
        subjectName column: "SubjectName"
        subjectCode column: "SubjectCode"
        programTypeId column: "ProgramTypeId"


    }

    static constraints = {
        subjectName(nullable: false)
        subjectCode(nullable:false)
        programTypeId (nullable: true)

    }
}
