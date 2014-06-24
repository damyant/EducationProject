package examinationproject

class Subject {

    String subjectCode
    String subjectName
    String aliasCode
    ProgramType programTypeId

    static hasMany = [marksCategory: MarksCategory]

    static mapping = {
        id column: "SubjectId"
        subjectName column: "SubjectName"
        subjectCode column: "SubjectCode"
        aliasCode column: "aliasCode"
        programTypeId column: "ProgramTypeId"



    }

    static constraints = {
        subjectName(nullable: false)
        subjectCode (nullable:true)
        aliasCode (nullable: true)
        programTypeId (nullable: false)

    }
}
