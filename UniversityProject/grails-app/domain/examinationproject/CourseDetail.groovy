package examinationproject

class CourseDetail {

    String courseName
    int courseCode
    CourseMode courseMode
    CourseType courseType
    int noOfTerms
    int noOfAcademicYears
    int noOfPapers
    int totalMarks
    int totalCreditPoints


    static hasMany = [subject : Subject]

    static mapping = {
        id column: "CourseId"
        courseName column: "CourseName"
        courseCode column: "CourseCode"
        courseMode column: "CourseMode"
        courseType column: "CourseType"
        noOfTerms column: "NoOfTerms"
        noOfAcademicYears column: "NoOfAcademicYears"
        noOfPapers column: "NoOfPapers"
        totalMarks column: "TotalMarks"
        totalCreditPoints column: "TotalCreditPoints"
        subject column: 'SubjectId'

    }

    static constraints = {
        courseName(nullable: false)
        courseCode(nullable:false)
        courseMode(nullable: false)
        courseType(nullable: false)
        noOfTerms(nullable: false)
        noOfAcademicYears(nullable: false)
        noOfPapers(nullable: false)
        totalMarks(nullable: false)
        totalCreditPoints(nullable:false)

    }
}
