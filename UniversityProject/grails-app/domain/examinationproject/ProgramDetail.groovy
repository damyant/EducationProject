package examinationproject

class ProgramDetail {

    String courseName
    String courseCode
    ProgramType programType
    CourseMode courseMode
    CourseType courseType
    int noOfTerms
    int noOfAcademicYears
    int noOfPapers
    int totalMarks
    int marksPerPaper
    int totalCreditPoints

    static hasMany = [semester: Semester, student: Student,programSession:ProgramSession]
    static belongsTo = [Student,ProgramSession]
    static mapping = {
        id column: "CourseId"
        courseName column: "CourseName"
        courseCode column: "CourseCode"
        programType column: "ProgramType"
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
        programType(nullable:false)
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
