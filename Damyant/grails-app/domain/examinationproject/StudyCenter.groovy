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
    String asstCoordinator
    String asstMobile
    String asstEmail
    int isExamVenue

    City city
    static hasMany = [student : Student]
    static belongsTo = Student

    static mapping = {
        student cascade:'none'
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
        asstCoordinator column: "asstCoordinator"
        asstMobile column: "asstMobile"
        asstEmail column: "asstEmail"
        isExamVenue defaultValue: 1
    }

    static constraints = {
            name(nullable: false)
            address(nullable:false)
            centerCode(nullable: false, unique: true)
            nameOfHeadIns(nullable:false)
            phoneNoOfHeadIns (nullable:false)
            emailIdOfHeadIns(email: true,nullable:false)
            nameOfCoordinator (nullable: false)
            phoneNoOfCoordinator(nullable:false)
            emailIdOfCoordinator(email: true,nullable:false)
            city(nullable:true)
            asstCoordinator(nullable: true)
            asstMobile (nullable: true)
            asstEmail(nullable: true)
    }

    def beforeDelete = {
        this.student.each {
            it.removeFromStudyCentre(this)
        }
    }
}


