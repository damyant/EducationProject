package examinationproject

import java.text.SimpleDateFormat

class Student {

    String studentName
    Date dob
    String category
    String gender
    String nationality
    String state
    BigInteger mobileNo
    BigInteger registrationNo1
    BigInteger registrationNo2
    String addressStudentName
    String addressTown
    String addressPO
    String addressDistrict
    String addressState
    String addressPinCode
    String location
    String applicationNo
    int rollNo
    int semester
    Status status
    int registrationYear
    int referenceNumber
    byte[] studentImage
    ProgramSession programSession
    Boolean admitCardGenerated
    Date admissionDate =new Date()


    static hasMany = [examinationCentre : ExaminationCentre, studyCentre : StudyCenter,programDetail:ProgramDetail]

    static constraints = {
        referenceNumber(nullable:true)
        studentName(nullable: true)
        dob(nullable: true)
        //programDetail(nullable: false)
        category(nullable: true)
        gender(nullable: true)
        nationality(nullable: true)
        state(nullable: true)
        mobileNo(nullable: true)
//        studyCentre(nullable: true)

//        preferenceOfExaminationCentre(nullable: true)
        registrationNo1(nullable: true)
        registrationNo2(nullable: true)
        addressStudentName(nullable: true)
        addressTown(nullable: true)
        addressPO(nullable: true)
        addressDistrict(nullable: true)
        addressState(nullable: true)
        addressPinCode(nullable:true)
        studentImage(nullable: true)
        location(nullable: true)
        applicationNo(nullable: true)
        rollNo(nullable:true)
        status(nullable:true)
        admitCardGenerated(nullable: true)





//        studentSignature(nullable: true)
    }
    static mapping ={
        studentImage column: "studentImage", sqlType: "blob"
//        studentSignature column: "studentSignature", sqlType: "blob"
        studyCentre cascade:'none'
        programDetail cascade: 'none'
        examinationCentre cascade:'none'
        studentName column: "studentName"
        dob column: "Dob",index: 'Dob_Index'
        rollNo column: "RollNo",index: 'RollNo_Index'

        category column: "Category"
        gender column: "Gender"
        nationality column: "Nationality"
        state column: "State"
        mobileNo column : "MobileNo"
        studyCentre column: "StudyCentre"
//        preferenceOfExaminationCentre column: "PreferenceOfExaminationCentre"
        registrationNo1 column: "RegistrationNo1"
        registrationNo2 column: "RegistrationNo2"
        addressStudentName column: "AddressStudentName"
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


    }

}
