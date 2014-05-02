package examinationproject

import examinationproject.District

class ExaminationCentre {
    String name
    String inchargeName
    String address
    int capacity
    int centreCode
    String contactNo
    City city
    static hasMany = [student : Student,examVenue:ExaminationVenue]
    String examinationCentreName
    District district

    static belongsTo = Student

    static constraints = {
        examinationCentreName(nullable: false)
    }

    static mapping = {
        id column: 'examinationCentreId'
        district column: 'DistrictId'
        examinationCentreName column: 'examinationCentreName'
    }
}
