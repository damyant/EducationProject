package examinationproject

class RollNo {
    String nameOfApplicant
    String course
    String session
    int omrFormNo
    String contactNo
    String examinationCentre
    static constraints = {
        nameOfApplicant(nullable: true)
        course(nullable: true)
        session(nullable: true)
        omrFormNo(nullable:true)
        contactNo(nullable: true)
        examinationCentre(nullable: true)
    }
}
