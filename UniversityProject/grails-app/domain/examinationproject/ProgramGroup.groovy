package examinationproject

class ProgramGroup {

    String groupName
    static belongsTo = [programSession: ProgramSession,semester:Semester]

    static constraints = {
    }

    static mapping = {
        groupName column: 'GroupName'
        programSession column: 'ProgramSessionId'
        semester column: 'SemesterId'

    }
}
