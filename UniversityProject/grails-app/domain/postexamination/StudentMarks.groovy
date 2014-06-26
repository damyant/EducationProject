package postexamination

import com.university.Role
import examinationproject.Student
import examinationproject.Subject

class StudentMarks {

    Subject subjectId
    int semesterNo
    int marksObtained
    Role roleId
    static belongsTo = [student:Student]

    static constraints = {
    }

    static mapping ={
        student column: "studentId"
    }
}
