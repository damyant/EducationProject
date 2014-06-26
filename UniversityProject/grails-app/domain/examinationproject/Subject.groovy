package examinationproject

import postexamination.SubjectMarksDetail

class Subject {

    String subjectCode
    String subjectName
    String aliasCode
    Integer creditPoints
//    Integer theoryMarks
//    Integer homeAssignmentMarks
//    Integer practicalMarks
//    Integer totalMarks
    ProgramType programTypeId

    static hasMany = [subjectMarksDetail:SubjectMarksDetail]

    static mapping = {
        id column: "SubjectId"
        subjectName column: "SubjectName"
        subjectCode column: "SubjectCode"
        aliasCode column: "AliasCode"
        creditPoints column: 'CreditPoints'
//        theoryMarks column: 'TheoryMarks'
//        practicalMarks column: 'PracticalMarks'
//        homeAssignmentMarks column: 'HomeAssignmentMarks'
//        totalMarks column: 'TotalMarks'
        programTypeId column: "ProgramTypeId"



    }

    static constraints = {
        subjectName(nullable: false)
        subjectCode (nullable:false, unique: true)
        aliasCode (nullable: false)
        creditPoints (nullable: false)
//        theoryMarks (nullable: false)
//        practicalMarks (nullable: true)
//        homeAssignmentMarks (nullable: false)
//        totalMarks (nullable: true)
        programTypeId (nullable: false)

    }
}
