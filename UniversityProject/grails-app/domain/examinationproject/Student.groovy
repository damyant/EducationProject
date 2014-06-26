package examinationproject


class Student {

    String firstName
    String middleName
    String lastName
    Date dob
    String category
    String gender
    String nationality
    String state
    BigInteger mobileNo
    String registrationNo1
    String registrationNo2
    String studentAddress
    String addressTown
    String addressPO
    String isAppliedFor
    String addressDistrict
    String addressState
    String addressPinCode
    String location
    String applicationNo
    String parentsName
    String rollNo
    int semester
    Status status
    int registrationYear
    String referenceNumber
    byte[] studentImage
    ProgramSession programSession
    Boolean admitCardGenerated
    ExaminationVenue examinationVenue
    Date admissionDate =new Date()
    
    String challanNo


    static hasMany = [city : City, studyCentre : StudyCenter,programDetail:ProgramDetail,studentMarks:StudentMarks]

    static constraints = {
        referenceNumber(nullable:true)
        middleName(nullable: true)
        parentsName(nullable: true)
        dob(nullable: true)
        isAppliedFor(nullable: true)
        category(nullable: true)
        gender(nullable: true)
        nationality(nullable: true)
        state(nullable: true)
        mobileNo(nullable: true)
        registrationNo1(nullable: true)
        registrationNo2(nullable: true)
        studentAddress(nullable: true)
        addressTown(nullable: true)
        addressPO(nullable: true)
        addressDistrict(nullable: true)
        addressState(nullable: true)
        addressPinCode(nullable:true)
        studentImage(nullable: true)
        location(nullable: true)
        applicationNo(nullable: true)
        rollNo(nullable:true,unique: true)
        status(nullable:true)
        admitCardGenerated(nullable: true)
        examinationVenue(nullable: true)
        challanNo(nullable: true)


    }

    static mapping ={
        studentImage column: "studentImage", sqlType: "blob"
        studyCentre cascade:'none'
        programDetail cascade: 'none'
//        examinationCentre cascade:'none'
        firstName column: "firstName"
        isAppliedFor column: "isAppliedFor"
        lastName column: "lastName"
        middleName column: "middleName"
        parentsName column: "parentsName"
        dob column: "Dob",index: 'Dob_Index'
        rollNo column: "RollNo",index: 'RollNo_Index'
        category column: "Category"
        gender column: "Gender"
        nationality column: "Nationality"
        state column: "State"
        mobileNo column : "MobileNo"
        studyCentre column: "StudyCentre"
        registrationNo1 column: "RegistrationNo1"
        registrationNo2 column: "RegistrationNo2"
        studentAddress column: "studentAddress"
        addressTown column: "AddressTown"
        addressPO column: "AddressPO"
        addressDistrict column: "AddressDistrict"
        addressState column: "AddressState"
        addressPinCode column: "AddressPinCode"
        location column: "Location"
        applicationNo column: "applicationNo"
        registrationYear column: "RegistrationYear"
        referenceNumber column:"ReferenceNumber"
        status column: 'StatusId'
        programSession column: 'ProgramSession'
        admitCardGenerated column: 'AdmitCardGenerated'
        examinationVenue column: 'examinationVenue'
        challanNo column: 'ChallanNo'
    }

}
