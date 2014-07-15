package examinationproject

class ProgramSession {
    ProgramDetail programDetailId
    String sessionOfProgram

    static hasMany = [semester: Semester,programGroup:ProgramGroup]
    static constraints = {
        sessionOfProgram(nullable: true)
    }


    static mapping = {
        sessionOfProgram column: "SessionOfProgram"
        programDetailId column: "ProgramDetailId"
        semester cascade:"all,delete-orphan"
        programGroup cascade:"all,delete-orphan"
    }
}
