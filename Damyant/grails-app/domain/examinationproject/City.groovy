package examinationproject

class City {
    String cityName
    District district
    int isExamCentre
    static hasMany = [
            student : Student,
            examVenue:ExaminationVenue
    ]

    static belongsTo = Student
    static constraints = {
        cityName(nullable: false)
      }


    static mapping = {
        id column: 'CityId'
        district column: 'DistrictId'
        cityName column: 'CityName'
        isExamCentre column: 'isExamCentre'
        isExamCentre defaultValue: false
    }
}
