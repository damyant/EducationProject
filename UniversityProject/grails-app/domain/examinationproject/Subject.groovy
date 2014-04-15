package examinationproject

class Subject {

    int subjectCode
    String subjectName
    Date examDate


    static mapping = {
        id column: "SubjectId"
        subjectName column: "SubjectName"
        subjectCode column: "SubjectCode"
        examDate column: "ExamDate"

    }

    static constraints = {
        subjectName(nullable: false)
        subjectCode(nullable:false)
        examDate(nullable: true)


    }
}
