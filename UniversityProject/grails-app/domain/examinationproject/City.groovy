package examinationproject

class City {
    String cityName
    District district

    static constraints = {
        cityName(nullable: false)
      }


    static mapping = {
        id column: 'CityId'
        district column: 'DistrictId'
        cityName column: 'CityName'

    }
}
