package examinationproject

class HomeAssignment {

    Student student
    Date submissionDate = new Date()
    int semester

    static constraints = {
        student(nullable: false)
        semester(nullable:false)
        submissionDate(nullable: false)
    }
}
