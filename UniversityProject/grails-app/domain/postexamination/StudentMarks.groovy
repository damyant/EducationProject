package postexamination

import com.university.Role
import examinationproject.Student
import examinationproject.Subject

class StudentMarks {

    Subject subjectId
    int semesterNo
    int marksObtained
    Role roleId
    MarksType marksTypeId
    static belongsTo = [student:Student]

    static constraints = {
    }

    static mapping ={
        subjectId column: 'SubjectId'
        semesterNo column: 'SemesterNo'
        marksObtained column: 'MarksObtained'
        student column: "StudentId"
        marksTypeId column: "MarksTypeId"
        roleId column: "RoleId"
    }
}
