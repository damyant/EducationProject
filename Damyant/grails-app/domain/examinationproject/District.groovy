package examinationproject

class District {
    String districtName
    Integer stateId

    static constraints = {
        districtName(nullable: false)
    }



    static mapping = {
        id column: 'DistrictId'
        stateId column: 'StateId'
        districtName column: 'DistrictName'

    }


}
