package com.university

import examinationproject.District
import examinationproject.ExaminationCentre
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
    @Secured("ROLE_ADMIN")
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

        if(status==true){
        stuList= adminInfoService.provisionalStudentList(params)

        }
        responseMap.status='rollNo'
        responseMap.stuList=stuList
        render responseMap as JSON
        render stuList as JSON
    }

    @Secured(["ROLE_ADMIN","ROLE_IDOL_USER"])
    def feeVoucher={
        def feeType = FeeType.list(sort:'type')
        def selectFeeType=FeeType.findAllById(1)
        [feeType:feeType,selectFeeType:selectFeeType]
    }


    def examFeeVoucher = {
        def feeType = FeeType.list(sort:'type')

        [feeType:feeType]
    }
    @Secured(["ROLE_ADMIN","ROLE_IDOL_USER"])
    def generateFeeVoucher={
        println("dsdsdsdsd"+Student.findByRollNo(params.rollNo)+""+params.feeType)
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
       // if(role=="ROLE_IDOL_USER"){
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

        //}

        def args = [template:"feeVoucher", model:[student:student, programFee:programFee,programFeeAmount:programFeeAmount,feeType:feeType]]
        pdfRenderingService.render(args+[controller:this],response)

    }

    @Secured("ROLE_ADMIN")
    def assignExaminationDate={
        def programList = ProgramDetail.list(sort:'courseName')
        [programList: programList]
    }
    @Secured("ROLE_ADMIN")
    def assignExaminationVenue={
        def programList = ProgramDetail.list(sort:'courseName')
        def examinationCenter=ExaminationCentre.list()*.city as Set
        def finalExaminationCenterList= examinationCenter.sort{a,b->
            a.cityName<=>b.cityName
        }

        [programList: programList,examinationCenterList:finalExaminationCenterList]
    }

    def getSubjectList={
        def subMap=[:]
       subMap= adminInfoService.subjectList(params)

        if(subMap.allSubjects.size()<1){
            subMap.noSubjects=true
            render subMap as JSON
        }
        else{
            render subMap as JSON
        }
    }

    def saveExamDate={
        def checkStatus=[:]
        def status=adminInfoService.saveExamDate(params)

        if(status.size()>1){
            checkStatus.saveFlag=true
        }
        else{
            checkStatus.saveFlag=false
        }
        render checkStatus as JSON

    }

    def saveExamVenue={

        def status=adminInfoService.saveExamVenue(params)
        println("here is the status"+status)
        if(status){
            render status
        }
        else{
            render "<h5>Error In Assigning Examination Venue</h5>"
        }

    }
    def generateStudentList={
        def studList= adminInfoService.updateStudentList(params)
        render studList as JSON
    }
}

