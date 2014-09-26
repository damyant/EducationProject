package IST

import grails.converters.JSON

import java.text.DateFormat
import java.text.SimpleDateFormat

class EmployeController {
def employeeInformationService

    def index() {}
    def createEmployee(){
        if(params.id){
            def employeeObj=Employee.get(params.id)
            [employeeObj:employeeObj]
        }

    }
    def saveEmployee(){

        employeeInformationService.saveEmployeeInformationData(params)
        if(params.employeeId){
            redirect(action: "listOfEmployee")
        }
        else{
            redirect(action: "createEmployee")
        }

    }

    def listOfEmployee(){
        def employeeList=Employee.list()
        [employeeList:employeeList]

    }

    def viewEmployeeDetail(){
      def employeeObj=Employee.get(params.id)
       [employeeObj: employeeObj]
    }

    def deleteEmployee()
    {
        def returnMap=[:]
        def empObj=Employee.get(params.empId)
        empObj.delete(failOnError:true,flush: true)
        if(Employee.exists(params.empId)){
            returnMap.status=false
        }else{
            returnMap.status=true
        }
        render returnMap as JSON
    }
}
