package examinationproject

import postexamination.SubjectMarksDetail

class Subject {

    String subjectCode
    String subjectName
    String aliasCode
    Integer creditPoints
    ProgramType programTypeId

//    static hasMany = [subjectMarksDetail:SubjectMarksDetail]

    static mapping = {
//        id column: "SubjectId"
        subjectName column: "SubjectName"
        subjectCode column: "SubjectCode"
        aliasCode column: "AliasCode"
        creditPoints column: 'CreditPoints'
//        totalMarks column: 'TotalMarks'
        programTypeId column: "ProgramTypeId"



    }

    static constraints = {
        subjectName(nullable: false)
        subjectCode (nullable:false, unique: true)
        aliasCode (nullable: false)
        creditPoints (nullable: false)
        programTypeId (nullable: false)

    }
}
