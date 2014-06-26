package examinationproject

class SubjectMarksDetail {

    Integer marksObtained
    Integer minPassingMarks
    MarksType marksTypeId

    static  belongsTo = [subject:Subject]

    static constraints = {
    }

    static mapping = {
     subject column: 'SubjectId'
     marksObtained column: 'MarksObtained'
     minPassingMarks column: 'MinPassingMarks'
     marksTypeId column: 'MarksTypeId'
    }
}
