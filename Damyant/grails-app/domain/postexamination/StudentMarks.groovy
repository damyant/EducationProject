package postexamination

import com.university.Role
import examinationproject.Student
import examinationproject.Subject
class StudentMarks {
    Subject subjectId
    int semesterNo
    Role roleId
    String theoryMarks
    String practicalMarks
    String homeAssignmentMarks
    String project
    String totalMarks
    String isCorrect
    Boolean isAbsent=false
    Boolean isPassed=false
    static belongsTo = [student:Student]
    static constraints = {
        theoryMarks(nullable: true)
        homeAssignmentMarks(nullable: true)
        isCorrect(nullable: true)
        practicalMarks(nullable: true)
        totalMarks(nullable: true)
        project(nullable: true)
    }
    static mapping ={
        subjectId column: 'SubjectId'
        semesterNo column: 'SemesterNo'
        isCorrect column: 'isCorrect'
        student column: "StudentId"
        theoryMarks column: "theoryMarks"
        roleId column: "RoleId"
        homeAssignmentMarks column: "homeAssignmentMarks"
        practicalMarks column: "practicalMarks"
        totalMarks column: "totalMarks"
        isAbsent column: "isAbsent"
        isPassed column: "isPassed"
    }
}
