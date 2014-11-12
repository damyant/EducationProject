package postexamination


import examinationproject.SubjectSession
import postexamination.MarksType

class SubjectMarksDetail {

    Boolean theory = false
    Boolean home = false
    Boolean practical = false
    Boolean project = false

    static belongsTo = [subjectSession: SubjectSession]

    static constraints = {
    }

    static mapping = {
        subjectSession column: 'SubjectSessionId'
        theory column: 'theory'
        home column: 'home'
        practical column: 'practical'
        project column: 'project'
    }
}
