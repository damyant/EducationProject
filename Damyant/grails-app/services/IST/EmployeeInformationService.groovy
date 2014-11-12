package IST

import grails.transaction.Transactional

import java.text.DateFormat
import java.text.SimpleDateFormat

@Transactional
class EmployeeInformationService {

    def serviceMethod() {

    }
    def saveEmployeeInformationData(params){
        if(params.employeeId){
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy")
            def employeeObj=Employee.get(params.employeeId)
            employeeObj.firstName=params.firstName
            employeeObj.middleName=params.middleName
            employeeObj.lastName=params.lastName
            employeeObj.employeeCode=params.employeeCode
            employeeObj.currentAddress=params.currentAddress
            employeeObj.permanentAddress=params.permanentAddress
            employeeObj.dateOfJoining=df.parse(params.dateOfJoining)
            employeeObj.designation=params.designation
            employeeObj.gender=params.gender
            employeeObj.mobileNo=Long.parseLong(params.mobileNo)
            employeeObj.dob=df.parse(params.dob)
            employeeObj.panNumber=params.panNumber
            employeeObj.save(failOnError: true)
        }
        else{
            def employeeObj=new Employee(params)
            employeeObj.save(failOnError: true)
        }
    }
}
