package examinationproject

class ExaminationVenue {
    String name
    String inchargeName
    String address
    int capacity
    int centreCode
    String contactNo

    static belongsTo = ExaminationCentre

    static constraints = {
        name(nullable: true)
        inchargeName(nullable: true)
        capacity(nullable: true)
        contactNo(nullable: true)
        centreCode(nullable:true,unique: true)
    }
    static mapping = {
//        student cascade:'none'
        name column: "Name"
        inchargeName column: "InchargeName"
        address column: "Address"
        capacity column: "Capacity"
        centreCode column: "CentreCode"
        contactNo column: "ContactNo"
    }

}














