package examinationproject

class Subject {

    String subjectCode
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
        subjectCode(nullable:true)
        programTypeId (nullable: false)

    }
}
