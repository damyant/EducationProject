package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import universityproject.StudentRegistrationService
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import java.security.SecureRandom

<<<<<<< HEAD
//@Secured("ROLE_STUDYCENTRE")
=======

>>>>>>> a65bf1032858ed0c76bfd024a2decea34406eb64
class StudentController {
 def studentRegistrationService
    def springSecurityService
//    @Secured('ROLE_STUDYCENTRE')
    def registration= {
        println("inside registration")
        if(springSecurityService.isLoggedIn()){
            def currentUser= springSecurityService.currentUser.username

            def studyCentre= StudyCenter.findByEmailIdOfHeadIns(currentUser)

              [studyCentre:studyCentre]

        }
        else{
             println("user is not logged in")
            def studyCentre= StudyCenter.findByCenterCode('11111')
            println("<><>><<><><><<><><>>><><>><<<<<<<<>>>"+studyCentre.name)
            [studyCentre:studyCentre]
        }

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


    def getReferenceNumber={
        println("Reference number is:"+studentRegistrationService.getReferenceNumber())
    }

    def showStatus(){
        try{
        def response = [:]
//        println("showing data.."+params.data)
        def refNumber=Student.findByReferenceNumber(params.data)
//        println(refNumber)
        def statusName
        if(refNumber!=null){
            def status=refNumber.statusId
//            println(status)
            def statusIn=Status.findById(status)
//            println(statusIn)
            statusName=statusIn.status
            println(statusName)
//            render statusName
            response.response1=statusName
            render response as JSON
        }
        else{
            render response as JSON
        }
      } catch(Exception e) {
           println("***problem in showing Status of Application***")
        }
    }


}
