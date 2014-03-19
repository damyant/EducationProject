package examinationproject

class Subject {

    String subjectName


    static mapping = {
        id column: "SubjectId"
        subjectName column: "SubjectName"

    }

    static constraints = {
        subjectName(nullable: false)


    }
}
