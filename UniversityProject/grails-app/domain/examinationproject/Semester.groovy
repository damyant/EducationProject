package examinationproject

class Semester {
    int semesterNo

    static belongsTo = [programSession: ProgramSession]

    static mapping = {
        id column :"SemesterId"
        semesterNo column: "SemesterNo"
        programSession column: "ProgramSessionId"
    }

    static constraints = {
        semesterNo(nullable: false)

    }
}