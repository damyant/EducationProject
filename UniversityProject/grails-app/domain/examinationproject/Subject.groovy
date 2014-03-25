package examinationproject

class Subject {

    int subjectCode
    String subjectName


    static mapping = {
        id column: "SubjectId"
        subjectName column: "SubjectName"
        subjectCode column: "SubjectCode"

    }

    static constraints = {
        subjectName(nullable: false)
        subjectCode(nullable:false)


    }
}
