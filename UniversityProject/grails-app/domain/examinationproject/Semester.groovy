package examinationproject

class Semester {
    int semesterNo
    String description

    static mapping = {
        id column :"SemesterId"
        semesterNo column: "SemesterNo"
        description column: "Description"

    }

    static constraints = {
        semesterNo(nullable: false)
        description(nullable: true)
    }
}
