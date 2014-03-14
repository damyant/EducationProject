package examinationproject

class CourseMode {

    String modeName

    static constraints = {

    }

    static mapping = {
        id column: "ModeId"
        modeName column: "ModeName"
      }
}
