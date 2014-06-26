package examinationproject

import com.university.Role

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
