package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import universityproject.StudentRegistrationService
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import java.security.SecureRandom


//@Secured("ROLE_STUDYCENTRE")
class StudentController {
 def studentRegistrationService
    def springSecurityService
//    @Secured('ROLE_STUDYCENTRE')
    def registration= {
        println("inside registration")
        if(springSecurityService.isLoggedIn()){


            def currentUser= springSecurityService.currentUser.username
            if(springSecurityService.currentUser.studyCentreId!=0){
            def studyCentre= StudyCenter.findByEmailIdOfHeadIns(currentUser)

              [studyCentre:studyCentre]
            }
            else{
                def studyCentre= StudyCenter.findByCenterCode('11111')
                println("<><>><<><><><<><><>>><><>><<<<<<<<>>>"+studyCentre.name)
                [studyCentre:studyCentre]
            }

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
            def rollNo=refNumber.rollNo
            def statusIn=Status.findById(status)
//            println(statusIn)
            statusName=statusIn.status
            response.response1=statusName
            response.response2=rollNo
            render response as JSON
        }
        else{
            render response as JSON
        }
      } catch(Exception e) {
           println("***problem in showing Status of Application***")
        }
    }
    def applicationPreview(){

    }

}
