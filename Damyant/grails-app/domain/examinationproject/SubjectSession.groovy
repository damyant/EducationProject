package examinationproject

import postexamination.SubjectMarksDetail

class SubjectSession {
    Subject subjectId
    String sessionOfSubject

    static hasMany = [subjectMarksDetail:SubjectMarksDetail]
    static constraints = {
        sessionOfSubject(nullable: true)
    }


    static mapping = {
        sessionOfSubject column: "SessionOfProgram"
        subjectId column: "SubjectId"
        subjectMarksDetail cascade:"all,delete-orphan"
    }
}