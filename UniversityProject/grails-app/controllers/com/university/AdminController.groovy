package com.university

import examinationproject.ProgramDetail
import examinationproject.StudyCenter
import grails.converters.JSON

class AdminController {

    def adminInfoService
    def studentRegistrationService
    def viewProvisionalStudents() {

        def studyCenterList=StudyCenter.findAll()
        def programList=ProgramDetail.findAll()
       [studyCenterList:studyCenterList,programList:programList]
    }

    def getStudentList(){

       def stuList= adminInfoService.provisionalStudentList(params)
        render stuList as JSON

    }

    def generateRollNo(){

        studentRegistrationService.getStudentRollNumber(params)

        redirect(controller: 'admin', action: 'viewProvisionalStudents')


    }
}
