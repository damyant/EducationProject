package examinationproject

class StudyCenter {
    String name
    String address
    String centerCode
    String websiteUrl
    String nameOfHeadIns
    String phoneNoOfHeadIns
    String emailIdOfHeadIns
    String nameOfCoordinator
    String phoneNoOfCoordinator
    String emailIdOfCoordinator
    City city


    static mapping = {
        id column: "StudyCenterId"
        name column: "Name"
        address column: "Address"
        centerCode column: 'CenterCode'
        websiteUrl column: 'WebsiteUrl'
        nameOfHeadIns column: 'NameOfHeadIns'
        phoneNoOfHeadIns column: 'PhoneNoOfHeadIns'
        emailIdOfHeadIns column: 'EmailIdOfHeadIns'
        nameOfCoordinator column: 'NameOfCoordinator'
        phoneNoOfCoordinator column: 'PhoneNoOfCoordinator'
        emailIdOfCoordinator column:'EmailIdOfCoordinator'
        city column: 'CityID'


    }

    static constraints = {
            name(nullable: false)
            address(nullable:false)
            centerCode(nullable: false)
            nameOfHeadIns(nullable:false)
            phoneNoOfHeadIns (nullable:false)
            emailIdOfHeadIns(email: true,nullable:false)
            nameOfCoordinator (nullable: false)
            phoneNoOfCoordinator(nullable:false)
            emailIdOfCoordinator(email: true,nullable:false)
            city(nullable:true)


    }
}


