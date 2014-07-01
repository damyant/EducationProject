package postexamination

import examinationproject.Subject
import postexamination.MarksType

class SubjectMarksDetail {

    Integer marks
    Integer minPassingMarks
    MarksType marksTypeId

    static  belongsTo = [subject:Subject]

    static constraints = {
    }

    static mapping = {
     subject column: 'SubjectId'
     marks column: 'Marks'
     minPassingMarks column: 'MinPassingMarks'
     marksTypeId column: 'MarksTypeId'
    }
}
