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
    int marksPerPaper
    int totalCreditPoints

    static hasMany = [semester: Semester]

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
        marksPerPaper column: "MarksPerPaper"
        totalCreditPoints column: "TotalCreditPoints"
        semester cascade:"all,delete-orphan"

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
        marksPerPaper(nullable: false)
        totalCreditPoints(nullable:false)

    }
}
