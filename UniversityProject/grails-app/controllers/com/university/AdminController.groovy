package com.university

import examinationproject.FeeType
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
    @Secured(["ROLE_ADMIN","ROLE_STUDYCENTRE","ROLE_IDOL_USER"])
    def viewProvisionalStudents() {

        def studyCenterList=StudyCenter.list(sort: 'name')
        def programList=ProgramDetail.list(sort: 'courseName')
       [studyCenterList:studyCenterList,programList:programList]
    }

    @Secured("ROLE_ADMIN")
    def viewApprovedStudents(){
        def studyCenterList=StudyCenter.list(sort: 'name')
        def programList=ProgramDetail.list(sort: 'courseName')
        [studyCenterList:studyCenterList,programList:programList]
    }

    def getStudentList(){
        def responseMap=[:]
       def stuList= adminInfoService.provisionalStudentList(params)
        responseMap.status="referenceNo"
        responseMap.label=params.pageType
        responseMap.stuList=stuList
        render responseMap as JSON

    }

    def generateRollNo(){
  
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
        def feeType = FeeType.list()
        [feeType:feeType]
    }


    def examFeeVoucher = {
        def feeType = FeeType.list()

        [feeType:feeType]
    }

    def generateFeeVoucher={

        def student = Student.findByRollNo(params.rollNo)

        if(!(student.studyCentre[0].centerCode=="11111")){
        redirect(action: "feeVoucher",params:[error:"error"])
        }
        def studyCentreType
        def semesterID = student.semester
        def program= student.programDetail
        def currentUser
        def role
        if(springSecurityService.isLoggedIn()){
            currentUser= springSecurityService.currentUser
            role = springSecurityService.getPrincipal().getAuthorities()[0]
    }

        def feeTypeId =Integer.parseInt(params.feeType)
        def feeType = FeeType.findById(feeTypeId)
        def programFee = ProgramFee.findByProgramDetail(program)
        def programFeeAmount
        if(role=="ROLE_IDOL_USER"){
            switch(feeTypeId){
                case 1:
                    programFeeAmount = programFee.feeAmountAtIDOL
                    break;
                case 2:
                    programFeeAmount = programFee.examinationFee
                    break;
                case 3:
                    programFeeAmount = programFee.certificateFee
                    break;
            }

        }

        def args = [template:"feeVoucher", model:[student:student, programFee:programFee,programFeeAmount:programFeeAmount,feeType:feeType]]
        pdfRenderingService.render(args+[controller:this],response)

    }
    def assignExaminationDate={
        def programList = ProgramDetail.list()
        [programList: programList]
    }
}

