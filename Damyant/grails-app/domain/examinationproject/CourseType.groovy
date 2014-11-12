package examinationproject

class CourseType {

    String courseTypeName

    static constraints = {
    }

    static mapping = {
        id column: "CourseTypeId"
        courseTypeName column: "courseTypeName"
    }
}
