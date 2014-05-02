package examinationproject

class Subject {

    int subjectCode
    String subjectName
    Date examDate
    String examTime

    static mapping = {
        id column: "SubjectId"
        subjectName column: "SubjectName"
        subjectCode column: "SubjectCode"
        examDate column: "ExamDate"
        examTime column: "ExamTime"

    }

    static constraints = {
        subjectName(nullable: false)
        subjectCode(nullable:false)
        examDate(nullable: true)
        examTime(nullable: true)
    }
}
