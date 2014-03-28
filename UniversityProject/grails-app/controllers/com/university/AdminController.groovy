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

       def stuList= adminInfoService.provisionalStudentList(params)
        render stuList as JSON

    }

    def generateRollNo(){

        def rollNo=studentRegistrationService.getStudentRollNumber(params)
        redirect(controller: 'admin', action: 'viewProvisionalStudents' , params: [rollNo:"generated"])

    }
}
