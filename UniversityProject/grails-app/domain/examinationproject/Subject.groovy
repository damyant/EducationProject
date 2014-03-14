package examinationproject

class Subject {

    String subjectName

    static belongsTo = CourseDetail

    static hasMany = [courseDetail : CourseDetail]



    static mapping = {
        id column: "SubjectId"
        subjectName column: "SubjectName"
        courseDetail column: "CourseDetailId"

    }

    static constraints = {
        subjectName(nullable: false)


    }
}
