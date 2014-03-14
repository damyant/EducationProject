package examinationproject

import java.sql.Blob

class Student {

    String name
    Date dob
    String program
    String category
    String gender
    String nationality
    String state
    BigInteger mobileNo
    int studyCentre
    int preferenceOfExaminationCentre
    BigInteger registrationNo1
    BigInteger registrationNo2
    String addressStudentName
    String addressVillage
    String addressPO
    String addressDistrict
    String addressState
    int addressPinCode
    String location
    byte[] studentImage
//    byte[] studentSignature

    static hasMany = [examinationCentre : ExaminationCentre]

    static constraints = {
        name(nullable: true)
        dob(nullable: true)
        program(nullable: true)
        category(nullable: true)
        gender(nullable: true)
        nationality(nullable: true)
        state(nullable: true)
        mobileNo(nullable: true)
        studyCentre(nullable: true)
        preferenceOfExaminationCentre(nullable: true)
        registrationNo1(nullable: true)
        registrationNo2(nullable: true)
        addressStudentName(nullable: true)
        addressVillage(nullable: true)
        addressPO(nullable: true)
        addressDistrict(nullable: true)
        addressState(nullable: true)
        addressPinCode(nullable:true)
        studentImage(nullable: true)
        location(nullable: true)
//        studentSignature(nullable: true)
    }
    static mapping ={
        studentImage column: "studentImage", sqlType: "blob"
//        studentSignature column: "studentSignature", sqlType: "blob"
//        examinationCentre cascade:'none'
        name column: "Name"
        dob column: "Dob"
        category column: "Category"
        gender column: "Gender"
        nationality column: "Nationality"
        state column: "State"
        mobileNo column : "MobileNo"
        studyCentre column: "StudyCentre"
        preferenceOfExaminationCentre column: "PreferenceOfExaminationCentre"
        registrationNo1 column: "RegistrationNo1"
        registrationNo2 column: "RegistrationNo2"
        addressStudentName column: "AddressStudentName"
        addressVillage column: "AddressVillage"
        addressPO column: "AddressPO"
        addressDistrict column: "AddressDistrict"
        addressState column: "AddressState"
        addressPinCode column: "AddressPinCode"
        location column: "Location"
    }

}