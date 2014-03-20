package examinationproject

import universityproject.StudentRegistrationService
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
class StudentController {
 def studentRegistrationService
    def springSecurityService

    def registration= {
        println("inside registration")


    }
    def viewResult={

    }
    def submitRegistration={
        println("in submit Registration "+ params)
        def signature= request.getFile('signature')
        def photographe= request.getFile("photograph")
        def flag = studentRegistrationService.saveNewStudentRegistration(params, signature, photographe)
        if(flag==true){
           println("New Student Registered Successfully")
            flash.message = "${message(code: 'register.created.message')}"
            redirect(action: "registration")
        }
        else{
            println("Cannot Register new Student")
            flash.message = "${message(code: 'register.notCreated.message')}"
            redirect(action: "registration")
        }
    }
}
