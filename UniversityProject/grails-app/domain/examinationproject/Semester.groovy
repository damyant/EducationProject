package examinationproject

class Semester {
    int semesterNo

    static belongsTo = [courseDetail: ProgramDetail]

    static mapping = {
        id column :"SemesterId"
        semesterNo column: "SemesterNo"
        courseDetail column: "CourseDetailId"
    }

    static constraints = {
        semesterNo(nullable: false)

    }
}