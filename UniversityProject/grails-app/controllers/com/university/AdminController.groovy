package com.university

import examinationproject.ProgramDetail
import examinationproject.StudyCenter
import grails.converters.JSON
import grails.plugins.springsecurity.Secured



class AdminController {

    def adminInfoService
    def studentRegistrationService
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
}
