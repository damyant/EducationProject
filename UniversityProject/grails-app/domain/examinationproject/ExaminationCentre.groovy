package examinationproject

import examinationproject.District

class ExaminationCentre {
    String examinationCentreName
    District district

    static hasMany = [
            student : Student,
            examVenue:ExaminationVenue
    ]
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
