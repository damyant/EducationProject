package examinationproject

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import universityproject.StudentRegistrationService
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

import java.security.SecureRandom


//@Secured("ROLE_STUDYCENTRE")
class StudentController {
    def studentRegistrationService
    def pdfRenderingService
    def springSecurityService
//    @Secured('ROLE_STUDYCENTRE')

    def registration= {

        def studyCentre

        if (springSecurityService.isLoggedIn()) {


            def currentUser = springSecurityService.currentUser.username
            if (springSecurityService.currentUser.studyCentreId != 0) {
                studyCentre = StudyCenter.findByEmailIdOfHeadIns(currentUser)
            } else {
                studyCentre = StudyCenter.findByCenterCode('11111')
            }

        } else {
            studyCentre = StudyCenter.findByCenterCode('11111')

        }
        def programList = ProgramDetail.list(sort: 'courseName')
        def districtList=District.list(sort: 'districtName')
        [studyCentre: studyCentre, programList: programList,districtList:districtList,registered:params.registered,studentID:params.studentID]


    }
    def viewResult = {

    }
    def submitRegistration = {
        println("in submit Registration " + params)
        def signature = request.getFile('signature')
        def photographe = request.getFile("photograph")
        def studentRegistration = studentRegistrationService.saveNewStudentRegistration(params, signature, photographe)
        if (studentRegistration) {
            flash.message = "${message(code: 'register.created.message')}"
            redirect(action: "registration", params: [ studentID: studentRegistration.id,registered:"registered"])
        } else {
            println("Cannot Register new Student")
            flash.message = "${message(code: 'register.notCreated.message')}"
            redirect(action: "registration")
        }
    }


    def applicationPrintPreview = {

        println("params" + params)
        def student = Student.findById(params.studentID)

        def args = [template: "applicationPrintPreview", model: [studentInstance: student]]
        pdfRenderingService.render(args + [controller: this], response)
        println("Student Name is " + student.name)


    }

    def showStatus() {
        try {
            def response = [:]
//        println("showing data.."+params.data)
            def refNumber = Student.findByReferenceNumber(params.data)
//        println(refNumber)
            def statusName
            if (refNumber != null) {
                def status = refNumber.statusId
                def rollNo = refNumber.rollNo
                def statusIn = Status.findById(status)
//            println(statusIn)
                statusName = statusIn.status
                response.response1 = statusName
                response.response2 = rollNo
                render response as JSON
            } else {
                render response as JSON
            }
        } catch (Exception e) {
            println("***problem in showing Status of Application***")
        }
    }

    def applicationPreview() {

    }
    def studentListView = {
        def studyCenterList=StudyCenter.list(sort: 'name')
        def programList=ProgramDetail.list(sort: 'courseName')
        [studyCenterList:studyCenterList,programList:programList]
    }


}
