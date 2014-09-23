package IST

class Employee {
    String firstName
    String middleName
    String lastName
    Date dob
    String gender
    BigInteger mobileNo
    String designation
    String employeeCode
    String panNumber
    String currentAddress
    String permanentAddress
    Date dateOfJoining

    static constraints = {
        firstName(nullable:true)
        middleName(nullable:true)
        lastName(nullable:true)
        dob(nullable:true)
        gender(nullable:true)
        mobileNo(nullable:true)
        designation(nullable:true)
        employeeCode(nullable:true,unique: true)
        panNumber(nullable:true)
        currentAddress(nullable:true)
        permanentAddress(nullable:true)
        dateOfJoining(nullable:true)
    }

    static mapping = {

        firstName column: 'FirstName'
        middleName column: 'MiddleName'
        lastName column: 'LastName'
        dob column: 'DOB'
        gender column: 'Gender'
        mobileNo column: 'MobileNo'
        designation column: 'Designation'
        employeeCode column: 'EmployeeCode'
        panNumber column: 'PanNumber'
        currentAddress column: 'CurrentAddress'
        permanentAddress column: 'PermanentAddress'
        dateOfJoining column: 'DateOfJoining'
    }
}
