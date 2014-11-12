package examinationproject

class ProgramGroup {

    String groupName
    String groupSelectionType
    int numberOfSubjectsToSelect
    static belongsTo = [programSession: ProgramSession,semester:Semester]

    static constraints = {
        numberOfSubjectsToSelect(nullable: true)
    }

    static mapping = {
        groupName column: 'GroupName'
        programSession column: 'ProgramSessionId'
        semester column: 'SemesterId'
        groupSelectionType column: 'GroupSelectionType'
        numberOfSubjectsToSelect column: 'NumberOfSubjectsToSelect'
    }
}
