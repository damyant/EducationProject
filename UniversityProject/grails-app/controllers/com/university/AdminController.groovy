package com.university

import examinationproject.ProgramDetail
import examinationproject.ProgramFee
import examinationproject.Semester
import examinationproject.Student
import examinationproject.StudyCenter
import grails.converters.JSON
import grails.plugins.springsecurity.Secured



class AdminController {

    def adminInfoService
    def pdfRenderingService
    def studentRegistrationService
    def springSecurityService
    @Secured(["ROLE_ADMIN","ROLE_STUDYCENTRE"])
    def viewProvisionalStudents() {

        def studyCenterList=StudyCenter.findAll()
        def programList=ProgramDetail.findAll()
       [studyCenterList:studyCenterList,programList:programList]
    }

    @Secured("ROLE_ADMIN")
    def viewApprovedStudents(){
        def studyCenterList=StudyCenter.findAll()
        def programList=ProgramDetail.findAll()
        [studyCenterList:studyCenterList,programList:programList]
    }

    def getStudentList(){
    println("<<<<<<<"+params)
        def responseMap=[:]
       def stuList= adminInfoService.provisionalStudentList(params)
        responseMap.status="referenceNo"
        responseMap.label=params.pageType
        responseMap.stuList=stuList
        render responseMap as JSON

    }

    def generateRollNo(){
    println("????????????"+params)
        def stuList=[]
        def responseMap=[:]
        def status
        if(params.pageType=="Approve RollNo"){
            status= studentRegistrationService.approvedStudents(params)
        }
        else{
         status=studentRegistrationService.getStudentRollNumber(params)
        }

        println(status)
        if(status==true){
        stuList= adminInfoService.provisionalStudentList(params)

        }
        responseMap.status='rollNo'
        responseMap.stuList=stuList
        render responseMap as JSON
        render stuList as JSON
    }

    def feeVoucher={

    }

    def generateFeeVoucher={

        def student = Student.findByRollNo(params.rollNo)
        def studyCentreType
        def semesterID = student.semester
        def semester = Semester.findById(semesterID)
        def program= student.programDetail
        def studyCenter = student.studyCentre
        def currentUser= springSecurityService.currentUser
        def role = springSecurityService.getPrincipal().getAuthorities()[0]
        println("Current user Role is "+role)
        if(role=="ROLE_ADMIN"){
            studyCentreType="IDOL"
        }else{
            studyCentreType="ST"
        }


        def programFee = ProgramFee.findByProgramDetailAndSemesterAndStudyCentreType(program,semester,studyCentreType,[s:'s'])
        println("Program Fee Amount"+programFee)
//        def obj=ProgramFee.createCriteria()
//        def programFee= obj.list{
//            programFee{
//                eq('programDetail', program)
//            }
//            and{
//                eq('semester',semester)
//            }
//            and{
//                eq('studyCentreType',studyCentreType)
//            }
//            maxResults(1)
//
//        }

        def args = [template:"feeVoucher", model:[student:student, programFee:programFee]]
        pdfRenderingService.render(args+[controller:this],response)

    }
}

