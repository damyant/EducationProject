package postexamination

import com.university.Role
import examinationproject.Student

class StudentMarks {

    int semesterNo
    int marksObtained
    Role roleId

    static belongsTo = [student:Student]

    static constraints = {
    }
}
