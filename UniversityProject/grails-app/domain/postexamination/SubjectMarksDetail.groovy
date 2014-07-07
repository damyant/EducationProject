package postexamination


import examinationproject.SubjectSession
import postexamination.MarksType

class SubjectMarksDetail {

    Integer marks
    Integer minPassingMarks
    MarksType marksTypeId

    static belongsTo = [subjectSession: SubjectSession]

    static constraints = {
    }

    static mapping = {
     subjectSession column: 'SubjectSessionId'
     marks column: 'Marks'
     minPassingMarks column: 'MinPassingMarks'
     marksTypeId column: 'MarksTypeId'
    }
}
