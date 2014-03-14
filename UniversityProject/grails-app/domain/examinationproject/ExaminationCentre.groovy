package examinationproject

class ExaminationCentre {
    String location
    String name
    String inchargeName
    String address
    int capacity
    int centreCode
    String contactNo

    static hasMany = [
            student : Student
    ]
    static belongsTo = Student

    static constraints = {
        location(nullable: true)
        name(nullable: true)
        inchargeName(nullable: true)
        capacity(nullable: true)
        contactNo(nullable: true)
        centreCode(nullable:true)
    }
    static mapping = {
        student cascade:'none'
        location column: "Location"
        name column: "Name"
        inchargeName column: "InchargeName"
        address column: "Address"
        capacity column: "Capacity"
        centreCode column: "CentreCode"
        contactNo column: "ContactNo"
    }

}














