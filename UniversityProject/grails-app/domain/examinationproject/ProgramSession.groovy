package examinationproject

class ProgramSession {
    String sessionOfProgram
    static constraints = {
        sessionOfProgram(nullable: true)
    }

    static hasMany = [
            programDetail : ProgramDetail
    ]
    static belongsTo = ProgramDetail
    static mapping = {
        sessionOfProgram column: "SessionOfProgram"
    }
}
